package repository.file;

import exceptions.RepositoryException;
import model.entities.Friendship;

import java.io.IOException;
import java.util.List;

public class FriendshipFileRepository extends FileRepository<Integer, Friendship> {
    private final UserFileRepository userFileRepository;

    public FriendshipFileRepository(String fileName, UserFileRepository userFileRepository) throws IOException, RepositoryException {
        super(fileName, false);
        this.userFileRepository = userFileRepository;
        super.loadData();
    }

    @Override
    public Friendship extractEntity(List<String> attributes) throws RepositoryException, IOException {
        // TODO add friendsFrom attribute
        // return new Friendship(Integer.parseInt(attributes.get(0)), userFileRepository.getOne(Integer.parseInt(attributes.get(1))), userFileRepository.getOne(Integer.parseInt(attributes.get(2))));
        return null;
    }

    @Override
    public String createEntityAsString(Friendship entity) {
        // TODO add friendsFrom attribute
        // return entity.getId().toString() + ';' + entity.getFirstUser().getId().toString() + ';' + entity.getSecondUser().getId().toString();
        return null;
    }
}
