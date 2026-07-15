package exception;

/**
 * Exception thrown when a requested book cannot be found.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class BookNotFoundException extends Exception {

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message detail about the missing book
     */
    public BookNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an exception for a missing book ID.
     *
     * @param bookId the book ID that was not found
     * @return a new {@link BookNotFoundException}
     */
    public static BookNotFoundException forId(int bookId) {
        return new BookNotFoundException("Book Not Found with ID: " + bookId);
    }
}
