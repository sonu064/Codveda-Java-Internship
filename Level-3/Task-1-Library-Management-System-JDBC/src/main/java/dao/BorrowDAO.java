package dao;

import config.DBConnection;
import exception.DatabaseException;
import model.BorrowTransaction;
import model.BorrowTransaction.Status;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for borrow/return transaction operations.
 * <p>
 * Supports JDBC transactions with commit and rollback for borrow and return.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class BorrowDAO {

    private static final String INSERT_BORROW = """
            INSERT INTO borrow_transactions (user_id, book_id, borrow_date, status)
            VALUES (?, ?, ?, 'BORROWED')
            """;

    private static final String RETURN_BOOK = """
            UPDATE borrow_transactions
            SET return_date = ?, status = 'RETURNED'
            WHERE transaction_id = ? AND status = 'BORROWED'
            """;

    private static final String SELECT_ALL_HISTORY = """
            SELECT bt.transaction_id, bt.user_id, bt.book_id,
                   u.full_name, b.title,
                   bt.borrow_date, bt.return_date, bt.status
            FROM borrow_transactions bt
            JOIN users u ON bt.user_id = u.user_id
            JOIN books b ON bt.book_id = b.book_id
            ORDER BY bt.transaction_id DESC
            """;

    private static final String SELECT_BY_USER = """
            SELECT bt.transaction_id, bt.user_id, bt.book_id,
                   u.full_name, b.title,
                   bt.borrow_date, bt.return_date, bt.status
            FROM borrow_transactions bt
            JOIN users u ON bt.user_id = u.user_id
            JOIN books b ON bt.book_id = b.book_id
            WHERE bt.user_id = ?
            ORDER BY bt.transaction_id DESC
            """;

    private static final String SELECT_ACTIVE_BY_USER_BOOK = """
            SELECT transaction_id, user_id, book_id, borrow_date, return_date, status
            FROM borrow_transactions
            WHERE user_id = ? AND book_id = ? AND status = 'BORROWED'
            LIMIT 1
            """;

    private static final String SELECT_BY_ID = """
            SELECT bt.transaction_id, bt.user_id, bt.book_id,
                   u.full_name, b.title,
                   bt.borrow_date, bt.return_date, bt.status
            FROM borrow_transactions bt
            JOIN users u ON bt.user_id = u.user_id
            JOIN books b ON bt.book_id = b.book_id
            WHERE bt.transaction_id = ?
            """;

    private final BookDAO bookDAO;

    /**
     * Creates a BorrowDAO with a BookDAO dependency for transactional updates.
     *
     * @param bookDAO the book data access object
     */
    public BorrowDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Borrows a book using a JDBC transaction (decrement available + insert borrow record).
     *
     * @param userId user ID
     * @param bookId book ID
     * @return the new transaction ID
     * @throws DatabaseException if borrow fails or no copies available
     */
    public int borrowBook(int userId, int bookId) throws DatabaseException {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            if (!bookDAO.decrementAvailable(connection, bookId)) {
                connection.rollback();
                throw new DatabaseException("Book Not Available");
            }

            int transactionId;
            try (PreparedStatement statement = connection.prepareStatement(
                    INSERT_BORROW, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, userId);
                statement.setInt(2, bookId);
                statement.setDate(3, Date.valueOf(LocalDate.now()));

                int rows = statement.executeUpdate();
                if (rows == 0) {
                    connection.rollback();
                    throw new DatabaseException("Failed to create borrow transaction.");
                }

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (!keys.next()) {
                        connection.rollback();
                        throw new DatabaseException("Failed to retrieve transaction ID.");
                    }
                    transactionId = keys.getInt(1);
                }
            }

            connection.commit();
            return transactionId;
        } catch (SQLException exception) {
            rollbackQuietly(connection);
            throw new DatabaseException("Database error during borrow: " + exception.getMessage(), exception);
        } finally {
            restoreAutoCommit(connection);
            closeQuietly(connection);
        }
    }

    /**
     * Returns a borrowed book using a JDBC transaction.
     *
     * @param transactionId the borrow transaction ID
     * @throws DatabaseException if return fails
     */
    public void returnBook(int transactionId) throws DatabaseException {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            Optional<BorrowTransaction> active = findActiveTransaction(connection, transactionId);
            if (active.isEmpty()) {
                connection.rollback();
                throw new DatabaseException("No active borrow found for transaction ID: " + transactionId);
            }

            BorrowTransaction transaction = active.get();

            try (PreparedStatement statement = connection.prepareStatement(RETURN_BOOK)) {
                statement.setDate(1, Date.valueOf(LocalDate.now()));
                statement.setInt(2, transactionId);

                int rows = statement.executeUpdate();
                if (rows == 0) {
                    connection.rollback();
                    throw new DatabaseException("Failed to update return transaction.");
                }
            }

            bookDAO.incrementAvailable(connection, transaction.getBookId());
            connection.commit();
        } catch (SQLException exception) {
            rollbackQuietly(connection);
            throw new DatabaseException("Database error during return: " + exception.getMessage(), exception);
        } finally {
            restoreAutoCommit(connection);
            closeQuietly(connection);
        }
    }

    /**
     * Returns all borrow history with joined user and book names.
     *
     * @return list of transactions
     * @throws DatabaseException if the query fails
     */
    public List<BorrowTransaction> findAllHistory() throws DatabaseException {
        return executeHistoryQuery(SELECT_ALL_HISTORY, null);
    }

    /**
     * Returns borrow history for a specific user.
     *
     * @param userId the user ID
     * @return list of transactions
     * @throws DatabaseException if the query fails
     */
    public List<BorrowTransaction> findHistoryByUser(int userId) throws DatabaseException {
        return executeHistoryQuery(SELECT_BY_USER, userId);
    }

    /**
     * Finds an active borrow transaction by transaction ID.
     *
     * @param transactionId transaction ID
     * @return optional active transaction
     * @throws DatabaseException if the query fails
     */
    public Optional<BorrowTransaction> findById(int transactionId) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, transactionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapHistoryRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Database error finding transaction: " + exception.getMessage(), exception);
        }
    }

    /**
     * Executes a history query with an optional user filter.
     *
     * @param sql    the SQL query
     * @param userId user ID filter, or {@code null} for all
     * @return list of transactions
     * @throws DatabaseException if the query fails
     */
    private List<BorrowTransaction> executeHistoryQuery(String sql, Integer userId) throws DatabaseException {
        List<BorrowTransaction> history = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (userId != null) {
                statement.setInt(1, userId);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    history.add(mapHistoryRow(resultSet));
                }
            }
            return history;
        } catch (SQLException exception) {
            throw new DatabaseException("Database error fetching history: " + exception.getMessage(), exception);
        }
    }

    /**
     * Finds an active (BORROWED) transaction by ID within a connection.
     *
     * @param connection    active connection
     * @param transactionId transaction ID
     * @return optional active transaction
     * @throws SQLException if the query fails
     */
    private Optional<BorrowTransaction> findActiveTransaction(Connection connection, int transactionId)
            throws SQLException {
        String sql = """
                SELECT transaction_id, user_id, book_id, borrow_date, return_date, status
                FROM borrow_transactions
                WHERE transaction_id = ? AND status = 'BORROWED'
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transactionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapBasicRow(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    /**
     * Maps a joined history ResultSet row to a BorrowTransaction.
     *
     * @param resultSet the result set
     * @return mapped transaction
     * @throws SQLException if column access fails
     */
    private BorrowTransaction mapHistoryRow(ResultSet resultSet) throws SQLException {
        BorrowTransaction transaction = mapBasicRow(resultSet);
        transaction.setUserName(resultSet.getString("full_name"));
        transaction.setBookTitle(resultSet.getString("title"));
        return transaction;
    }

    /**
     * Maps a basic ResultSet row to a BorrowTransaction.
     *
     * @param resultSet the result set
     * @return mapped transaction
     * @throws SQLException if column access fails
     */
    private BorrowTransaction mapBasicRow(ResultSet resultSet) throws SQLException {
        Date returnDate = resultSet.getDate("return_date");
        BorrowTransaction transaction = new BorrowTransaction(
                resultSet.getInt("transaction_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("book_id"),
                resultSet.getDate("borrow_date").toLocalDate(),
                returnDate != null ? returnDate.toLocalDate() : null,
                Status.valueOf(resultSet.getString("status")));
        return transaction;
    }

    /**
     * Rolls back a connection quietly on error.
     *
     * @param connection the connection
     */
    private void rollbackQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
                // Rollback failure is secondary to the original error.
            }
        }
    }

    /**
     * Restores auto-commit on a connection.
     *
     * @param connection the connection
     */
    private void restoreAutoCommit(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
                // Best-effort cleanup.
            }
        }
    }

    /**
     * Closes a connection quietly.
     *
     * @param connection the connection
     */
    private void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                // Best-effort cleanup.
            }
        }
    }
}
