from model.finite_automata import FiniteAutomata
from read import InputType, read_finite_automata
from user_interface.user_interface import UserInterface
from utils.constants import FILE_NAME


if __name__ == '__main__':
    while True:
        try:
            states, start_state, final_states, alphabet, transitions = read_finite_automata(input_type=InputType.FILE, file_name=FILE_NAME)
            finite_automata = FiniteAutomata(states, start_state, final_states, alphabet, transitions)
            user_interface = UserInterface(finite_automata)
            exit_code: int = user_interface.run()
            if exit_code == 0:
                break
        except Exception as exception:
            print(str(exception))
