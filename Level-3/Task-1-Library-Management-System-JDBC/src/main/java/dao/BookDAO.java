package dao;

import config.DBConnection;
import exception.DatabaseException;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookDAO {

    private static final String INSERT_BOOK = """
            INSERT INTO books (title, author, category, isbn, quantity, available_quantity)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_ALL = """
            SELECT book_id, title, author, category, isbn, quantity, available_quantity
            FROM books ORDER BY book_id
            """;

    private static final String SELECT_BY_ID = """
            SELECT book_id, title, author, category, isbn, quantity, available_quantity
            FROM books WHERE book_id = ?
            """;

    private static final String SEARCH_BOOKS = """
            SELECT book_id, title, author, category, isbn, quantity, available_quantity
            FROM books
            WHERE title LIKE ? OR author LIKE ? OR category LIKE ? OR isbn LIKE ?
            ORDER BY title
            """;

    private static final String UPDATE_BOOK = """
            UPDATE books SET title = ?, author = ?, category = ?, isbn = ?,
            quantity = ?, available_quantity = ? WHERE book_id = ?
            """;

    private static final String DELETE_BOOK = "DELETE FROM books WHERE book_id = ?";

    private static final String EXISTS_BY_ISBN = "SELECT 1 FROM books WHERE isbn = ?";

    private static final String DECREMENT_AVAILABLE = """
            UPDATE books SET available_quantity = available_quantity - 1
            WHERE book_id = ? AND available_quantity > 0
            """;

    private static final String INCREMENT_AVAILABLE = """
            UPDATE books SET available_quantity = available_quantity + 1
            WHERE book_id = ?
            """;

    private static final String COUNT_ACTIVE_BORROWS = """
            SELECT COUNT(*) FROM borrow_transactions
            WHERE book_id = ? AND status = 'BORROWED'
            """;


    public int insert(Book book) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getAvailableQuantity());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to insert book.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new DatabaseException("Failed to retrieve generated book ID.");
        } catch (SQLException exception) {
            throw mapSqlException(exception, "insert book");
        }
    }


    public List<Book> findAll() throws DatabaseException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                books.add(mapRow(resultSet));
            }
            return books;
        } catch (SQLException exception) {
            throw mapSqlException(exception, "fetch all books");
        }
    }


    public Optional<Book> findById(int bookId) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw mapSqlException(exception, "find book by ID");
        }
    }


    public List<Book> search(String keyword) throws DatabaseException {
        List<Book> books = new ArrayList<>();
        String pattern = "%" + keyword.trim() + "%";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_BOOKS)) {

            statement.setString(1, pattern);
            statement.setString(2, pattern);
            statement.setString(3, pattern);
            statement.setString(4, pattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(mapRow(resultSet));
                }
            }
            return books;
        } catch (SQLException exception) {
            throw mapSqlException(exception, "search books");
        }
    }


    public void update(Book book) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getAvailableQuantity());
            statement.setInt(7, book.getBookId());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Book not found for update. ID: " + book.getBookId());
            }
        } catch (SQLException exception) {
            throw mapSqlException(exception, "update book");
        }
    }


    public void delete(int bookId) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK)) {

            statement.setInt(1, bookId);
            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Book not found for deletion. ID: " + bookId);
            }
        } catch (SQLException exception) {
            throw mapSqlException(exception, "delete book");
        }
    }

    public boolean existsByIsbn(String isbn) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ISBN)) {

            statement.setString(1, isbn.trim());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw mapSqlException(exception, "check ISBN duplicate");
        }
    }


    public boolean decrementAvailable(Connection connection, int bookId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DECREMENT_AVAILABLE)) {
            statement.setInt(1, bookId);
            return statement.executeUpdate() > 0;
        }
    }

    public void incrementAvailable(Connection connection, int bookId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INCREMENT_AVAILABLE)) {
            statement.setInt(1, bookId);
            statement.executeUpdate();
        }
    }


    public int countActiveBorrows(int bookId) throws DatabaseException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ACTIVE_BORROWS)) {

            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            }
        } catch (SQLException exception) {
            throw mapSqlException(exception, "count active borrows");
        }
    }

    private Book mapRow(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getInt("book_id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getString("category"),
                resultSet.getString("isbn"),
                resultSet.getInt("quantity"),
                resultSet.getInt("available_quantity"));
    }

    private DatabaseException mapSqlException(SQLException exception, String operation) {
        if (exception.getMessage() != null && exception.getMessage().contains("Duplicate")) {
            return new DatabaseException("Duplicate ISBN detected.", exception);
        }
        return new DatabaseException("Database error during " + operation + ": " + exception.getMessage(), exception);
    }
}
