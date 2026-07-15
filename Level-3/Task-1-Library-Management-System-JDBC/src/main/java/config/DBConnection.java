package config;

import exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DBConnection {


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

    private DBConnection() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }


    public static Connection getConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException exception) {
            throw new DatabaseException(buildConnectionFailureMessage(exception), exception);
        }
    }


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

    private static String buildConnectionFailureMessage(SQLException exception) {
        return "Failed to connect to database."
                + " URL=" + DB_URL
                + " | User=" + DB_USER
                + " | SQLState=" + exception.getSQLState()
                + " | ErrorCode=" + exception.getErrorCode()
                + " | Message=" + exception.getMessage();
    }
}
