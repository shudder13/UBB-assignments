import repository.FriendshipRepository;
import repository.UserRepository;
import service.FriendshipService;
import service.SuperService;
import service.UserService;
import userinterface.UserInterface;
import validator.FriendshipValidator;
import validator.UserValidator;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        UserValidator userValidator = new UserValidator();
        FriendshipRepository friendshipRepository = new FriendshipRepository();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserService userService = new UserService(userRepository, userValidator);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository, friendshipValidator);
        SuperService superService = new SuperService(userService, friendshipService);
        UserInterface userInterface = new UserInterface(superService);
        userInterface.run();
    }
}