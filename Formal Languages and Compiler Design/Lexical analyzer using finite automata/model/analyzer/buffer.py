from enum import Enum, auto
from model.analyzer.character import Character
from model.finite_automata.finite_automata import FiniteAutomata

class BufferState(Enum):
    EMPTY = auto()
    SYMBOL = auto()  # operator or separator
    IDENTIFIER = auto()
    KEYWORD = auto()
    INTEGER = auto()
    REAL = auto()
    SPACE = auto()
    ERROR = auto()


class Buffer:
    def __init__(self, tokens_table, identifiers_finite_automata, integers_finite_automata, reals_finite_automata) -> None:
        self.token : list[Character] = []
        self.buffer_state = BufferState.EMPTY
        self.__tokens_table: dict[int, str] = tokens_table
        self.__identifiers_finite_automata: FiniteAutomata = identifiers_finite_automata
        self.__integers_finite_automata: FiniteAutomata = integers_finite_automata
        self.__reals_finite_automata: FiniteAutomata = reals_finite_automata
    
    def __len__(self) -> int:
        return len(self.token)
    
    def append(self, character: Character) -> None:
        self.token.append(character)
        self.__update_buffer_state()
    
    def get_first_element(self) -> Character:
        return self.token[0]
    
    def remove_last_element(self) -> Character:
        last_element = self.token.pop()
        self.__update_buffer_state()
        return last_element

    def clear(self) -> None:
        self.token.clear()
        self.buffer_state = BufferState.EMPTY

    def __update_buffer_state(self):
        if len(self) == 0:
            self.buffer_state = BufferState.EMPTY
        elif self.__identifiers_finite_automata.test_sequence(str(self)):  # identifier or keyword
            if str(self) in self.__tokens_table.values():  # keyword
                self.buffer_state = BufferState.KEYWORD
            else:  # identifier
                self.buffer_state = BufferState.IDENTIFIER
        elif self.__reals_finite_automata.test_sequence(str(self)):
            self.buffer_state = BufferState.REAL
        elif self.__integers_finite_automata.test_sequence(str(self)):
            self.buffer_state = BufferState.INTEGER
        elif str(self).isspace():
            self.buffer_state = BufferState.SPACE
        elif str(self) in self.__tokens_table.values():
            self.buffer_state = BufferState.SYMBOL
        else:
            self.buffer_state = BufferState.ERROR

    def __str__(self) -> str:
        return ''.join(character.value for character in self.token)
