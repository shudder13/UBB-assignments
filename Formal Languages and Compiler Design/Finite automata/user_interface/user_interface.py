import json
from model.finite_automata import FiniteAutomata


class UserInterface:
    def __init__(self, finite_automata: FiniteAutomata) -> None:
        self.__finite_automata: FiniteAutomata = finite_automata
        self.__menu: str = ("\tMENU\n"
            "1. Print set of states\n"
            "2. Print alphabet\n"
            "3. Print transitions\n"
            "4. Print final states\n"
            "5. Test sequence\n"
            "6. Print longest accepted prefix\n"
            "7. Test if deterministic\n"
            "0. Exit")
    
    def run(self) -> int:
        while True:
            print(self.__menu)
            command = input('Enter command: ')
            match command:
                case '1':
                    print(f'States: {self.__finite_automata.get_states()}')
                case '2':
                    print(f'Alphabet: {str(self.__finite_automata.get_alphabet())}')
                case '3':
                    transitions = self.__finite_automata.get_transitions()
                    for key, value in transitions.items():
                        print(f'{key}: {value}')
                case '4':
                    print(f'Final states: {self.__finite_automata.get_final_states()}')
                case '5':
                    sequence = input('Enter sequence: ')
                    print("Sequence accepted" if self.__finite_automata.test_sequence(sequence) else "Sequence not accepted")
                case '6':
                    sequence = input('Enter sequence: ')
                    print(f'Longest accepted prefix is: {self.__finite_automata.get_longest_accepted_prefix(sequence)}')
                case '7':
                    print("FA is deterministic" if self.__finite_automata.is_deterministic() else "FA is not deterministic")
                case '0':
                    print('Exiting...')
                    return 0
                case _:
                    print('Unknown command')
            print()
