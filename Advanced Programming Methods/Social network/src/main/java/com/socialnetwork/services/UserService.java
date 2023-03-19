package com.socialnetwork.services;

import com.socialnetwork.domain.entities.User;
import com.socialnetwork.domain.exceptions.ValidationException;
import com.socialnetwork.domain.validators.UserValidator;
import com.socialnetwork.repository.UserRepository;
import java.util.Collection;

public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    private Integer getMaximumId() {
        Collection<User> users = userRepository.getAll();
        if (users.isEmpty())
            return -1;
        Integer maximumId = 0;
        for (User user : users)
            if (user.getId() > maximumId)
                maximumId = user.getId();
        return maximumId;
    }

    public void addUser(String username, String password, String firstName, String lastName, String email) throws ValidationException {
        Integer idCounter = getMaximumId() + 1;
        User user = new User(idCounter, username, password, firstName, lastName, email);
        userValidator.validate(user);
        userRepository.add(user);
    }

    public void removeUser(Integer id) {
        userRepository.remove(id);
    }

    public User getUser(Integer id) {
        return userRepository.getOne(id);
    }

    public Collection<User> getUsers() {
        return userRepository.getAll();
    }
}
