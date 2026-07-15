package model;

/**
 * Holds statistical analysis results for a processed text file.
 * <p>
 * Encapsulates line, word, character, and character-type counts.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class FileStatistics {

    private String sourceFileName;
    private int totalLines;
    private int totalWords;
    private int totalCharacters;
    private int emptyLines;
    private int vowels;
    private int consonants;
    private int digits;

    /**
     * Default constructor initializing all counters to zero.
     */
    public FileStatistics() {
        this.totalLines = 0;
        this.totalWords = 0;
        this.totalCharacters = 0;
        this.emptyLines = 0;
        this.vowels = 0;
        this.consonants = 0;
        this.digits = 0;
    }

    /**
     * Returns the source file name that was analyzed.
     *
     * @return source file name
     */
    public String getSourceFileName() {
        return sourceFileName;
    }

    /**
     * Sets the source file name.
     *
     * @param sourceFileName the analyzed file name
     */
    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    /**
     * Returns the total number of lines.
     *
     * @return line count
     */
    public int getTotalLines() {
        return totalLines;
    }

    /**
     * Sets the total line count.
     *
     * @param totalLines number of lines
     */
    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    /**
     * Returns the total number of words.
     *
     * @return word count
     */
    public int getTotalWords() {
        return totalWords;
    }

    /**
     * Sets the total word count.
     *
     * @param totalWords number of words
     */
    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    /**
     * Returns the total number of characters.
     *
     * @return character count
     */
    public int getTotalCharacters() {
        return totalCharacters;
    }

    /**
     * Sets the total character count.
     *
     * @param totalCharacters number of characters
     */
    public void setTotalCharacters(int totalCharacters) {
        this.totalCharacters = totalCharacters;
    }

    /**
     * Returns the number of empty lines.
     *
     * @return empty line count
     */
    public int getEmptyLines() {
        return emptyLines;
    }

    /**
     * Sets the empty line count.
     *
     * @param emptyLines number of empty lines
     */
    public void setEmptyLines(int emptyLines) {
        this.emptyLines = emptyLines;
    }

    /**
     * Returns the vowel count.
     *
     * @return vowel count
     */
    public int getVowels() {
        return vowels;
    }

    /**
     * Sets the vowel count.
     *
     * @param vowels number of vowels
     */
    public void setVowels(int vowels) {
        this.vowels = vowels;
    }

    /**
     * Returns the consonant count.
     *
     * @return consonant count
     */
    public int getConsonants() {
        return consonants;
    }

    /**
     * Sets the consonant count.
     *
     * @param consonants number of consonants
     */
    public void setConsonants(int consonants) {
        this.consonants = consonants;
    }

    /**
     * Returns the digit count.
     *
     * @return digit count
     */
    public int getDigits() {
        return digits;
    }

    /**
     * Sets the digit count.
     *
     * @param digits number of digits
     */
    public void setDigits(int digits) {
        this.digits = digits;
    }

    /**
     * Builds a formatted report string for console or file output.
     *
     * @return formatted statistics report
     */
    public String toReport() {
        StringBuilder report = new StringBuilder();
        report.append("=========================================\n");
        report.append("FILE STATISTICS\n");
        report.append("=========================================\n\n");
        report.append(String.format("Source File        : %s%n", sourceFileName));
        report.append(String.format("Total Lines        : %d%n", totalLines));
        report.append(String.format("Total Words        : %d%n", totalWords));
        report.append(String.format("Characters         : %d%n", totalCharacters));
        report.append(String.format("Empty Lines        : %d%n", emptyLines));
        report.append(String.format("Vowels             : %d%n", vowels));
        report.append(String.format("Consonants         : %d%n", consonants));
        report.append(String.format("Digits             : %d%n", digits));
        report.append("\nReport Generated Successfully.\n");
        return report.toString();
    }
}
