import networkx as nx


class NetworkDataset:
    def __init__(self, name: str, network_file_name: str):
        self._name = name
        self._network_file_name = network_file_name
        self._network = nx.read_gml(network_file_name)
    
    @property
    def name(self):
        return self._name
    
    @property
    def network(self):
        return self._network
