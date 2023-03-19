package com.socialnetwork.controller;

import com.socialnetwork.domain.entities.User;
import com.socialnetwork.services.ServiceManager;
import com.socialnetwork.config.ApplicationContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AuthenticationController {
    private ServiceManager service;
    private FXMLLoader dashboardFXMLLoader;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Text incorrectCredentialsText;

    @FXML
    void signInButtonClick(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        Optional<User> user = service.login(username, password);
        if (user.isEmpty())
            incorrectCredentialsText.setVisible(true);
        else {
            ApplicationContext.getInstance().setUser(user.get());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            Scene dashboardScene;
            try {
                dashboardScene = new Scene(dashboardFXMLLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            DashboardController dashboardController = dashboardFXMLLoader.getController();
            dashboardController.setService(service);

            stage.setScene(dashboardScene);
            stage.setTitle("Dashboard");
            stage.show();
        }
    }

    public void setDashboardFXMLLoader(FXMLLoader dashboardFXMLLoader) {
        this.dashboardFXMLLoader = dashboardFXMLLoader;
    }

    public void setService(ServiceManager service) {
        this.service = service;
    }
}
