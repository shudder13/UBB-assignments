import exceptions.RepositoryException;
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
        UserFileRepository userFileRepository = new UserFileRepository(Constants.USERS_FILENAME);
        UserValidator userValidator = new UserValidator();
        FriendshipFileRepository friendshipFileRepository = new FriendshipFileRepository(Constants.FRIENDSHIPS_FILENAME, userFileRepository);
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserService userService = new UserService(userFileRepository, userValidator);
        FriendshipService friendshipService = new FriendshipService(friendshipFileRepository, friendshipValidator);
        SuperService superService = new SuperService(userService, friendshipService);
        UserInterface userInterface = new UserInterface(superService);
        userInterface.run();
    }
}
