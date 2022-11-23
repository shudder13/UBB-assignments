from model.finite_automata.alpha import Alpha
from model.finite_automata.state import State

class FiniteAutomata:
    def __init__(self, states: set[State], start_state: State, final_states: set[State], alphabet: set[Alpha], transitions: dict[State, dict[Alpha, set[State]]]) -> None:
        self.__states: set[State] = states
        self.__start_state: State = start_state
        self.__final_states: set[State] = final_states
        self.__alphabet: set[Alpha] = alphabet
        self.__transitions: dict[State, dict[Alpha, set[State]]] = transitions
    
    def get_states(self) -> set[State]:
        return self.__states
    
    def get_start_state(self) -> State:
        return self.__start_state
    
    def get_final_states(self) -> set[State]:
        return self.__final_states
    
    def get_alphabet(self) -> set[Alpha]:
        return self.__alphabet
    
    def get_transitions(self) -> dict[State, dict[Alpha, set[State]]]:
        return self.__transitions
    
    def test_sequence(self, sequence: str) -> bool:
        sequence: list[Alpha] = [Alpha(alpha) for alpha in sequence]
        sequence.insert(0, self.__start_state)
        while len(sequence) != 1:
            current_state, alpha = sequence.pop(0), sequence.pop(0)
            try:
                # get one random state from the set
                for next_state in self.__transitions[current_state][alpha]:
                    break
            except KeyError:
                return False
            sequence.insert(0, next_state)
        return True if sequence[0] in self.__final_states else False
    
    def get_longest_accepted_prefix(self, sequence: str) -> str:
        while True:  # to fix
            if self.test_sequence(sequence):
                return sequence
            sequence = sequence[:-1]
    
    def is_deterministic(self) -> bool:
        for _, transitions in self.__transitions.items():
            for _, next_states in transitions.items():
                if len(next_states) >= 2:
                    return False
        return True
