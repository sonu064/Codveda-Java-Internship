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


public class LibraryService {

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final BorrowDAO borrowDAO;


    public LibraryService(BookDAO bookDAO, UserDAO userDAO, BorrowDAO borrowDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        this.borrowDAO = borrowDAO;
    }


    public Book addBook(Book book) throws DatabaseException {
        if (bookDAO.existsByIsbn(book.getIsbn())) {
            throw new DatabaseException("Duplicate ISBN: " + book.getIsbn());
        }
        int bookId = bookDAO.insert(book);
        book.setBookId(bookId);
        return book;
    }

    public List<Book> getAllBooks() throws DatabaseException {
        return bookDAO.findAll();
    }


    public List<Book> searchBooks(String keyword) throws DatabaseException {
        return bookDAO.search(keyword);
    }

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

    public void deleteBook(int bookId) throws BookNotFoundException, DatabaseException {
        if (bookDAO.findById(bookId).isEmpty()) {
            throw BookNotFoundException.forId(bookId);
        }
        if (bookDAO.countActiveBorrows(bookId) > 0) {
            throw new DatabaseException("Cannot delete book with active borrows.");
        }
        bookDAO.delete(bookId);
    }

    public User registerUser(User user) throws DatabaseException {
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new DatabaseException("Duplicate email address: " + user.getEmail());
        }
        int userId = userDAO.insert(user);
        user.setUserId(userId);
        return user;
    }

    public List<User> getAllUsers() throws DatabaseException {
        return userDAO.findAll();
    }


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


    public void returnBook(int transactionId) throws DatabaseException {
        borrowDAO.returnBook(transactionId);
    }


    public List<BorrowTransaction> getAllBorrowHistory() throws DatabaseException {
        return borrowDAO.findAllHistory();
    }


    public List<BorrowTransaction> getBorrowHistoryByUser(int userId)
            throws UserNotFoundException, DatabaseException {
        if (userDAO.findById(userId).isEmpty()) {
            throw UserNotFoundException.forId(userId);
        }
        return borrowDAO.findHistoryByUser(userId);
    }


    public Book findBook(int bookId) throws BookNotFoundException, DatabaseException {
        return bookDAO.findById(bookId)
                .orElseThrow(() -> BookNotFoundException.forId(bookId));
    }
}
