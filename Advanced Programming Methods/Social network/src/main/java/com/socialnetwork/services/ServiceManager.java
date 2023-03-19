package com.socialnetwork.services;

import com.socialnetwork.domain.entities.FriendRequest;
import com.socialnetwork.domain.entities.Friendship;
import com.socialnetwork.domain.entities.User;
import com.socialnetwork.domain.exceptions.ValidationException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceManager {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final FriendRequestService friendRequestService;

    public ServiceManager(UserService userService, FriendshipService friendshipService, FriendRequestService friendRequestService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.friendRequestService = friendRequestService;
    }

    public void addUser(String username, String password, String firstName, String lastName, String email) throws ValidationException {
        userService.addUser(username, password, firstName, lastName, email);
    }

    public void removeUser(Integer id) {
        friendshipService.removeFriendshipsOfUser(id);
        userService.removeUser(id);
    }

    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    public Collection<User> getFriendsOfUser(User user) {
        Collection<Friendship> friendships = friendshipService.getFriendships();
        return friendships.stream()
                .filter(friendship -> friendship.getFirstUser().equals(user) || friendship.getSecondUser().equals(user))
                .map(friendship -> {
                    if (friendship.getFirstUser().equals(user))
                        return friendship.getSecondUser();
                    else
                        return friendship.getFirstUser();
                })
                .collect(Collectors.toList());
    }

    public boolean checkFriendship(User firstUser, User secondUser) {
        Integer firstUserId = firstUser.getId();
        Integer secondUserId = secondUser.getId();
        return friendshipService.getFriendships().stream()
                .anyMatch(friendship -> friendship.getFirstUser().getId().equals(firstUserId) && friendship.getSecondUser().getId().equals(secondUserId) ||
                        friendship.getSecondUser().getId().equals(firstUserId) && friendship.getFirstUser().getId().equals(secondUserId));
    }

    public void addFriendship(Integer firstUserId, Integer secondUserId) throws ValidationException {
        User firstUser = userService.getUser(firstUserId);
        User secondUser = userService.getUser(secondUserId);
        friendshipService.addFriendship(firstUser, secondUser);
    }

    public void removeFriendship(Integer id) {
        friendshipService.removeFriendship(id);
    }

    public Collection<Friendship> getFriendships() {
        return friendshipService.getFriendships();
    }

    public Friendship getFriendshipOfUsers(Integer firstUserId, Integer secondUserId) {
        return friendshipService.getFriendshipOfUsers(firstUserId, secondUserId);
    }

    public Optional<User> login(String username, String password) {
        Collection<User> users = userService.getUsers();
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findFirst();
    }

    public Collection<FriendRequest> getFriendshipRequestsOfUser(User user) {
        return friendRequestService.getFriendRequestsOfUser(user);
    }

    public void requestFriendship(User sender, User receiver) {
        friendRequestService.requestFriendship(sender, receiver);
    }

    public void acceptFriendRequest(FriendRequest friendRequest) {
        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();
        try {
            friendshipService.addFriendship(sender, receiver);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        friendRequestService.removeFriendRequest(friendRequest);
    }

    public void deleteFriendRequest(FriendRequest friendRequest) {
        friendRequestService.removeFriendRequest(friendRequest);
    }
}
