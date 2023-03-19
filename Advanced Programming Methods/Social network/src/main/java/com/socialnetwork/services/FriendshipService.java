package com.socialnetwork.services;

import com.socialnetwork.domain.entities.Friendship;
import com.socialnetwork.domain.entities.User;
import com.socialnetwork.domain.exceptions.ValidationException;
import com.socialnetwork.domain.validators.FriendshipValidator;
import com.socialnetwork.repository.FriendshipRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final FriendshipValidator friendshipValidator;

    public FriendshipService(FriendshipRepository friendshipDbRepository, FriendshipValidator friendshipValidator) {
        this.friendshipRepository = friendshipDbRepository;
        this.friendshipValidator = friendshipValidator;
    }

    private Integer getMaximumId() {
        Collection<Friendship> friendships = friendshipRepository.getAll();
        if (friendships.isEmpty())
            return -1;
        Integer maximumId = 0;
        for (Friendship friendship : friendships)
            if (friendship.getId() > maximumId)
                maximumId = friendship.getId();
        return maximumId;
    }

    public void addFriendship(User firstUser, User secondUser) throws ValidationException {
        Integer idCounter = getMaximumId() + 1;
        Friendship friendship = new Friendship(idCounter, firstUser, secondUser, LocalDateTime.now());
        friendshipValidator.validate(friendship);
        friendshipRepository.add(friendship);
    }

    public void removeFriendship(Integer id) {
        friendshipRepository.remove(id);
    }

    public void removeFriendshipsOfUser(Integer userId) {
        Collection<Friendship> friendships = friendshipRepository.getAll();
        for (Friendship friendship : friendships)
            if (Objects.equals(friendship.getFirstUser().getId(), userId) || Objects.equals(friendship.getSecondUser().getId(), userId)) {
                friendshipRepository.remove(friendship.getId());
                removeFriendshipsOfUser(userId);
                break;
            }
    }

    public Friendship getFriendshipOfUsers(Integer firstUserId, Integer secondUserId) {
        return friendshipRepository.getAll().stream()
                .filter(friendship -> friendship.getFirstUser().getId().equals(firstUserId) && friendship.getSecondUser().getId().equals(secondUserId) ||
                        friendship.getFirstUser().getId().equals(secondUserId) && friendship.getSecondUser().getId().equals(firstUserId))
                .findFirst().orElse(null);
    }

    public Collection<Friendship> getFriendships() {
        return friendshipRepository.getAll();
    }
}
