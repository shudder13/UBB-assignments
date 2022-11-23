from enum import Enum, auto
from typing import Optional

from model.finite_automata.state import State
from model.finite_automata.alpha import Alpha


class InputType(Enum):
    FILE = auto()
    CONSOLE = auto()


def read_finite_automata_from_console() -> tuple[set[State], State, set[State], set[Alpha], dict[State, dict[Alpha, set[State]]]]:
    states = set([State(state) for state in input('Enter states: ').strip().split(' ')])
    start_state = State(input('Enter start state: ').strip())
    final_states = set([State(state) for state in input('Enter final states: ').strip().split(' ')])
    alphabet = set([Alpha(alpha) for alpha in input('Enter alphabet: ').strip()])
    transitions = {}
    print('Enter transitions, then type "STOP":')
    while True:
        line = input()
        if line.strip().upper() == 'STOP':
            break
        current_state, alphas, next_state = line.strip().split(' ')
        current_state = State(current_state)
        next_state = State(next_state)
        for alpha in alphas:
            alpha = Alpha(alpha)
            if current_state in transitions:
                if alpha in transitions[current_state]:
                    transitions[current_state][alpha].add(next_state)
                else:
                    transitions[current_state][alpha] = {next_state}
            else:
                transitions[current_state] = {alpha: {next_state}}
    return (states, start_state, final_states, alphabet, transitions)


def read_finite_automata_from_file(file_name: str) -> tuple[set[State], State, set[State], set[Alpha], dict[State, dict[Alpha, set[State]]]]:
    with open(file_name, 'r') as f:
        states = set([State(state) for state in f.readline().strip().split(' ')])
        start_state = State(f.readline().strip())
        final_states = set([State(state) for state in f.readline().strip().split(' ')])
        alphabet = set([Alpha(alpha) for alpha in f.readline().strip()])
        transitions = {}
        for line in f:
            current_state, alphas, next_state = line.strip().split(' ')
            current_state = State(current_state)
            next_state = State(next_state)
            for alpha in alphas:
                alpha = Alpha(alpha)
                if current_state in transitions:
                    if alpha in transitions[current_state]:
                        transitions[current_state][alpha].add(next_state)
                    else:
                        transitions[current_state][alpha] = {next_state}
                else:
                    transitions[current_state] = {alpha: {next_state}}
        return (states, start_state, final_states, alphabet, transitions)


def read_finite_automata(input_type: InputType, file_name: Optional[str] = None):
    match input_type:
        case InputType.CONSOLE:
            return read_finite_automata_from_console()
        case InputType.FILE:
            if type(file_name) != str:
                raise Exception('The file name must be specified.')
            return read_finite_automata_from_file(file_name)
