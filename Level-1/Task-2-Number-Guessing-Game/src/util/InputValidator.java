package util;

import game.NumberGuessingGame.Difficulty;

/**
 * Utility class for validating user input in the Number Guessing Game.
 * <p>
 * Centralizes validation rules to keep presentation and game logic decoupled.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public final class InputValidator {

  private static final int MIN_DIFFICULTY_OPTION = 1;
  private static final int MAX_DIFFICULTY_OPTION = 3;

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private InputValidator() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated.");
  }

  /**
   * Validates whether the given value is a valid difficulty menu option.
   *
   * @param option the menu option entered by the user
   * @return {@code true} if the option is between 1 and 3 (inclusive)
   */
  public static boolean isValidDifficultyOption(int option) {
    return option >= MIN_DIFFICULTY_OPTION && option <= MAX_DIFFICULTY_OPTION;
  }

  /**
   * Validates whether a guess falls within the allowed range for the current difficulty.
   *
   * @param guess    the player's guess
   * @param minValue the minimum allowed value (inclusive)
   * @param maxValue the maximum allowed value (inclusive)
   * @return {@code true} if the guess is within range
   */
  public static boolean isValidGuessRange(int guess, int minValue, int maxValue) {
    return guess >= minValue && guess <= maxValue;
  }

  /**
   * Validates whether the user's play-again response is Y or N (case-insensitive).
   *
   * @param response the user's response string
   * @return {@code true} if the response is Y or N
   */
  public static boolean isValidPlayAgainResponse(String response) {
    if (response == null || response.isBlank()) {
      return false;
    }
    String normalized = response.trim().toUpperCase();
    return normalized.equals("Y") || normalized.equals("N");
  }

  /**
   * Determines whether the player wants to play again.
   *
   * @param response the user's validated Y/N response
   * @return {@code true} if the response is Y
   */
  public static boolean shouldPlayAgain(String response) {
    return response.trim().toUpperCase().equals("Y");
  }

  /**
   * Returns a formatted range hint for the given difficulty.
   *
   * @param difficulty the selected difficulty
   * @return a human-readable range description
   */
  public static String formatDifficultyRange(Difficulty difficulty) {
    return difficulty.getMinValue() + " to " + difficulty.getMaxValue();
  }
}
