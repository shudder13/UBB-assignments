from model.analyzer.analyzer import Analyzer
from model.finite_automata.finite_automata import FiniteAutomata
from model.finite_automata.read import read_finite_automata_from_file
from utils.constants import IDENTIFIERS_FA_FILENAME, INTEGER_CONSTANTS_FA_FILENAME, REAL_CONSTANTS_FA_FILENAME, TOKENS_FILENAME


if __name__ == '__main__':
    identifiers_finite_automata: FiniteAutomata = FiniteAutomata(*read_finite_automata_from_file(IDENTIFIERS_FA_FILENAME))
    integers_finite_automata: FiniteAutomata = FiniteAutomata(*read_finite_automata_from_file(INTEGER_CONSTANTS_FA_FILENAME))
    reals_finite_automata: FiniteAutomata = FiniteAutomata(*read_finite_automata_from_file(REAL_CONSTANTS_FA_FILENAME))
    analyzer = Analyzer(source_code_filename='example.cpp', tokens_table_filename=TOKENS_FILENAME, identifiers_finite_automata=identifiers_finite_automata, integers_finite_automata=integers_finite_automata, reals_finite_automata=reals_finite_automata)

    analyzer.tokenize()
    analyzer.tokenize()
    analyzer.write_symbols_table()
    analyzer.write_internal_form()
