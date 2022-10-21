from enum import Enum, auto
from model.character import Character

class BufferState(Enum):
    EMPTY = auto()
    SYMBOL = auto()  # operator or separator
    IDENTIFIER_OR_KEYWORD = auto()
    CONSTANT = auto()  # numerical
    SPACE = auto()
    ERROR = auto()


class Buffer:
    def __init__(self, tokens_table) -> None:
        self.token : list[Character] = []
        self.buffer_state = BufferState.EMPTY
        self.__tokens_table: dict[int, str] = tokens_table
    
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
        elif str(self).isidentifier():
            self.buffer_state = BufferState.IDENTIFIER_OR_KEYWORD
        elif str(self).isdecimal():
            self.buffer_state = BufferState.CONSTANT
        elif str(self).isspace():
            self.buffer_state = BufferState.SPACE
        elif str(self) in self.__tokens_table.values():
            self.buffer_state = BufferState.SYMBOL
        else:
            self.buffer_state = BufferState.ERROR

    def __str__(self) -> str:
        return ''.join(character.value for character in self.token)
