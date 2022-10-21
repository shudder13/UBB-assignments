from model.analyzer import Analyzer


if __name__ == '__main__':
    analyzer = Analyzer(source_code_filename='example.cpp', tokens_table_filename='data/tokens.txt')
    tokens = analyzer.tokenize()
