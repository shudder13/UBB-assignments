import networkx as nx
import random

from helpers.helpers import convert_communities_to_smaller_numbers

class Chromosome:
    def __init__(self, network: nx.classes.Graph, representation: list[int] | None = None) -> None:
        self.__network = network
        if representation != None:
            self.__representation = convert_communities_to_smaller_numbers(representation)
        else:
            number_of_nodes = network.number_of_nodes()
            self.__representation = [random.randint(1, number_of_nodes) for _ in range(number_of_nodes)]
        self.__fitness: int
    
    @property
    def representation(self):
        return self.__representation
    
    @property
    def fitness(self):
        return self.__fitness
    
    @representation.setter
    def representation(self, new_representation: list[int]):
        self.__representation = new_representation

    @fitness.setter
    def fitness(self, new_fitness: int):
        self.__fitness = new_fitness
    
    def crossover(self, other_chromosome):
        position = random.randint(0, len(self.__representation) - 1)
        new_chromosome_representation = []
        for i in range(position):
            new_chromosome_representation.append(self.__representation[i])
        for i in range(position, len(self.__representation)):
            new_chromosome_representation.append(other_chromosome.__representation[i])
        offspring = Chromosome(network=self.__network, representation=new_chromosome_representation)
        return offspring
    
    def mutation(self):
        position = random.randint(0, len(self.__representation) - 1)
        new_community = random.choice(list(set(self.__representation)))
        self.__representation[position] = new_community
    
    def __str__(self) -> str:
        return f'Chromosome {self.__representation} has fit {self.__fitness}'

    def __repr__(self) -> str:
        return self.__str__()
    
    def __eq__(self, __c) -> bool:
        return self.__representation == __c.__representation and self.__fitness == __c.__fitness
