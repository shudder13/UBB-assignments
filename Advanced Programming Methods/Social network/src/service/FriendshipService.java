package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.entities.Friendship;
import model.entities.User;
import repository.database.FriendshipDbRepository;
import validator.FriendshipValidator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

public class FriendshipService {
    private final FriendshipDbRepository friendshipDbRepository;
    private final FriendshipValidator friendshipValidator;

    public FriendshipService(FriendshipDbRepository friendshipDbRepository, FriendshipValidator friendshipValidator) {
        this.friendshipDbRepository = friendshipDbRepository;
        this.friendshipValidator = friendshipValidator;
    }

    private Integer getMaximumId() throws RepositoryException, IOException {
        Collection<Friendship> friendships = friendshipDbRepository.getAll();
        if (friendships.isEmpty())
            return -1;
        Integer maximumId = 0;
        for (Friendship friendship : friendships)
            if (friendship.getId() > maximumId)
                maximumId = friendship.getId();
        return maximumId;
    }

    public void addFriendship(User firstUser, User secondUser) throws ValidationException, RepositoryException, IOException {
        Integer idCounter = getMaximumId() + 1;
        Friendship friendship = new Friendship(idCounter, firstUser, secondUser, LocalDateTime.now());
        friendshipValidator.validate(friendship);
        friendshipDbRepository.add(friendship);
    }

    public void removeFriendship(Integer id) {
        friendshipDbRepository.remove(id);
    }

    public void removeFriendshipsOfUser(Integer userId) throws RepositoryException, IOException {
        Collection<Friendship> friendships = friendshipDbRepository.getAll();
        for (Friendship friendship : friendships)
            if (Objects.equals(friendship.getFirstUser().getId(), userId) || Objects.equals(friendship.getSecondUser().getId(), userId)) {
                friendshipDbRepository.remove(friendship.getId());
                removeFriendshipsOfUser(userId);
                break;
            }
    }

    public Collection<Friendship> getFriendships() throws RepositoryException, IOException {
        return friendshipDbRepository.getAll();
    }
}
