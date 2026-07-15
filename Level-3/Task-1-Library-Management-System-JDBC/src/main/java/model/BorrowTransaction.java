package model;

import java.time.LocalDate;


public class BorrowTransaction {


    public enum Status {

        BORROWED,
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


    public BorrowTransaction() {

    }

    public BorrowTransaction(int transactionId, int userId, int bookId,
                             LocalDate borrowDate, LocalDate returnDate, Status status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }


    public int getTransactionId() {
        return transactionId;
    }


    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getBookId() {
        return bookId;
    }


    public void setBookId(int bookId) {
        this.bookId = bookId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getBookTitle() {
        return bookTitle;
    }


    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }


    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }


    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    public Status getStatus() {
        return status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        String returnedOn = returnDate != null ? returnDate.toString() : "N/A";
        return String.format("TXN-%d | User: %s (ID:%d) | Book: %s (ID:%d) | Borrowed: %s | Returned: %s | %s",
                transactionId, userName, userId, bookTitle, bookId,
                borrowDate, returnedOn, status);
    }
}
