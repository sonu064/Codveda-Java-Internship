package util;


public final class Constants {


    public static final String DEFAULT_HOST = "localhost";


    public static final int DEFAULT_PORT = 5000;

    public static final int CONNECT_TIMEOUT_MILLIS = 5_000;

    public static final String COMMAND_HELP = "/help";

    public static final String COMMAND_LIST = "/list";

    public static final String COMMAND_EXIT = "/exit";

    public static final String COMMAND_CLEAR = "/clear";

    public static final String PROTOCOL_SUBMIT_USERNAME = "SUBMIT_USERNAME";

    public static final String PROTOCOL_USERNAME_TAKEN = "USERNAME_TAKEN";
    public static final String PROTOCOL_USERNAME_ACCEPTED = "USERNAME_ACCEPTED";


    public static final String TIMESTAMP_PATTERN = "hh:mm a";

    
    public static final int BANNER_WIDTH = 45;


    public static final char BANNER_BORDER_CHAR = '=';

    public static final String NOTIFICATION_JOINED = " joined the chat";
    public static final String NOTIFICATION_LEFT = " left the chat";

    public static final String SERVER_NAME = "SERVER";


    private Constants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }
}
