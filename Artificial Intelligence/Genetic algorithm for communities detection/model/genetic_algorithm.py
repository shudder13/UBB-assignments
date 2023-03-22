from model.chromosome import Chromosome
from typing import Callable
import networkx as nx
import random


class GeneticAlgorithm:
    def __init__(self, population_size: int, network: nx.classes.Graph, fitness_function: Callable):
        self.__population_size = population_size
        self.__network = network
        self.__fitness_function = fitness_function
        self.__population: list[Chromosome] = []
    
    @property
    def population(self):
        return self.__population
    
    def initialisation(self):
        for _ in range(self.__population_size):
            chromosome = Chromosome(network=self.__network)
            self.__population.append(chromosome)

    def evaluation(self):
        for chromosome in self.__population:
            chromosome.fitness = self.__fitness_function(self.__network, chromosome.representation)
    
    def best_chromosome(self):
        best_chromosome = self.__population[0]
        for chromosome in self.__population:
            if chromosome.fitness > best_chromosome.fitness:
                best_chromosome = chromosome
        return best_chromosome

    def worst_chromosome(self):
        worst_chromosome = self.__population[0]
        for chromosome in self.__population:
            if chromosome.fitness < worst_chromosome.fitness:
                worst_chromosome = chromosome
        return worst_chromosome
    
    def selection(self):
        position1 = random.randint(0, self.__population_size - 1)
        position2 = random.randint(0, self.__population_size - 1)
        if (self.__population[position1].fitness > self.__population[position2].fitness):
            return self.__population[position1]
        else:
            return self.__population[position2]
    
    def one_generation(self):
        new_population = []
        for _ in range(self.__population_size):
            chromosome1 = self.selection()
            chromosome2 = self.selection()
            offspring = chromosome1.crossover(chromosome2)
            offspring.mutation()
            new_population.append(offspring)
        self.__population = new_population
        self.evaluation()

    def one_generation_elitism(self):
        new_population = [self.best_chromosome()]
        for _ in range(self.__population_size - 1):
            chromosome1 = self.selection()
            chromosome2 = self.selection()
            offspring = chromosome1.crossover(chromosome2)
            offspring.mutation()
            new_population.append(offspring)
        self.__population = new_population
        self.evaluation()
    
    def one_generation_steady_state(self):
        for _ in range(self.__population_size):
            chromosome1 = self.selection()
            chromosome2 = self.selection()
            offspring = chromosome1.crossover(chromosome2)
            offspring.mutation()
            offspring.fitness = self.__fitness_function(self.__network, offspring.representation)
            worst_chromosome = self.worst_chromosome()
            if (offspring.fitness > worst_chromosome.fitness):
                worst_chromosome = offspring
