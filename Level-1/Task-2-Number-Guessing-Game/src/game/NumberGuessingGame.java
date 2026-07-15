package game;

import java.util.Random;


public class NumberGuessingGame {

  public static final int MAX_ATTEMPTS = 10;

  public enum Difficulty {
    EASY(1, 50, "Easy (1-50)"),
    MEDIUM(1, 100, "Medium (1-100)"),
    HARD(1, 500, "Hard (1-500)");

    private final int minValue;
    private final int maxValue;
    private final String displayName;

    Difficulty(int minValue, int maxValue, String displayName) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.displayName = displayName;
    }
    public int getMinValue() {
      return minValue;
    }
    public int getMaxValue() {
      return maxValue;
    }
    public String getDisplayName() {
      return displayName;
    }
    public static Difficulty fromMenuOption(int option) {
      return switch (option) {
        case 1 -> EASY;
        case 2 -> MEDIUM;
        case 3 -> HARD;
        default -> throw new IllegalArgumentException("Invalid difficulty option: " + option);
      };
    }
  }

  public enum GuessResult {
    TOO_HIGH,
    TOO_LOW,
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

  public NumberGuessingGame() {
    this.random = new Random();
    resetState();
  }
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

  public int getSecretNumber() {
    return secretNumber;
  }

  public int getMinRange() {
    return minRange;
  }
  public int getMaxRange() {
    return maxRange;
  }

  public int getAttemptsUsed() {
    return attemptsUsed;
  }
  public int getRemainingAttempts() {
    return remainingAttempts;
  }

  public boolean isGameOver() {
    return gameOver;
  }


  public boolean isWon() {
    return won;
  }

  public Difficulty getCurrentDifficulty() {
    return currentDifficulty;
  }
}
