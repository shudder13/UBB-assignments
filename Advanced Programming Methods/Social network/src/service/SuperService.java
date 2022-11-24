package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.entities.Friendship;
import model.entities.User;
import model.network.Network;

import java.io.IOException;
import java.util.Collection;

public class SuperService {
    private final UserService userService;
    private final FriendshipService friendshipService;

    public SuperService(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    public void addUser(String firstName, String lastName, String email) throws ValidationException, RepositoryException, IOException {
        userService.addUser(firstName, lastName, email);
    }

    public void removeUser(Integer id) throws RepositoryException, IOException {
        friendshipService.removeFriendshipsOfUser(id);
        userService.removeUser(id);
    }

    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    public void addFriendship(Integer firstUserId, Integer secondUserId) throws ValidationException, RepositoryException, IOException {
        User firstUser = userService.getUser(firstUserId);
        User secondUser = userService.getUser(secondUserId);
        friendshipService.addFriendship(firstUser, secondUser);
    }

    public void removeFriendship(Integer id) {
        friendshipService.removeFriendship(id);
    }

    public Collection<Friendship> getFriendships() throws RepositoryException, IOException {
        return friendshipService.getFriendships();
    }

    public Integer getNumberOfCommunities() throws RepositoryException, IOException {
        Network network = new Network(userService.getUsers(), friendshipService.getFriendships());
        return network.getNumberOfCommunities();
    }
}
