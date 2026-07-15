package model;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Immutable representation of a connected chat user.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class User {

    private final String username;
    private final LocalTime joinedAt;

    /**
     * Creates a user stamped with the current join time.
     *
     * @param username the unique display name
     */
    public User(String username) {
        this.username = username;
        this.joinedAt = LocalTime.now();
    }

    /**
     * Returns the user's display name.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the time the user joined the chat.
     *
     * @return join time
     */
    public LocalTime getJoinedAt() {
        return joinedAt;
    }

    /**
     * Users are equal when their usernames match (case-insensitive).
     *
     * @param object the reference object
     * @return {@code true} if usernames match
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User other = (User) object;
        return username.equalsIgnoreCase(other.username);
    }

    /**
     * Returns hash code based on the lowercase username.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(username.toLowerCase());
    }

    /**
     * Returns the username.
     *
     * @return username string
     */
    @Override
    public String toString() {
        return username;
    }
}
