package com.socialnetwork.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship implements Entity<Integer> {
    private final Integer id;
    private final User firstUser;
    private final User secondUser;
    private final LocalDateTime friendsSince;

    public Friendship(Integer id, User firstUser, User secondUser, LocalDateTime friendsSince) {
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.friendsSince = friendsSince;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public LocalDateTime getFriendsSince() {
        return friendsSince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        boolean same_order = firstUser.equals(that.firstUser) && secondUser.equals(that.secondUser);
        boolean reverse_order = firstUser.equals(that.secondUser) && secondUser.equals(that.firstUser);
        return Objects.equals(id, that.id) || same_order || reverse_order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstUser, secondUser, friendsSince);
    }

    @Override
    public String toString() {
        return "Friendship " + id + ": {" + firstUser.toString() + "} {" + secondUser.toString() + "} since " + friendsSince.toString();
    }
}
