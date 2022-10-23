package model.network;

import model.entities.User;

import java.util.LinkedList;
import java.util.List;

public class NetworkNode {
    private final User user;
    private final List<NetworkNode> neighbors;
    private boolean visited;

    public NetworkNode(User user) {
        this.user = user;
        this.neighbors = new LinkedList<>();
        this.visited = false;
    }

    public User getUser() {
        return user;
    }

    public void addNeighbor(NetworkNode neighbor) {
        if (neighbor == null)
            throw new IllegalArgumentException("The neighbor must not be null.");
        neighbors.add(neighbor);
    }

    public void visit() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public List<NetworkNode> getNeighbors() {
        return neighbors;
    }
}
