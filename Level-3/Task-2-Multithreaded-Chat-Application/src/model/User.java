package model;

import java.time.LocalTime;
import java.util.Objects;


public final class User {

    private final String username;
    private final LocalTime joinedAt;


    public User(String username) {
        this.username = username;
        this.joinedAt = LocalTime.now();
    }

    public String getUsername() {
        return username;
    }

    public LocalTime getJoinedAt() {
        return joinedAt;
    }



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


    @Override
    public int hashCode() {
        return Objects.hash(username.toLowerCase());
    }

    @Override
    public String toString() {
        return username;
    }
}
