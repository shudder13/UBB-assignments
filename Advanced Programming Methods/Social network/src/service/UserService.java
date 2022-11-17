package service;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.entities.User;
import repository.file.UserFileRepository;
import validator.UserValidator;

import java.io.IOException;
import java.util.Collection;

public class UserService {
    private final UserFileRepository userFileRepository;
    private final UserValidator userValidator;

    public UserService(UserFileRepository userFileRepository, UserValidator userValidator) {
        this.userFileRepository = userFileRepository;
        this.userValidator = userValidator;
    }

    private Integer getMaximumId() throws RepositoryException, IOException {
        Collection<User> users = userFileRepository.getAll();
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
        userFileRepository.add(user);
    }

    public void removeUser(Integer id) throws RepositoryException, IOException {
        userFileRepository.remove(id);
    }

    public User getUser(Integer id) throws RepositoryException, IOException {
        return userFileRepository.getOne(id);
    }

    public Collection<User> getUsers() throws RepositoryException, IOException {
        return userFileRepository.getAll();
    }
}
