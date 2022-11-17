package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.entities.Friendship;
import model.entities.User;
import repository.file.FriendshipFileRepository;
import validator.FriendshipValidator;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class FriendshipService {
    private final FriendshipFileRepository friendshipFileRepository;
    private final FriendshipValidator friendshipValidator;

    public FriendshipService(FriendshipFileRepository friendshipFileRepository, FriendshipValidator friendshipValidator) {
        this.friendshipFileRepository = friendshipFileRepository;
        this.friendshipValidator = friendshipValidator;
    }

    private Integer getMaximumId() throws RepositoryException, IOException {
        Collection<Friendship> friendships = friendshipFileRepository.getAll();
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
        Friendship friendship = new Friendship(idCounter, firstUser, secondUser);
        friendshipValidator.validate(friendship);
        friendshipFileRepository.add(friendship);
    }

    public void removeFriendship(Integer id) throws RepositoryException, IOException {
        friendshipFileRepository.remove(id);
    }

    public void removeFriendshipsOfUser(Integer userId) throws RepositoryException, IOException {
        Collection<Friendship> friendships = friendshipFileRepository.getAll();
        for (Friendship friendship : friendships)
            if (Objects.equals(friendship.getFirstUser().getId(), userId) || Objects.equals(friendship.getSecondUser().getId(), userId)) {
                friendshipFileRepository.remove(friendship.getId());
                removeFriendshipsOfUser(userId);
                break;
            }
    }

    public Collection<Friendship> getFriendships() throws RepositoryException, IOException {
        return friendshipFileRepository.getAll();
    }
}
