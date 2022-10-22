package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.Friendship;
import model.User;
import repository.FriendshipRepository;
import validator.FriendshipValidator;

import java.util.Collection;
import java.util.Objects;

public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final FriendshipValidator friendshipValidator;

    public FriendshipService(FriendshipRepository friendshipRepository, FriendshipValidator friendshipValidator) {
        this.friendshipRepository = friendshipRepository;
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

    public void addFriendship(User firstUser, User secondUser) throws ValidationException, RepositoryException {
        Integer idCounter = getMaximumId() + 1;
        Friendship friendship = new Friendship(idCounter, firstUser, secondUser);
        friendshipValidator.validate(friendship);
        friendshipRepository.add(friendship);
    }

    public void removeFriendshipsOfUser(Integer userId) throws RepositoryException {
        Collection<Friendship> friendships = friendshipRepository.getAll();
        for (Friendship friendship : friendships)
            if (Objects.equals(friendship.getFirstUser().getId(), userId) || Objects.equals(friendship.getSecondUser().getId(), userId)) {
                friendshipRepository.remove(friendship.getId());
                removeFriendshipsOfUser(userId);
                break;
            }
    }

    public Collection<Friendship> getFriendships() {
        return friendshipRepository.getAll();
    }
}
