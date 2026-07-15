package util;


public final class IdGenerator {

    private static final String ID_PREFIX = "EMP";
    private static final int ID_PADDING = 3;

    private static int counter = 1;

    private IdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }


    public static synchronized String generateEmployeeId() {
        String id = String.format("%s%0" + ID_PADDING + "d", ID_PREFIX, counter);
        counter++;
        return id;
    }


    public static int getCurrentCounter() {
        return counter;
    }
}
