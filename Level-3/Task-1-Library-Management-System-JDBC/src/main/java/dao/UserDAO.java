package dao;

import config.DBConnection;
import exception.DatabaseException;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserDAO {

    private static final String INSERT_USER = """
            INSERT INTO users (full_name, email, phone) VALUES (?, ?, ?)
            """;

    private static final String SELECT_ALL = """
            SELECT user_id, full_name, email, phone FROM users ORDER BY user_id
            """;

    private static final String SELECT_BY_ID = """
            SELECT user_id, full_name, email, phone FROM users WHERE user_id = ?
            """;

    private static final String EXISTS_BY_EMAIL = "SELECT 1 FROM users WHERE email = ?";

    public int insert(User user) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to register user.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new DatabaseException("Failed to retrieve generated user ID.");
        } catch (SQLException exception) {
            throw mapSqlException(exception, "register user");
        }
    }


    public List<User> findAll() throws DatabaseException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapRow(resultSet));
            }
            return users;
        } catch (SQLException exception) {
            throw mapSqlException(exception, "fetch all users");
        }
    }


    public Optional<User> findById(int userId) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw mapSqlException(exception, "find user by ID");
        }
    }


    public boolean existsByEmail(String email) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL)) {

            statement.setString(1, email.trim().toLowerCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw mapSqlException(exception, "check email duplicate");
        }
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("user_id"),
                resultSet.getString("full_name"),
                resultSet.getString("email"),
                resultSet.getString("phone"));
    }

    private DatabaseException mapSqlException(SQLException exception, String operation) {
        if (exception.getMessage() != null && exception.getMessage().contains("Duplicate")) {
            return new DatabaseException("Duplicate email address detected.", exception);
        }
        return new DatabaseException("Database error during " + operation + ": " + exception.getMessage(), exception);
    }
}
