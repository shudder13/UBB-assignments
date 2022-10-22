package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
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
        userService.removeUser(id);
    }

    public Collection<User> getUsers() {
        return userService.getUsers();
    }
}
