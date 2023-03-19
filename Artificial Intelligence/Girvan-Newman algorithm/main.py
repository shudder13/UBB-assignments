import os
from helpers.helpers import plot_network_communities
from model.network_dataset import NetworkDataset
import networkx as nx


def detect_communities(network: nx.classes.Graph, number_of_communities: int | None = None):
    from networkx.algorithms.community import greedy_modularity_communities

    communities: list[frozenset]
    if number_of_communities is None:
        communities = greedy_modularity_communities(network)
    else:
        communities = greedy_modularity_communities(network, cutoff=number_of_communities, best_n=number_of_communities)
    
    node_to_index = {node: idx for idx, node in enumerate(network.nodes())}
    communities_list = [0] * network.number_of_nodes()
    for idx, community in enumerate(communities):
        for node in community:
            communities_list[node_to_index[node]] = idx + 1

    return {
        'number_of_communities': len(communities),
        'list': communities_list
    }


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
        match dataset.name:
            case "dolphins":
                communities = detect_communities(network, number_of_communities=2)
            case "football":
                communities = detect_communities(network, number_of_communities=12)
            case "karate":
                communities = detect_communities(network, number_of_communities=2)
            case "krebs":
                communities = detect_communities(network, number_of_communities=3)
            case _:
                communities = detect_communities(network)
        print(f'Dataset name: {dataset.name}')
        print(f'Number of communities: {communities["number_of_communities"]}')
        print(f'Communities: {communities["list"]}')
        print()
        plot_network_communities(network, communities, dataset.name)
 