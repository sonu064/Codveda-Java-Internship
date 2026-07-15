package model;

/**
 * Represents a book in the library catalog.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class Book {

    private int bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private int quantity;
    private int availableQuantity;

    /**
     * Default constructor.
     */
    public Book() {
        // Fields set via setters or parameterized constructor.
    }

    /**
     * Creates a book without a database-assigned ID.
     *
     * @param title             book title
     * @param author            book author
     * @param category          book category
     * @param isbn              unique ISBN
     * @param quantity          total copies
     * @param availableQuantity available copies
     */
    public Book(String title, String author, String category, String isbn,
                int quantity, int availableQuantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }

    /**
     * Creates a book with all fields including ID.
     *
     * @param bookId            database ID
     * @param title             book title
     * @param author            book author
     * @param category          book category
     * @param isbn              unique ISBN
     * @param quantity          total copies
     * @param availableQuantity available copies
     */
    public Book(int bookId, String title, String author, String category,
                String isbn, int quantity, int availableQuantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
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
     * @param bookId database ID
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Returns the title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title book title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author.
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     *
     * @param author book author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the category.
     *
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category.
     *
     * @param category book category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the ISBN.
     *
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN.
     *
     * @param isbn unique ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the total quantity.
     *
     * @return total copies
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the total quantity.
     *
     * @param quantity total copies
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the available quantity.
     *
     * @return available copies
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Sets the available quantity.
     *
     * @param availableQuantity available copies
     */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     * Returns a formatted summary of the book.
     *
     * @return summary string
     */
    @Override
    public String toString() {
        return String.format("ID: %d | %s by %s | Category: %s | ISBN: %s | Available: %d/%d",
                bookId, title, author, category, isbn, availableQuantity, quantity);
    }
}
