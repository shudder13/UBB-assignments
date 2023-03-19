package com.socialnetwork.repository;

import com.socialnetwork.domain.entities.FriendRequest;
import com.socialnetwork.domain.entities.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class FriendRequestRepository implements Repository<Integer, FriendRequest> {
    private final String url;
    private final String username;
    private final String password;
    private final UserRepository userRepository;

    public FriendRequestRepository(String url, String username, String password, UserRepository userRepository) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userRepository = userRepository;
    }

    @Override
    public void add(FriendRequest entity) {
        String SQLCommand = "INSERT INTO \"friendRequests\" (\"senderId\", \"receiverId\", \"date\") VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, entity.getSender().getId());
            statement.setInt(2, entity.getReceiver().getId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FriendRequest getOne(Integer id) {
        String SQLCommand = "SELECT * FROM \"friendRequests\" WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int senderId = resultSet.getInt("senderId");
                int receiverId = resultSet.getInt("receiverId");
                User sender = userRepository.getOne(senderId);
                User receiver = userRepository.getOne(receiverId);
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                return new FriendRequest(id, sender, receiver, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<FriendRequest> getAll() {
        Collection<FriendRequest> friendRequests = new ArrayList<>();
        String SQLCommand = "SELECT * FROM \"friendRequests\"";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer senderId = resultSet.getInt("senderId");
                Integer receiverId = resultSet.getInt("receiverId");
                User sender = userRepository.getOne(senderId);
                User receiver = userRepository.getOne(receiverId);
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                FriendRequest friendRequest = new FriendRequest(id, sender, receiver, date);
                friendRequests.add(friendRequest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequests;
    }

    public Collection<FriendRequest> getAllOfUser(User user) {
        Collection<FriendRequest> friendRequests = new ArrayList<>();
        String SQLCommand = "SELECT * FROM \"friendRequests\" WHERE \"receiverId\" = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer senderId = resultSet.getInt("senderId");
                Integer receiverId = resultSet.getInt("receiverId");
                User sender = userRepository.getOne(senderId);
                User receiver = userRepository.getOne(receiverId);
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                FriendRequest friendRequest = new FriendRequest(id, sender, receiver, date);
                friendRequests.add(friendRequest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequests;
    }

    @Override
    public void update(FriendRequest entity) {
        // TODO
    }

    @Override
    public void remove(Integer id) {
        String SQLCommand = "DELETE FROM \"friendRequests\" WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        String SQLCommand = "DELETE FROM \"friendRequests\"";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
