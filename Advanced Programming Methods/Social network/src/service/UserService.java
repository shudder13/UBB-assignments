package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.entities.User;
import repository.database.UserDbRepository;
import validator.UserValidator;

import java.io.IOException;
import java.util.Collection;

public class UserService {
    private final UserDbRepository userDbRepository;
    private final UserValidator userValidator;

    public UserService(UserDbRepository userDbRepository, UserValidator userValidator) {
        this.userDbRepository = userDbRepository;
        this.userValidator = userValidator;
    }

    private Integer getMaximumId() {
        Collection<User> users = userDbRepository.getAll();
        if (users.isEmpty())
            return -1;
        Integer maximumId = 0;
        for (User user : users)
            if (user.getId() > maximumId)
                maximumId = user.getId();
        return maximumId;
    }

    public void addUser(String firstName, String lastName, String email) throws ValidationException, RepositoryException, IOException {
        Integer idCounter = getMaximumId() + 1;
        User user = new User(idCounter, firstName, lastName, email);
        userValidator.validate(user);
        userDbRepository.add(user);
    }

    public void removeUser(Integer id) {
        userDbRepository.remove(id);
    }

    public User getUser(Integer id) {
        return userDbRepository.getOne(id);
    }

    public Collection<User> getUsers() {
        return userDbRepository.getAll();
    }
}
