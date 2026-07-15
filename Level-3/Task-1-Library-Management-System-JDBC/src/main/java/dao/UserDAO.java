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

/**
 * Data Access Object for user-related database operations.
 *
 * @author Sonu Singh
 * @version 1.0
 */
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

    /**
     * Inserts a new user into the database.
     *
     * @param user the user to insert
     * @return the generated user ID
     * @throws DatabaseException if the insert fails
     */
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

    /**
     * Returns all users from the database.
     *
     * @return list of users
     * @throws DatabaseException if the query fails
     */
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

    /**
     * Finds a user by ID.
     *
     * @param userId the user ID
     * @return optional user
     * @throws DatabaseException if the query fails
     */
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

    /**
     * Checks whether an email already exists.
     *
     * @param email the email to check
     * @return {@code true} if duplicate exists
     * @throws DatabaseException if the query fails
     */
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

    /**
     * Maps a ResultSet row to a User object.
     *
     * @param resultSet the result set
     * @return mapped user
     * @throws SQLException if column access fails
     */
    private User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("user_id"),
                resultSet.getString("full_name"),
                resultSet.getString("email"),
                resultSet.getString("phone"));
    }

    /**
     * Maps SQLException to DatabaseException with duplicate detection.
     *
     * @param exception the SQL exception
     * @param operation operation description
     * @return mapped database exception
     */
    private DatabaseException mapSqlException(SQLException exception, String operation) {
        if (exception.getMessage() != null && exception.getMessage().contains("Duplicate")) {
            return new DatabaseException("Duplicate email address detected.", exception);
        }
        return new DatabaseException("Database error during " + operation + ": " + exception.getMessage(), exception);
    }
}
