package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public final class FileValidator {


    private FileValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    public static boolean isValidFilePath(String filePath) {
        return filePath != null && !filePath.isBlank();
    }


    public static boolean fileExists(String filePath) {
        return Files.exists(Path.of(filePath));
    }


    public static boolean isReadableFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }


    public static boolean isEmptyFile(String filePath) {
        File file = new File(filePath);
        return file.length() == 0;
    }


    public static boolean ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() || directory.mkdirs();
    }


    public static boolean isValidMenuOption(int option) {
        return option >= 1 && option <= 5;
    }
}
