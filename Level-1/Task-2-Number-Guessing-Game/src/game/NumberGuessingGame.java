package game;

import java.util.Random;

/**
 * Core game logic for the Number Guessing Game.
 * <p>
 * Manages random number generation, guess evaluation, attempt tracking,
 * and game state. Contains no console I/O.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class NumberGuessingGame {

  /** Maximum number of guesses allowed per round. */
  public static final int MAX_ATTEMPTS = 10;

  /**
   * Supported difficulty levels with their numeric ranges.
   */
  public enum Difficulty {
    /** Range 1 to 50. */
    EASY(1, 50, "Easy (1-50)"),
    /** Range 1 to 100. */
    MEDIUM(1, 100, "Medium (1-100)"),
    /** Range 1 to 500. */
    HARD(1, 500, "Hard (1-500)");

    private final int minValue;
    private final int maxValue;
    private final String displayName;

    Difficulty(int minValue, int maxValue, String displayName) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.displayName = displayName;
    }

    /**
     * Returns the minimum value for this difficulty range.
     *
     * @return minimum inclusive value
     */
    public int getMinValue() {
      return minValue;
    }

    /**
     * Returns the maximum value for this difficulty range.
     *
     * @return maximum inclusive value
     */
    public int getMaxValue() {
      return maxValue;
    }

    /**
     * Returns the human-readable label for this difficulty.
     *
     * @return display name
     */
    public String getDisplayName() {
      return displayName;
    }

    /**
     * Resolves a menu option (1-based) to a difficulty level.
     *
     * @param option the menu option (1 = Easy, 2 = Medium, 3 = Hard)
     * @return the matching {@link Difficulty}
     * @throws IllegalArgumentException if the option is invalid
     */
    public static Difficulty fromMenuOption(int option) {
      return switch (option) {
        case 1 -> EASY;
        case 2 -> MEDIUM;
        case 3 -> HARD;
        default -> throw new IllegalArgumentException("Invalid difficulty option: " + option);
      };
    }
  }

  /**
   * Result of evaluating a player's guess.
   */
  public enum GuessResult {
    /** Guess is higher than the secret number. */
    TOO_HIGH,
    /** Guess is lower than the secret number. */
    TOO_LOW,
    /** Guess matches the secret number. */
    CORRECT
  }

  private final Random random;

  private int secretNumber;
  private int minRange;
  private int maxRange;
  private int attemptsUsed;
  private int remainingAttempts;
  private boolean gameOver;
  private boolean won;
  private Difficulty currentDifficulty;

  /**
   * Creates a new game instance with a {@link Random} generator.
   */
  public NumberGuessingGame() {
    this.random = new Random();
    resetState();
  }

  /**
   * Starts a new round with the given difficulty.
   * Regenerates the secret number for every new game.
   *
   * @param difficulty the selected difficulty level
   */
  public void startNewGame(Difficulty difficulty) {
    this.currentDifficulty = difficulty;
    this.minRange = difficulty.getMinValue();
    this.maxRange = difficulty.getMaxValue();
    this.secretNumber = random.nextInt(maxRange - minRange + 1) + minRange;
    this.attemptsUsed = 0;
    this.remainingAttempts = MAX_ATTEMPTS;
    this.gameOver = false;
    this.won = false;
  }

  /**
   * Evaluates the player's guess and updates game state.
   *
   * @param guess the player's guess
   * @return the result of the guess comparison
   * @throws IllegalStateException if the game is already over
   */
  public GuessResult evaluateGuess(int guess) {
    if (gameOver) {
      throw new IllegalStateException("Cannot evaluate guess — game is already over.");
    }

    attemptsUsed++;
    remainingAttempts--;

    GuessResult result;
    if (guess > secretNumber) {
      result = GuessResult.TOO_HIGH;
    } else if (guess < secretNumber) {
      result = GuessResult.TOO_LOW;
    } else {
      result = GuessResult.CORRECT;
      won = true;
      gameOver = true;
      return result;
    }

    if (remainingAttempts == 0) {
      gameOver = true;
      won = false;
    }

    return result;
  }

  /**
   * Resets all fields to an inactive state before the first game.
   */
  private void resetState() {
    this.secretNumber = 0;
    this.minRange = 0;
    this.maxRange = 0;
    this.attemptsUsed = 0;
    this.remainingAttempts = MAX_ATTEMPTS;
    this.gameOver = false;
    this.won = false;
    this.currentDifficulty = null;
  }

  /**
   * Returns the secret number. Intended for display when the player loses.
   *
   * @return the generated secret number
   */
  public int getSecretNumber() {
    return secretNumber;
  }

  /**
   * Returns the minimum allowed guess for the current difficulty.
   *
   * @return minimum guess value
   */
  public int getMinRange() {
    return minRange;
  }

  /**
   * Returns the maximum allowed guess for the current difficulty.
   *
   * @return maximum guess value
   */
  public int getMaxRange() {
    return maxRange;
  }

  /**
   * Returns the number of attempts used in the current round.
   *
   * @return attempts used
   */
  public int getAttemptsUsed() {
    return attemptsUsed;
  }

  /**
   * Returns the number of remaining attempts in the current round.
   *
   * @return remaining attempts
   */
  public int getRemainingAttempts() {
    return remainingAttempts;
  }

  /**
   * Returns whether the current round has ended.
   *
   * @return {@code true} if the game is over
   */
  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * Returns whether the player won the current round.
   *
   * @return {@code true} if the player guessed correctly
   */
  public boolean isWon() {
    return won;
  }

  /**
   * Returns the difficulty of the current round.
   *
   * @return current difficulty, or {@code null} if no game started
   */
  public Difficulty getCurrentDifficulty() {
    return currentDifficulty;
  }
}
