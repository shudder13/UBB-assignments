from model.analyzer import Analyzer


if __name__ == '__main__':
    analyzer = Analyzer(source_code_filename='example.cpp', tokens_table_filename='data/tokens.txt')
    analyzer.tokenize()
    analyzer.tokenize()
    analyzer.write_symbols_table()
    analyzer.write_internal_form()
