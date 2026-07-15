package util;

import game.NumberGuessingGame.Difficulty;


public final class InputValidator {

  private static final int MIN_DIFFICULTY_OPTION = 1;
  private static final int MAX_DIFFICULTY_OPTION = 3;

  private InputValidator() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated.");
  }


  public static boolean isValidDifficultyOption(int option) {
    return option >= MIN_DIFFICULTY_OPTION && option <= MAX_DIFFICULTY_OPTION;
  }


  public static boolean isValidGuessRange(int guess, int minValue, int maxValue) {
    return guess >= minValue && guess <= maxValue;
  }


  public static boolean isValidPlayAgainResponse(String response) {
    if (response == null || response.isBlank()) {
      return false;
    }
    String normalized = response.trim().toUpperCase();
    return normalized.equals("Y") || normalized.equals("N");
  }

  public static boolean shouldPlayAgain(String response) {
    return response.trim().toUpperCase().equals("Y");
  }


  public static String formatDifficultyRange(Difficulty difficulty) {
    return difficulty.getMinValue() + " to " + difficulty.getMaxValue();
  }
}
