from typing import Iterator, Tuple, Union

from utils.constants import INTERNAL_FORM_FILENAME, SYMBOLS_TABLE_FILENAME

from model.analyzer.buffer import Buffer, BufferState
from model.analyzer.character import Character
from model.analyzer.linear_table import LinearTable

from model.finite_automata.finite_automata import FiniteAutomata


class Analyzer:
    def __init__(self, source_code_filename: str, tokens_table_filename: str, identifiers_finite_automata: FiniteAutomata, integers_finite_automata: FiniteAutomata, reals_finite_automata: FiniteAutomata) -> None:
        self.__source_code_filename: str = source_code_filename
        self.__tokens_table: dict[int, str] = self.__read_tokens_table(tokens_table_filename)
        self.__symbols_table: LinearTable = LinearTable()
        self.__internal_form: list[Tuple[int, Union[int, None]]] = []
        self.__identifiers_finite_automata: FiniteAutomata = identifiers_finite_automata
        self.__integers_finite_automata: FiniteAutomata = integers_finite_automata
        self.__reals_finite_automata: FiniteAutomata = reals_finite_automata
    
    def __read_tokens_table(self, tokens_table_filename: str) -> dict[int, str]:
        tokens_table: dict[int, str] = {}
        with open(file=tokens_table_filename, mode='r') as f:
            for line in f.readlines():
                code, token = line.split()
                code = int(code)
                tokens_table[code] = token
        return tokens_table
    
    def __get_next_character(self) -> Iterator[Character]:
        with open(self.__source_code_filename, 'r') as f:
            for i, line in enumerate(f.readlines()):
                for j, character in enumerate(line):
                    yield Character(i, j, character)
    
    def __add_constant(self, constant: str) -> None:
        constant_code: int
        if self.__symbols_table.exists(constant):
            constant_code = self.__symbols_table.index(constant)
        else:
            constant_code = self.__symbols_table.add(constant)
        self.__internal_form.append((1, constant_code))

    def __add_identifier(self, identifier: str) -> None:
        identifier_code: int
        if self.__symbols_table.exists(identifier):
            identifier_code = self.__symbols_table.index(identifier)
        else:
            identifier_code = self.__symbols_table.add(identifier)
        self.__internal_form.append((0, identifier_code))

    def __add_keyword(self, keyword: str) -> None:
        # keyword is a value in the token table
        keyword_code: int
        for key, value in self.__tokens_table.items():
            if value == keyword:
                keyword_code = key
                break
        self.__internal_form.append((keyword_code, None))

    def __add_symbol(self, symbol: str) -> None:
        # symbol is a value in the token table
        symbol_code: int
        for key, value in self.__tokens_table.items():
            if value == symbol:
                symbol_code = key
                break
        self.__internal_form.append((symbol_code, None))
    
    def tokenize(self) -> list[str]:
        self.__internal_form.clear()
        character_generator: Iterator[Character] = self.__get_next_character()
        buffer: Buffer = Buffer(self.__tokens_table, self.__identifiers_finite_automata, self.__integers_finite_automata, self.__reals_finite_automata)
        for character in character_generator:
            buffer.append(character)
            if buffer.buffer_state in (BufferState.IDENTIFIER, BufferState.KEYWORD, BufferState.INTEGER, BufferState.REAL, BufferState.SYMBOL, BufferState.SPACE):
                continue
            else:
                assert buffer.buffer_state == BufferState.ERROR
                last_character_in_buffer: Character = buffer.remove_last_element()
                match buffer.buffer_state:
                    case BufferState.EMPTY:
                        raise Exception(f'Unexpected symbol on line {last_character_in_buffer.i}, column {last_character_in_buffer.j}.')
                    case BufferState.KEYWORD:
                        self.__add_keyword(str(buffer))
                    case BufferState.IDENTIFIER:
                        if len(buffer) > 8:
                            first_character = buffer.get_first_element()
                            raise Exception(f'Too long identifier on line {first_character.i}, column {first_character.j} (maximum 8 characters).')
                        self.__add_identifier(str(buffer))
                    case BufferState.INTEGER:
                        if str(last_character_in_buffer) == '.':  # possible real number
                            buffer.append(last_character_in_buffer)
                            continue
                        else:
                            self.__add_constant(str(buffer))
                    case BufferState.REAL:
                        self.__add_constant(str(buffer))
                    case BufferState.SYMBOL:
                        self.__add_symbol(str(buffer))
                    case BufferState.SPACE:
                        pass
                    case BufferState.ERROR:
                        # 2 characters were added at the same time:
                        # first of them is still in the buffer and is an unexpected symbol
                        # second of them is stored in the variable last_character_in_buffer
                        error_character = buffer.get_first_element()
                        raise Exception(f'Unexpected symbol on line {error_character.i}, column {error_character.j}.')
                buffer.clear()
                buffer.append(last_character_in_buffer)
    
    def write_symbols_table(self):
        with open(SYMBOLS_TABLE_FILENAME, 'w') as f:
            for index, element in enumerate(self.__symbols_table.get_all()):
                f.write(f'{index}\t{element}\n')

    def write_internal_form(self):
        with open(INTERNAL_FORM_FILENAME, 'w') as f:
            for element in self.__internal_form:
                if element[1] is None:
                    f.write(f'{element[0]}\n')
                else:
                    f.write(f'{element[0]}\t{element[1]}\n')
