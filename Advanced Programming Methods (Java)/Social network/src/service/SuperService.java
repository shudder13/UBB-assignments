package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.Friendship;
import model.User;

import java.util.Collection;

public class SuperService {
    private final UserService userService;
    private final FriendshipService friendshipService;

    public SuperService(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    public void addUser(String firstName, String lastName) throws ValidationException, RepositoryException {
        userService.addUser(firstName, lastName);
    }

    public void removeUser(Integer id) throws RepositoryException {
        friendshipService.removeFriendshipsOfUser(id);
        userService.removeUser(id);
    }

    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    public void addFriendship(Integer firstUserId, Integer secondUserId) throws ValidationException, RepositoryException {
        User firstUser = userService.getUser(firstUserId);
        User secondUser = userService.getUser(secondUserId);
        friendshipService.addFriendship(firstUser, secondUser);
    }

    public Collection<Friendship> getFriendships() {
        return friendshipService.getFriendships();
    }
}
