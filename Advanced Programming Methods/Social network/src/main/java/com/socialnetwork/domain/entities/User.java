package com.socialnetwork.domain.entities;

import java.util.Objects;

public class User implements Entity<Integer> {
    private final Integer id;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;


    public User(Integer id, String username, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "User " + id + ": " + username + " " + firstName + " " + lastName + " " + email;
    }
}
