package repository.database;

import exceptions.RepositoryException;
import model.entities.Friendship;
import model.entities.User;
import repository.Repository;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class FriendshipDbRepository implements Repository<Integer, Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final UserDbRepository userDbRepository;

    public FriendshipDbRepository(String url, String username, String password, UserDbRepository userDbRepository) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userDbRepository = userDbRepository;
    }

    @Override
    public void add(Friendship entity) throws RepositoryException, IOException {
        String SQLCommand = "INSERT INTO friendships (\"firstUserID\", \"secondUserID\", \"friendsFrom\") VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, entity.getFirstUser().getId());
            statement.setInt(2, entity.getSecondUser().getId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getFriendsFrom()));
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
                int firstUserID = resultSet.getInt("firstUserID");
                int secondUserID = resultSet.getInt("secondUserID");
                User firstUser = userDbRepository.getOne(firstUserID);
                User secondUser = userDbRepository.getOne(secondUserID);
                LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                return new Friendship(id, firstUser, secondUser, friendsFrom);
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
                Integer firstUserID = resultSet.getInt("firstUserID");
                Integer secondUserID = resultSet.getInt("secondUserID");
                User firstUser = userDbRepository.getOne(firstUserID);
                User secondUser = userDbRepository.getOne(secondUserID);
                LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                Friendship friendship = new Friendship(id, firstUser, secondUser, friendsFrom);
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
