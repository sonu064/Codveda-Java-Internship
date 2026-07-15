package service;

import dao.BookDAO;
import dao.BorrowDAO;
import dao.UserDAO;
import exception.BookNotFoundException;
import exception.DatabaseException;
import exception.UserNotFoundException;
import model.Book;
import model.BorrowTransaction;
import model.User;
import util.IdGenerator;

import java.util.List;

/**
 * Service layer orchestrating library business logic.
 * <p>
 * Delegates persistence to DAO classes. Contains no console I/O.
 * Uses constructor injection for dependencies.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class LibraryService {

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final BorrowDAO borrowDAO;

    /**
     * Creates a library service with injected DAO dependencies.
     *
     * @param bookDAO   book data access object
     * @param userDAO   user data access object
     * @param borrowDAO borrow data access object
     */
    public LibraryService(BookDAO bookDAO, UserDAO userDAO, BorrowDAO borrowDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        this.borrowDAO = borrowDAO;
    }

    /**
     * Adds a new book to the library.
     *
     * @param book the book to add
     * @return the created book with generated ID
     * @throws DatabaseException if insert fails or ISBN is duplicate
     */
    public Book addBook(Book book) throws DatabaseException {
        if (bookDAO.existsByIsbn(book.getIsbn())) {
            throw new DatabaseException("Duplicate ISBN: " + book.getIsbn());
        }
        int bookId = bookDAO.insert(book);
        book.setBookId(bookId);
        return book;
    }

    /**
     * Returns all books in the library.
     *
     * @return list of books
     * @throws DatabaseException if query fails
     */
    public List<Book> getAllBooks() throws DatabaseException {
        return bookDAO.findAll();
    }

    /**
     * Searches books by keyword.
     *
     * @param keyword search term
     * @return matching books
     * @throws DatabaseException if query fails
     */
    public List<Book> searchBooks(String keyword) throws DatabaseException {
        return bookDAO.search(keyword);
    }

    /**
     * Updates an existing book.
     *
     * @param book the book with updated fields
     * @return the updated book
     * @throws BookNotFoundException if book not found
     * @throws DatabaseException     if update fails
     */
    public Book updateBook(Book book) throws BookNotFoundException, DatabaseException {
        if (bookDAO.findById(book.getBookId()).isEmpty()) {
            throw BookNotFoundException.forId(book.getBookId());
        }

        int borrowed = bookDAO.countActiveBorrows(book.getBookId());
        int minAvailable = book.getQuantity() - borrowed;
        if (book.getAvailableQuantity() < 0 || book.getAvailableQuantity() > book.getQuantity()) {
            throw new DatabaseException("Available quantity must be between 0 and total quantity.");
        }
        if (book.getAvailableQuantity() < minAvailable) {
            throw new DatabaseException("Cannot reduce available copies below currently borrowed count (" + borrowed + ").");
        }

        bookDAO.update(book);
        return book;
    }

    /**
     * Deletes a book by ID.
     *
     * @param bookId the book ID
     * @throws BookNotFoundException if book not found
     * @throws DatabaseException     if delete fails or book has active borrows
     */
    public void deleteBook(int bookId) throws BookNotFoundException, DatabaseException {
        if (bookDAO.findById(bookId).isEmpty()) {
            throw BookNotFoundException.forId(bookId);
        }
        if (bookDAO.countActiveBorrows(bookId) > 0) {
            throw new DatabaseException("Cannot delete book with active borrows.");
        }
        bookDAO.delete(bookId);
    }

    /**
     * Registers a new library user.
     *
     * @param user the user to register
     * @return the created user with generated ID
     * @throws DatabaseException if insert fails or email is duplicate
     */
    public User registerUser(User user) throws DatabaseException {
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new DatabaseException("Duplicate email address: " + user.getEmail());
        }
        int userId = userDAO.insert(user);
        user.setUserId(userId);
        return user;
    }

    /**
     * Returns all registered users.
     *
     * @return list of users
     * @throws DatabaseException if query fails
     */
    public List<User> getAllUsers() throws DatabaseException {
        return userDAO.findAll();
    }

    /**
     * Borrows a book for a user.
     *
     * @param userId user ID
     * @param bookId book ID
     * @return transaction ID
     * @throws UserNotFoundException if user not found
     * @throws BookNotFoundException if book not found
     * @throws DatabaseException     if borrow fails or book unavailable
     */
    public int borrowBook(int userId, int bookId)
            throws UserNotFoundException, BookNotFoundException, DatabaseException {
        if (userDAO.findById(userId).isEmpty()) {
            throw UserNotFoundException.forId(userId);
        }
        Book book = bookDAO.findById(bookId)
                .orElseThrow(() -> BookNotFoundException.forId(bookId));

        if (book.getAvailableQuantity() <= 0) {
            throw new DatabaseException("Book Not Available");
        }

        int transactionId = borrowDAO.borrowBook(userId, bookId);
        IdGenerator.generateTransactionReference();
        return transactionId;
    }

    /**
     * Returns a borrowed book.
     *
     * @param transactionId the borrow transaction ID
     * @throws DatabaseException if return fails
     */
    public void returnBook(int transactionId) throws DatabaseException {
        borrowDAO.returnBook(transactionId);
    }

    /**
     * Returns all borrow history.
     *
     * @return list of transactions
     * @throws DatabaseException if query fails
     */
    public List<BorrowTransaction> getAllBorrowHistory() throws DatabaseException {
        return borrowDAO.findAllHistory();
    }

    /**
     * Returns borrow history for a specific user.
     *
     * @param userId user ID
     * @return list of transactions
     * @throws UserNotFoundException if user not found
     * @throws DatabaseException     if query fails
     */
    public List<BorrowTransaction> getBorrowHistoryByUser(int userId)
            throws UserNotFoundException, DatabaseException {
        if (userDAO.findById(userId).isEmpty()) {
            throw UserNotFoundException.forId(userId);
        }
        return borrowDAO.findHistoryByUser(userId);
    }

    /**
     * Finds a book by ID.
     *
     * @param bookId book ID
     * @return the book
     * @throws BookNotFoundException if not found
     * @throws DatabaseException     if query fails
     */
    public Book findBook(int bookId) throws BookNotFoundException, DatabaseException {
        return bookDAO.findById(bookId)
                .orElseThrow(() -> BookNotFoundException.forId(bookId));
    }
}
