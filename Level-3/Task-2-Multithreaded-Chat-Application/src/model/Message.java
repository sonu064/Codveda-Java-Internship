package model;

import util.Constants;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Immutable chat message with sender, content, and creation timestamp.
 * <p>
 * Produces the display format required by the application:
 * </p>
 * <pre>
 * [12:40 PM] Sonu:
 * Hello everyone
 * </pre>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class Message {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN);

    private final String sender;
    private final String content;
    private final LocalTime timestamp;

    /**
     * Creates a message stamped with the current time.
     *
     * @param sender  the sender's display name
     * @param content the message body
     */
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalTime.now();
    }

    /**
     * Returns the sender's display name.
     *
     * @return sender name
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the message body.
     *
     * @return message content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the creation timestamp.
     *
     * @return message timestamp
     */
    public LocalTime getTimestamp() {
        return timestamp;
    }

    /**
     * Formats the message for console display.
     *
     * @return formatted two-line message string
     */
    public String format() {
        return "[" + timestamp.format(TIMESTAMP_FORMATTER) + "] " + sender + ":"
                + System.lineSeparator() + content;
    }

    /**
     * Returns the formatted message.
     *
     * @return formatted message string
     */
    @Override
    public String toString() {
        return format();
    }
}
