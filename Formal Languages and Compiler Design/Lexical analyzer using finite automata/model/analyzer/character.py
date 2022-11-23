class Character:
    def __init__(self, i: int, j: int, value: str) -> None:
        self.i = i
        self.j = j
        self.value = value
    

    def __str__(self) -> str:
        return self.value
