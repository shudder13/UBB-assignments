package com.socialnetwork;
import com.socialnetwork.controller.AuthenticationController;
import com.socialnetwork.domain.validators.FriendshipValidator;
import com.socialnetwork.domain.validators.UserValidator;
import com.socialnetwork.repository.FriendRequestRepository;
import com.socialnetwork.repository.FriendshipRepository;
import com.socialnetwork.repository.UserRepository;
import com.socialnetwork.services.FriendRequestService;
import com.socialnetwork.services.FriendshipService;
import com.socialnetwork.services.ServiceManager;
import com.socialnetwork.services.UserService;
import com.socialnetwork.config.ApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties properties = ApplicationContext.getInstance().getProperties();
        String databaseURL = properties.getProperty("databaseURL");
        String databaseUsername = properties.getProperty("databaseUsername");
        String databasePassword = properties.getProperty("databasePassword");

        UserRepository userRepository = new UserRepository(databaseURL, databaseUsername, databasePassword);
        UserValidator userValidator = new UserValidator();
        FriendshipRepository friendshipRepository = new FriendshipRepository(databaseURL, databaseUsername, databasePassword, userRepository);
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        FriendRequestRepository friendRequestRepository = new FriendRequestRepository(databaseURL, databaseUsername, databasePassword, userRepository);
        UserService userService = new UserService(userRepository, userValidator);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository, friendshipValidator);
        FriendRequestService friendRequestService = new FriendRequestService(friendRequestRepository);

        FXMLLoader authenticationFXMLLoader = new FXMLLoader(getClass().getResource("views/authentication.fxml"));
        Scene authenticationScene = new Scene(authenticationFXMLLoader.load());
        AuthenticationController authenticationController = authenticationFXMLLoader.getController();
        authenticationController.setService(new ServiceManager(userService, friendshipService, friendRequestService));

        FXMLLoader dashboardFXMLLoader = new FXMLLoader(getClass().getResource("views/dashboard.fxml"));
        authenticationController.setDashboardFXMLLoader(dashboardFXMLLoader);

        stage.setScene(authenticationScene);
        stage.setTitle("Log In");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
