package exception;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(String message) {
        super(message);
    }


    public static BookNotFoundException forId(int bookId) {
        return new BookNotFoundException("Book Not Found with ID: " + bookId);
    }
}
