package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class for validating file paths and file state.
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class FileValidator {

    /**
     * Private constructor to prevent instantiation.
     */
    private FileValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Validates that a file path is not null or blank.
     *
     * @param filePath the path to validate
     * @return {@code true} if the path is non-empty
     */
    public static boolean isValidFilePath(String filePath) {
        return filePath != null && !filePath.isBlank();
    }

    /**
     * Checks whether a file exists at the given path.
     *
     * @param filePath the file path
     * @return {@code true} if the file exists
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Path.of(filePath));
    }

    /**
     * Checks whether the path points to a readable regular file.
     *
     * @param filePath the file path
     * @return {@code true} if readable
     */
    public static boolean isReadableFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }

    /**
     * Checks whether a file is empty (zero bytes).
     *
     * @param filePath the file path
     * @return {@code true} if the file has no content
     */
    public static boolean isEmptyFile(String filePath) {
        File file = new File(filePath);
        return file.length() == 0;
    }

    /**
     * Validates that the parent directory for output exists or can be created.
     *
     * @param directoryPath the directory path
     * @return {@code true} if directory exists or was created
     */
    public static boolean ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() || directory.mkdirs();
    }

    /**
     * Validates a main menu option.
     *
     * @param option the menu option
     * @return {@code true} if between 1 and 5
     */
    public static boolean isValidMenuOption(int option) {
        return option >= 1 && option <= 5;
    }
}
