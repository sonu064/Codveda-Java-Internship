package model;

import util.Constants;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public final class Message {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN);

    private final String sender;
    private final String content;
    private final LocalTime timestamp;


    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalTime.now();
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }


    public LocalTime getTimestamp() {
        return timestamp;
    }


    public String format() {
        return "[" + timestamp.format(TIMESTAMP_FORMATTER) + "] " + sender + ":"
                + System.lineSeparator() + content;
    }

    @Override
    public String toString() {
        return format();
    }
}
