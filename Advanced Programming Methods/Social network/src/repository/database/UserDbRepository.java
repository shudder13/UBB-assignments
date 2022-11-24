package repository.database;

import exceptions.RepositoryException;
import model.entities.User;
import repository.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDbRepository implements Repository<Integer, User> {
    private final String url;
    private final String username;
    private final String password;

    public UserDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void add(User entity) throws RepositoryException, IOException {
        String SQLCommand = "INSERT INTO users (\"firstName\", \"lastName\", \"email\") VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getOne(Integer id) {
        String SQLCommand = "SELECT * FROM users WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                return new User(id, firstName, lastName, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = new ArrayList<>();
        String SQLCommand = "SELECT * FROM users";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(SQLCommand);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                User user = new User(id, firstName, lastName, email);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User entity) {
        // TODO
    }

    @Override
    public void remove(Integer id) {
        String SQLCommand = "DELETE FROM users WHERE id = ?";
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
        String SQLCommand = "DELETE FROM users";
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
