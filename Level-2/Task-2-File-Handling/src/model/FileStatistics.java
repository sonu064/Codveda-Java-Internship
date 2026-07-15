package model;

public class FileStatistics {

    private String sourceFileName;
    private int totalLines;
    private int totalWords;
    private int totalCharacters;
    private int emptyLines;
    private int vowels;
    private int consonants;
    private int digits;

    public FileStatistics() {
        this.totalLines = 0;
        this.totalWords = 0;
        this.totalCharacters = 0;
        this.emptyLines = 0;
        this.vowels = 0;
        this.consonants = 0;
        this.digits = 0;
    }


    public String getSourceFileName() {
        return sourceFileName;
    }


    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }


    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }


    public int getTotalWords() {
        return totalWords;
    }


    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public int getTotalCharacters() {
        return totalCharacters;
    }


    public void setTotalCharacters(int totalCharacters) {
        this.totalCharacters = totalCharacters;
    }


    public int getEmptyLines() {
        return emptyLines;
    }

    public void setEmptyLines(int emptyLines) {
        this.emptyLines = emptyLines;
    }

    public int getVowels() {
        return vowels;
    }


    public void setVowels(int vowels) {
        this.vowels = vowels;
    }

    public int getConsonants() {
        return consonants;
    }

    public void setConsonants(int consonants) {
        this.consonants = consonants;
    }


    public int getDigits() {
        return digits;
    }


    public void setDigits(int digits) {
        this.digits = digits;
    }


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
