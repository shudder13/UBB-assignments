import networkx as nx


class NetworkDataset:
    def __init__(self, name: str, network_file_name: str, expected_number_of_communities: int | None = None):
        self._name = name
        self._network_file_name = network_file_name
        self._expected_number_of_communities = expected_number_of_communities
        self._network = nx.read_gml(network_file_name)
    
    @property
    def name(self):
        return self._name

    @property
    def network(self):
        return self._network
    
    @property
    def expected_number_of_communities(self):
        return self._expected_number_of_communities
    
    @expected_number_of_communities.setter
    def expected_number_of_communities(self, value):
        self._expected_number_of_communities = value
