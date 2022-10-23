package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.entities.User;
import repository.UserRepository;
import validator.UserValidator;

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

    public void addUser(String firstName, String lastName) throws ValidationException, RepositoryException {
        Integer idCounter = getMaximumId() + 1;
        User user = new User(idCounter, firstName, lastName);
        userValidator.validate(user);
        userRepository.add(user);
    }

    public void removeUser(Integer id) throws RepositoryException {
        userRepository.remove(id);
    }

    public User getUser(Integer id) {
        return userRepository.getOne(id);
    }

    public Collection<User> getUsers() {
        return userRepository.getAll();
    }
}
