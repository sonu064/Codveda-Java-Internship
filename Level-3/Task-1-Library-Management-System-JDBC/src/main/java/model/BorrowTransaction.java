package model;

import java.time.LocalDate;

/**
 * Represents a book borrow/return transaction.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class BorrowTransaction {

    /**
     * Status values for borrow transactions.
     */
    public enum Status {
        /** Book is currently borrowed. */
        BORROWED,
        /** Book has been returned. */
        RETURNED
    }

    private int transactionId;
    private int userId;
    private int bookId;
    private String userName;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Status status;

    /**
     * Default constructor.
     */
    public BorrowTransaction() {
        // Fields set via setters.
    }

    /**
     * Creates a borrow transaction with core fields.
     *
     * @param transactionId transaction ID
     * @param userId        user ID
     * @param bookId        book ID
     * @param borrowDate    borrow date
     * @param returnDate    return date (null if not returned)
     * @param status        transaction status
     */
    public BorrowTransaction(int transactionId, int userId, int bookId,
                             LocalDate borrowDate, LocalDate returnDate, Status status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    /**
     * Returns the transaction ID.
     *
     * @return transaction ID
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction ID.
     *
     * @param transactionId transaction ID
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Returns the user ID.
     *
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the book ID.
     *
     * @return book ID
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Sets the book ID.
     *
     * @param bookId book ID
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Returns the user name (for display in joined queries).
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the book title (for display in joined queries).
     *
     * @return book title
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * Sets the book title.
     *
     * @param bookTitle book title
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    /**
     * Returns the borrow date.
     *
     * @return borrow date
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Sets the borrow date.
     *
     * @param borrowDate borrow date
     */
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * Returns the return date.
     *
     * @return return date, or {@code null}
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     *
     * @param returnDate return date
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Returns the transaction status.
     *
     * @return status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the transaction status.
     *
     * @param status transaction status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns a formatted summary of the transaction.
     *
     * @return summary string
     */
    @Override
    public String toString() {
        String returnedOn = returnDate != null ? returnDate.toString() : "N/A";
        return String.format("TXN-%d | User: %s (ID:%d) | Book: %s (ID:%d) | Borrowed: %s | Returned: %s | %s",
                transactionId, userName, userId, bookTitle, bookId,
                borrowDate, returnedOn, status);
    }
}
