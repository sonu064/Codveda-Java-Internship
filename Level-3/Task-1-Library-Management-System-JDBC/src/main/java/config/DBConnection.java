package config;

import exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages JDBC database connections for the Library Management System.
 * <p>
 * Provides a centralized connection factory using try-with-resources pattern
 * at the call site. Credentials should be configured before running.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class DBConnection {

    /**
     * JDBC URL with MySQL 8-compatible parameters.
     * <ul>
     *   <li>{@code allowPublicKeyRetrieval=true} — required for caching_sha2_password</li>
     *   <li>{@code useSSL=false} — avoids SSL handshake failures on local setups</li>
     *   <li>{@code serverTimezone=UTC} — avoids timezone conversion errors</li>
     * </ul>
     */
    private static final String DB_URL =
            "jdbc:mysql://127.0.0.1:3306/library_management"
                    + "?allowPublicKeyRetrieval=true"
                    + "&useSSL=false"
                    + "&serverTimezone=UTC";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mily@20564";

    private static final String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(MYSQL_DRIVER_CLASS);
        } catch (ClassNotFoundException exception) {
            throw new ExceptionInInitializerError(
                    new DatabaseException(
                            "MySQL JDBC driver not found on classpath. "
                                    + "Verify mysql-connector-j is declared in pom.xml.",
                            exception));
        }
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private DBConnection() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Returns a new database connection.
     *
     * @return active JDBC connection
     * @throws DatabaseException if the connection cannot be established
     */
    public static Connection getConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException exception) {
            throw new DatabaseException(buildConnectionFailureMessage(exception), exception);
        }
    }

    /**
     * Tests the database connection on application startup.
     *
     * @throws DatabaseException if the connection test fails
     */
    public static void testConnection() throws DatabaseException {
        try (Connection connection = getConnection()) {
            if (!connection.isValid(5)) {
                throw new DatabaseException(
                        "Database connection opened but failed validation (isValid returned false).");
            }
        } catch (SQLException exception) {
            throw new DatabaseException(buildConnectionFailureMessage(exception), exception);
        }
    }

    /**
     * Builds a detailed failure message including SQLState and error code.
     *
     * @param exception the SQLException
     * @return detailed message for console debugging
     */
    private static String buildConnectionFailureMessage(SQLException exception) {
        return "Failed to connect to database."
                + " URL=" + DB_URL
                + " | User=" + DB_USER
                + " | SQLState=" + exception.getSQLState()
                + " | ErrorCode=" + exception.getErrorCode()
                + " | Message=" + exception.getMessage();
    }
}
