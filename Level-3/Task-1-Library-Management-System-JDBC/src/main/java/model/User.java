package model;

/**
 * Represents a library member / user.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class User {

    private int userId;
    private String fullName;
    private String email;
    private String phone;

    /**
     * Default constructor.
     */
    public User() {
        // Fields set via setters or parameterized constructor.
    }

    /**
     * Creates a user without a database-assigned ID.
     *
     * @param fullName full name
     * @param email    email address
     * @param phone    phone number
     */
    public User(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Creates a user with all fields including ID.
     *
     * @param userId   database ID
     * @param fullName full name
     * @param email    email address
     * @param phone    phone number
     */
    public User(int userId, String fullName, String email, String phone) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
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
     * @param userId database ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the full name.
     *
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name.
     *
     * @param fullName full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number.
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number.
     *
     * @param phone phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns a formatted summary of the user.
     *
     * @return summary string
     */
    @Override
    public String toString() {
        return String.format("ID: %d | %s | %s | %s", userId, fullName, email, phone);
    }
}
