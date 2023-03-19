package com.socialnetwork.repository;

import com.socialnetwork.domain.entities.Friendship;
import com.socialnetwork.domain.entities.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class FriendshipRepository implements Repository<Integer, Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final UserRepository userRepository;

    public FriendshipRepository(String url, String username, String password, UserRepository userRepository) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userRepository = userRepository;
    }

    @Override
    public void add(Friendship entity) {
        String SQLCommand = "INSERT INTO friendships (\"firstUserId\", \"secondUserId\", \"friendsSince\") VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, entity.getFirstUser().getId());
            statement.setInt(2, entity.getSecondUser().getId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getFriendsSince()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Friendship getOne(Integer id) {
        String SQLCommand = "SELECT * FROM friendships WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int firstUserId = resultSet.getInt("firstUserId");
                int secondUserId = resultSet.getInt("secondUserId");
                User firstUser = userRepository.getOne(firstUserId);
                User secondUser = userRepository.getOne(secondUserId);
                LocalDateTime friendsSince = resultSet.getTimestamp("friendsSince").toLocalDateTime();
                return new Friendship(id, firstUser, secondUser, friendsSince);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Friendship> getAll() {
        Collection<Friendship> friendships = new ArrayList<>();
        String SQLCommand = "SELECT * FROM friendships";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer firstUserId = resultSet.getInt("firstUserId");
                Integer secondUserId = resultSet.getInt("secondUserId");
                User firstUser = userRepository.getOne(firstUserId);
                User secondUser = userRepository.getOne(secondUserId);
                LocalDateTime friendsSince = resultSet.getTimestamp("friendsSince").toLocalDateTime();
                Friendship friendship = new Friendship(id, firstUser, secondUser, friendsSince);
                friendships.add(friendship);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public void update(Friendship entity) {
        // TODO
    }

    @Override
    public void remove(Integer id) {
        String SQLCommand = "DELETE FROM friendships WHERE id = ?";
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
        String SQLCommand = "DELETE FROM friendships";
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
