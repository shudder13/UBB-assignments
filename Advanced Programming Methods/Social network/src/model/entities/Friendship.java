package model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship implements Entity<Integer> {
    private final Integer id;
    private final User firstUser;
    private final User secondUser;
    private final LocalDateTime friendsFrom;

    public Friendship(Integer id, User firstUser, User secondUser, LocalDateTime friendsFrom) {
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.friendsFrom = friendsFrom;
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

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
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
        return Objects.hash(id, firstUser, secondUser, friendsFrom);
    }

    @Override
    public String toString() {
        return "Friendship " + id + ": {" + firstUser.toString() + "} {" + secondUser.toString() + "} since " + friendsFrom.toString();
    }
}
