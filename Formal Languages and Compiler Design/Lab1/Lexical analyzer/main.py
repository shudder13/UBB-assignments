from analyzer import Analyzer


if __name__ == '__main__':
    analyzer = Analyzer(source_code_filename='example.cpp', tokens_table_filename='tokens.txt')
    tokens = analyzer.tokenize()
