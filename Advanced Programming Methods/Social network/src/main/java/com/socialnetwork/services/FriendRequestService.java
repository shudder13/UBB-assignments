package com.socialnetwork.services;

import com.socialnetwork.domain.entities.FriendRequest;
import com.socialnetwork.domain.entities.User;
import com.socialnetwork.repository.FriendRequestRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public Collection<FriendRequest> getFriendRequestsOfUser(User user) {
        return friendRequestRepository.getAllOfUser(user);
    }

    private Integer getMaximumId() {
        Collection<FriendRequest> friendRequests = friendRequestRepository.getAll();
        if (friendRequests.isEmpty())
            return -1;
        Integer maximumId = 0;
        for (FriendRequest friendRequest : friendRequests)
            if (friendRequest.getId() > maximumId)
                maximumId = friendRequest.getId();
        return maximumId;
    }

    public void requestFriendship(User sender, User receiver) {
        Integer idCounter = getMaximumId() + 1;
        FriendRequest friendRequest = new FriendRequest(idCounter, sender, receiver, LocalDateTime.now());
        friendRequestRepository.add(friendRequest);
    }

    public void removeFriendRequest(FriendRequest friendRequest) {
        friendRequestRepository.remove(friendRequest.getId());
    }
}
