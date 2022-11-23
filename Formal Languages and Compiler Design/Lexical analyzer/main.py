from utils.constants import SOURCE_CODE_FILENAME, TOKENS_FILENAME
from model.analyzer import Analyzer


if __name__ == '__main__':
    analyzer = Analyzer(source_code_filename=SOURCE_CODE_FILENAME, tokens_table_filename=TOKENS_FILENAME)
    analyzer.tokenize()
    analyzer.tokenize()
    analyzer.write_symbols_table()
    analyzer.write_internal_form()
