import os
import networkx as nx
from parameters import NUMBER_OF_GENERATIONS, POPULATION_SIZE
from model.genetic_algorithm import GeneticAlgorithm
from helpers.helpers import plot_network_communities
from model.network_dataset import NetworkDataset
from helpers.modularity import compute_modularity

def detect_communities(network: nx.classes.Graph):
    genetic_algorithm = GeneticAlgorithm(population_size=POPULATION_SIZE, network=network, fitness_function=compute_modularity)
    genetic_algorithm.initialisation()
    genetic_algorithm.evaluation()

    for generation_index in range(NUMBER_OF_GENERATIONS):
        best_chromosome_fitness = genetic_algorithm.best_chromosome().fitness
        genetic_algorithm.one_generation_elitism()
        print(f'Best fitness in generation {generation_index} is {best_chromosome_fitness}')
    
    return genetic_algorithm.best_chromosome().representation


if __name__ == '__main__':
    datasets_names = [
        'adjnoun',
        'dolphins',
        'football',
        'karate',
        'krebs',
        'lesmis',
        'polbooks',
        'netscience',
        'polblogs',
        'astro-ph'
    ]

    datasets = [NetworkDataset(dataset_name, os.path.join('data', dataset_name, f'{dataset_name}.gml')) 
                for dataset_name in datasets_names]
    
    for dataset in datasets:
        network = dataset.network
        communities = detect_communities(network)
        print(f'Dataset name: {dataset.name}')
        print(f'Number of communities: {len(list(set(communities)))}')
        print(f'Communities: {communities}')
        print()
        plot_network_communities(network, communities, dataset.name)
