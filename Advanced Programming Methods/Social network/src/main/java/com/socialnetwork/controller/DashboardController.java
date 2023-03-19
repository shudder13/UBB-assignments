package com.socialnetwork.controller;

import com.socialnetwork.domain.entities.FriendRequest;
import com.socialnetwork.domain.entities.Friendship;
import com.socialnetwork.domain.entities.User;
import com.socialnetwork.services.ServiceManager;
import com.socialnetwork.config.ApplicationContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DashboardController {
    private ServiceManager service;
    private User currentUser;
    private ObservableList<User> friends = FXCollections.observableArrayList();
    private ObservableList<User> searchResults = FXCollections.observableArrayList();
    private ObservableList<FriendRequest> friendRequests = FXCollections.observableArrayList();

    @FXML
    private Text helloText;

    @FXML
    private ListView<User> friendsListView;

    @FXML
    private ListView<User> searchUserListView;

    @FXML
    private ListView<FriendRequest> friendRequestsListView;

    @FXML
    private TextField searchUserTextField;

    @FXML
    private Button addFriendButton;

    @FXML
    private Button removeFriendButton;

    @FXML
    private Button deleteFriendRequestButton;

    @FXML
    private Button acceptFriendRequestButton;

    private void setListViewCellFactoryForUsers(ListView<User> listView) {
        listView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                ListCell<User> listCell = new ListCell<User>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        }
                        else {
                            setText(item.getFullName());
                        }
                    }
                };
                return listCell;
            }
        });
    }

    private void setListViewCellFactoryForFriendRequests(ListView<FriendRequest> listView) {
        listView.setCellFactory(new Callback<ListView<FriendRequest>, ListCell<FriendRequest>>() {
            @Override
            public ListCell<FriendRequest> call(ListView<FriendRequest> param) {
                ListCell<FriendRequest> listCell = new ListCell<FriendRequest>() {
                    @Override
                    protected void updateItem(FriendRequest item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        }
                        else {
                            setText(item.getSender().getFullName());
                        }
                    }
                };
                return listCell;
            }
        });
    }

    @FXML
    public void initialize() {
        currentUser = ApplicationContext.getInstance().getUser();
        helloText.setText("Good afternoon, " + currentUser.getFullName() + "!");

        setListViewCellFactoryForUsers(friendsListView);
        friendsListView.setItems(friends);

        setListViewCellFactoryForUsers(searchUserListView);
        searchUserListView.setItems(searchResults);

        setListViewCellFactoryForFriendRequests(friendRequestsListView);
        friendRequestsListView.setItems(friendRequests);

        searchUserTextField.textProperty().addListener(o -> updateSearchUserListView());
    }

    private void updateSearchUserListView() {
        Predicate<User> nameStartsWith = user -> user.getFullName().startsWith(searchUserTextField.getText()) ||
                user.getLastName().startsWith(searchUserTextField.getText());
        Predicate<User> isStranger = user -> !friends.contains(user) && !user.equals(currentUser);

        searchResults.setAll(service.getUsers().stream()
                .filter(nameStartsWith.and(isStranger))
                .collect(Collectors.toList()));
    }

    @FXML
    void addFriendButtonClick(ActionEvent event) {
        User user = searchUserListView.getSelectionModel().getSelectedItem();
        service.requestFriendship(currentUser, user);
    }

    @FXML
    void removeFriendButtonClick(ActionEvent event) {
        User user = friendsListView.getSelectionModel().getSelectedItem();
        Friendship friendship = service.getFriendshipOfUsers(currentUser.getId(), user.getId());
        service.removeFriendship(friendship.getId());
        friends.remove(user);
        updateSearchUserListView();
        removeFriendButton.setDisable(true);
    }

    @FXML
    void friendsListViewMouseClicked() {
        removeFriendButton.setDisable(false);
    }

    @FXML
    void searchUserListViewMouseClicked() {
        addFriendButton.setDisable(false);
    }

    @FXML
    void friendRequestsListViewMouseClicked() {
        acceptFriendRequestButton.setDisable(false);
        deleteFriendRequestButton.setDisable(false);
    }

    @FXML
    void acceptFriendRequestButtonClick(ActionEvent event) {
        FriendRequest friendRequest = friendRequestsListView.getSelectionModel().getSelectedItem();
        service.acceptFriendRequest(friendRequest);
        friendRequests.remove(friendRequest);
        friends.add(friendRequest.getSender());
        acceptFriendRequestButton.setDisable(false);
        deleteFriendRequestButton.setDisable(false);
    }

    @FXML
    void deleteFriendRequestButtonClick(ActionEvent event) {
        FriendRequest friendRequest = friendRequestsListView.getSelectionModel().getSelectedItem();
        service.deleteFriendRequest(friendRequest);
        friendRequests.remove(friendRequest);
        acceptFriendRequestButton.setDisable(false);
        deleteFriendRequestButton.setDisable(false);
    }

    public void setService(ServiceManager service) {
        this.service = service;
        friends.setAll(service.getFriendsOfUser(currentUser).stream().toList());
        Predicate<User> isStranger = user -> !friends.contains(user) && !user.equals(currentUser);
        searchResults.setAll(service.getUsers().stream().filter(isStranger).collect(Collectors.toList()));
        friendRequests.setAll(service.getFriendshipRequestsOfUser(currentUser));
    }
}
