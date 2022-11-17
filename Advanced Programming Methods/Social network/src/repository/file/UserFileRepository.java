package repository.file;

import exceptions.RepositoryException;
import model.entities.User;

import java.io.IOException;
import java.util.List;

public class UserFileRepository extends FileRepository<Integer, User> {
    public UserFileRepository(String fileName) throws IOException, RepositoryException {
        super(fileName);
    }

    @Override
    public User extractEntity(List<String> attributes) {
        return new User(Integer.parseInt(attributes.get(0)), attributes.get(1), attributes.get(2), attributes.get(3));
    }

    @Override
    public String createEntityAsString(User entity) {
        return entity.getId().toString() + ';' + entity.getFirstName() + ';' + entity.getLastName() + ';' + entity.getEmail();
    }
}
