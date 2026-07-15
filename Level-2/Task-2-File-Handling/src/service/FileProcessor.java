package service;

import exception.FileProcessingException;
import model.FileStatistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for reading, processing, and writing text files.
 * <p>
 * Uses {@link BufferedReader}/{@link FileReader} for input and
 * {@link BufferedWriter}/{@link FileWriter} for output.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class FileProcessor {

    private static final String VOWELS = "aeiouAEIOU";

    private final List<String> fileLines;
    private FileStatistics statistics;

    /**
     * Creates a new file processor with an empty line buffer.
     */
    public FileProcessor() {
        this.fileLines = new ArrayList<>();
        this.statistics = null;
    }

    /**
     * Reads all lines from the specified input file.
     *
     * @param inputFile path to the input text file
     * @throws FileProcessingException if the file cannot be read
     */
    public void readFile(Path inputFile) throws FileProcessingException {
        validateInputFile(inputFile);
        fileLines.clear();
        statistics = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (FileNotFoundException exception) {
            throw new FileProcessingException(
                    "Input file not found. Searched at: " + inputFile.toAbsolutePath(), exception);
        } catch (IOException exception) {
            throw new FileProcessingException(
                    "Error reading file: " + exception.getMessage(), exception);
        }

        try {
            if (fileLines.isEmpty() && Files.size(inputFile) == 0) {
                throw new FileProcessingException(
                        "Input file is empty. Please provide content to process.");
            }
        } catch (IOException exception) {
            throw new FileProcessingException(
                    "Error inspecting file size: " + exception.getMessage(), exception);
        }
    }

    /**
     * Processes loaded file content and computes statistics.
     *
     * @param sourceFileName display name for the report
     * @return computed {@link FileStatistics}
     * @throws FileProcessingException if no file has been loaded
     */
    public FileStatistics processFile(String sourceFileName) throws FileProcessingException {
        if (fileLines.isEmpty()) {
            throw new FileProcessingException("No file content loaded. Please read a file first.");
        }

        FileStatistics stats = new FileStatistics();
        stats.setSourceFileName(sourceFileName);

        int totalWords = 0;
        int totalCharacters = 0;
        int emptyLines = 0;
        int vowels = 0;
        int consonants = 0;
        int digits = 0;

        for (String line : fileLines) {
            if (line.isBlank()) {
                emptyLines++;
            }

            totalCharacters += line.length();

            String[] words = line.trim().split("\\s+");
            if (!line.isBlank()) {
                totalWords += words.length;
            }

            for (char character : line.toCharArray()) {
                if (Character.isDigit(character)) {
                    digits++;
                } else if (Character.isLetter(character)) {
                    if (isVowel(character)) {
                        vowels++;
                    } else {
                        consonants++;
                    }
                }
            }
        }

        stats.setTotalLines(fileLines.size());
        stats.setTotalWords(totalWords);
        stats.setTotalCharacters(totalCharacters);
        stats.setEmptyLines(emptyLines);
        stats.setVowels(vowels);
        stats.setConsonants(consonants);
        stats.setDigits(digits);

        this.statistics = stats;
        return stats;
    }

    /**
     * Writes the statistics report to the specified output file.
     * <p>
     * The parent output directory is created automatically if it does not exist.
     * </p>
     *
     * @param outputFile path to the output file
     * @throws FileProcessingException if writing fails or statistics are unavailable
     */
    public void writeReport(Path outputFile) throws FileProcessingException {
        if (statistics == null) {
            throw new FileProcessingException("No statistics available. Please process the file first.");
        }

        if (outputFile == null) {
            throw new FileProcessingException("Invalid output file path.");
        }

        Path outputDirectory = outputFile.toAbsolutePath().getParent();
        try {
            if (outputDirectory != null) {
                Files.createDirectories(outputDirectory);
            }
        } catch (IOException exception) {
            throw new FileProcessingException(
                    "Unable to create output directory: " + outputDirectory, exception);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile()))) {
            writer.write(statistics.toReport());
            writer.write("Output saved to:\n");
            writer.write(outputFile.toAbsolutePath().toString());
            writer.newLine();
        } catch (IOException exception) {
            throw new FileProcessingException(
                    "Error writing report: " + exception.getMessage(), exception);
        }
    }

    /**
     * Returns the current statistics, or {@code null} if not yet processed.
     *
     * @return file statistics
     */
    public FileStatistics getStatistics() {
        return statistics;
    }

    /**
     * Checks whether file content has been loaded.
     *
     * @return {@code true} if lines are loaded
     */
    public boolean isFileLoaded() {
        return !fileLines.isEmpty();
    }

    /**
     * Checks whether statistics have been computed.
     *
     * @return {@code true} if statistics exist
     */
    public boolean hasStatistics() {
        return statistics != null;
    }

    /**
     * Returns the number of lines currently loaded in memory.
     *
     * @return loaded line count
     */
    public int getLoadedLineCount() {
        return fileLines.size();
    }

    /**
     * Validates the input file before reading.
     *
     * @param inputFile path to validate
     * @throws FileProcessingException if validation fails
     */
    private void validateInputFile(Path inputFile) throws FileProcessingException {
        if (inputFile == null) {
            throw new FileProcessingException("Invalid file path provided.");
        }

        Path absolutePath = inputFile.toAbsolutePath();

        if (!Files.exists(inputFile)) {
            throw new FileProcessingException("File not found. Searched at: " + absolutePath);
        }
        if (!Files.isRegularFile(inputFile) || !Files.isReadable(inputFile)) {
            throw new FileProcessingException("File is not readable: " + absolutePath);
        }
    }

    /**
     * Determines whether a character is a vowel.
     *
     * @param character the character to check
     * @return {@code true} if vowel
     */
    private boolean isVowel(char character) {
        return VOWELS.indexOf(character) >= 0;
    }
}
