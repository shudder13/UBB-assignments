package model.network;


import model.entities.Friendship;
import model.entities.User;

import java.util.*;

public class Network {
    private final List<NetworkNode> nodes;

    private NetworkNode getNodeByUserId(Integer id) {
        for (NetworkNode node : nodes)
            if (Objects.equals(node.getUser().getId(), id))
                return node;
        return null;
    }

    public Network(Collection<User> users, Collection<Friendship> friendships) {
        nodes = new ArrayList<>();
        for (User user : users)
            nodes.add(new NetworkNode(user));
        for (Friendship friendship : friendships) {
            NetworkNode firstNode = getNodeByUserId(friendship.getFirstUser().getId());
            NetworkNode secondNode = getNodeByUserId(friendship.getSecondUser().getId());
            assert firstNode != null && secondNode != null;
            firstNode.addNeighbor(secondNode);
            secondNode.addNeighbor(firstNode);
        }
    }

    public Integer getNumberOfCommunities() {
        Integer numberOfCommunities = 0;

        Deque<NetworkNode> nodesToVisit = new LinkedList<>();
        while (true) {
            NetworkNode firstUnvisitedNode = getFirstUnvisitedNode();
            if (firstUnvisitedNode == null)
                break;
            nodesToVisit.push(firstUnvisitedNode);
            while (!nodesToVisit.isEmpty()) {
                NetworkNode currentNode = nodesToVisit.pop();
                currentNode.visit();
                List<NetworkNode> neighbors = currentNode.getNeighbors();
                for (NetworkNode neighbor : neighbors)
                    if (!neighbor.isVisited())
                        nodesToVisit.push(neighbor);
            }
            numberOfCommunities++;
        }
        return numberOfCommunities;
    }

    private NetworkNode getFirstUnvisitedNode() {
        for (NetworkNode node : nodes)
            if (!node.isVisited())
                return node;
        return null;
    }
}
