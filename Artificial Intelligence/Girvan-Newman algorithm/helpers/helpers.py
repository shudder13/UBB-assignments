import networkx as nx
import matplotlib.pyplot as plt

def plot_network_communities(network, communities, dataset_name):
    plt.figure(figsize=(6, 6))
    pos = nx.spring_layout(network, seed=42)
    nx.draw(network, pos, node_color=communities['list'], cmap=plt.get_cmap('jet'), node_size=50)
    plt.get_current_fig_manager().set_window_title(dataset_name)
    plt.show()
