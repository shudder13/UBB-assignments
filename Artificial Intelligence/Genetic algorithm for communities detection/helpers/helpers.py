import networkx as nx
import matplotlib.pyplot as plt


def plot_network_communities(network: nx.classes.Graph, communities: list[int], dataset_name: str):
    plt.figure(figsize=(6, 6))
    pos = nx.spring_layout(network, seed=42)
    nx.draw(network, pos, node_color=communities, cmap=plt.get_cmap('jet'), node_size=50)
    plt.get_current_fig_manager().set_window_title(dataset_name)
    plt.show()


def convert_communities_to_smaller_numbers(communities: list[int]):
    unique_elements = list(set(communities))
    identifier_map = {unique_elements[i]: i+1 for i in range(len(unique_elements))}

    converted_list = [identifier_map[elem] for elem in communities]
    return converted_list
