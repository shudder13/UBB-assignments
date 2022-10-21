from typing import Iterator
from buffer import Buffer, BufferState

from character import Character


class Analyzer:
    def __init__(self, source_code_filename: str, tokens_table_filename: str) -> None:
        self.__source_code_filename = source_code_filename
        self.__tokens_table: dict[int, str] = self.__read_tokens_table(tokens_table_filename)
        self.__identifiers_table = []
        self.__constants_table = []
    
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
        print(f'Constant {constant}')

    def __add_identifier(self, identifier: str) -> None:
        print(f'Identifier {identifier}')

    def __add_keyword(self, keyword: str) -> None:
        print(f'Keyword {keyword}')

    def __add_symbol(self, symbol: str) -> None:
        print(f'Symbol {symbol}')
    
    def tokenize(self) -> list[str]:
        character_generator: Iterator[Character] = self.__get_next_character()
        buffer: Buffer = Buffer(self.__tokens_table)
        for character in character_generator:
            buffer.append(character)
            if buffer.buffer_state in (BufferState.IDENTIFIER_OR_KEYWORD, BufferState.CONSTANT, BufferState.SYMBOL, BufferState.SPACE):
                continue
            else:
                assert buffer.buffer_state == BufferState.ERROR
                last_character_in_buffer: Character = buffer.remove_last_element()
                match buffer.buffer_state:
                    case BufferState.EMPTY:
                        raise Exception(f'Unexpected symbol on line {last_character_in_buffer.i}, column {last_character_in_buffer.j}.')
                    case BufferState.IDENTIFIER_OR_KEYWORD:  # identifier or keyword
                        if str(buffer) in self.__tokens_table.values():  # keyword
                            self.__add_keyword(str(buffer))
                        else:  # identifier
                            if len(buffer) > 8:
                                first_character = buffer.get_first_element()
                                raise Exception(f'Too long identifier on line {first_character.i}, column {first_character.j} (maximum 8 characters).')
                            self.__add_identifier(str(buffer))
                    case BufferState.CONSTANT:
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
