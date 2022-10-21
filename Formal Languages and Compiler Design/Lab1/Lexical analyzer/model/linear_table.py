from typing import List


class LinearTable:
    def __init__(self) -> None:
        self.__table: List[str] = []

    def __insert_item_in_table(self, item: str) -> None:
        insert_index = 0
        for index in range(len(self.__table)):
            if self.__table[index] > item:
                insert_index = index
                break
            insert_index = len(self.__table)
        self.__table = self.__table[:insert_index] + [item] + self.__table[insert_index:]

    def add(self, item: str) -> None:
        if self.exists(item):
            raise Exception('This item already exists in the table.')
        self.__insert_item_in_table(item)
        
    def exists(self, item: str) -> bool:
        if item in self.__table:
            return True
        return False
    
    def index(self, item: str) -> int:
        for index, element in enumerate(self.__table):
            if element == item:
                return index
        raise Exception('This item does not exist in the table.')
