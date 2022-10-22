package service;

import repository.FriendshipRepository;
import validator.FriendshipValidator;

public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final FriendshipValidator friendshipValidator;

    public FriendshipService(FriendshipRepository friendshipRepository, FriendshipValidator friendshipValidator) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipValidator = friendshipValidator;
    }
}
