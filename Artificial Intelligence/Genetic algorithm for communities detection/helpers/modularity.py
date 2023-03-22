import networkx as nx
import numpy as np

def compute_modularity(network: nx.classes.Graph, communities: list[int]):
    number_of_nodes = network.number_of_nodes()
    adjacency_matrix = nx.to_numpy_array(network)
    degrees = np.array([val for (node, val) in network.degree()])
    number_of_edges = network.number_of_edges()
    M = 2 * number_of_edges
    Q = 0.0
    for i in range(number_of_nodes):
        for j in range(number_of_nodes):
            if communities[i] == communities[j]:
                Q += adjacency_matrix[i, j] - degrees[i] * degrees[j] / M
    return Q * 1 / M
