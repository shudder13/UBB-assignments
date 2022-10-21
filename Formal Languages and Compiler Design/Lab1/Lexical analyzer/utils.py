def read_file(filename: str) -> str:
    with open(file=filename, mode='r') as f:
        return f.read()
