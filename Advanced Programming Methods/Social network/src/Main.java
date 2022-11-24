import exceptions.RepositoryException;
import repository.database.FriendshipDbRepository;
import repository.database.UserDbRepository;
import repository.file.FriendshipFileRepository;
import repository.file.UserFileRepository;
import service.FriendshipService;
import service.SuperService;
import service.UserService;
import userinterface.UserInterface;
import utils.Constants;
import validator.FriendshipValidator;
import validator.UserValidator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws RepositoryException, IOException {
        UserDbRepository userDbRepository = new UserDbRepository(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        UserValidator userValidator = new UserValidator();
        FriendshipDbRepository friendshipDbRepository = new FriendshipDbRepository(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD, userDbRepository);
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserService userService = new UserService(userDbRepository, userValidator);
        FriendshipService friendshipService = new FriendshipService(friendshipDbRepository, friendshipValidator);
        SuperService superService = new SuperService(userService, friendshipService);
        UserInterface userInterface = new UserInterface(superService);
        userInterface.run();
    }
}
