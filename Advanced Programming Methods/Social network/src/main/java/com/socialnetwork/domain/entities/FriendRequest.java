package com.socialnetwork.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class FriendRequest implements Entity<Integer> {
    private final Integer id;
    private final User sender;
    private final User receiver;
    private final LocalDateTime date;

    public FriendRequest(Integer id, User sender, User receiver, LocalDateTime date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return id.equals(that.id) || (sender.equals(that.sender) && receiver.equals(that.receiver));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver, date);
    }
}
