class Alpha:
    def __init__(self, value: str) -> None:
        self.__value: str = value
    
    def __str__(self) -> str:
        return self.__value
    
    def __repr__(self) -> str:
        return self.__value
    
    def __hash__(self) -> int:
        return hash((self.__value))

    def __eq__(self, __o: object) -> bool:
        return type(self) == type(__o) and self.__value == __o.__value
