import game.NumberGuessingGame;
import game.NumberGuessingGame.Difficulty;
import game.NumberGuessingGame.GuessResult;
import model.GameStatistics;
import util.InputValidator;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

  private static final String APP_TITLE = "NUMBER GUESSING GAME";
  private static final String BORDER_LONG = "========================================";
  private static final String BORDER_SHORT = "==============================";

  private static final boolean ANSI_SUPPORTED = System.console() != null;

  private static final String RESET = ANSI_SUPPORTED ? "\u001B[0m" : "";
  private static final String BOLD = ANSI_SUPPORTED ? "\u001B[1m" : "";
  private static final String CYAN = ANSI_SUPPORTED ? "\u001B[36m" : "";
  private static final String GREEN = ANSI_SUPPORTED ? "\u001B[32m" : "";
  private static final String YELLOW = ANSI_SUPPORTED ? "\u001B[33m" : "";
  private static final String RED = ANSI_SUPPORTED ? "\u001B[31m" : "";
  private static final String MAGENTA = ANSI_SUPPORTED ? "\u001B[35m" : "";

  private final NumberGuessingGame game;
  private final GameStatistics statistics;
  private final Scanner scanner;

  public Main() {
    this.game = new NumberGuessingGame();
    this.statistics = new GameStatistics();
    this.scanner = new Scanner(System.in);
  }

  public static void main(String[] args) {
    Main application = new Main();
    application.run();
  }

  public void run() {
    displayWelcomeBanner();
    boolean playing = true;

    while (playing) {
      Difficulty difficulty = selectDifficulty();
      playRound(difficulty);
      playing = promptPlayAgain();
    }

    displayStatistics();
    displayGoodbyeMessage();
    scanner.close();
  }
  private void displayWelcomeBanner() {
    System.out.println();
    System.out.println(colorize(BORDER_LONG, CYAN));
    System.out.println(colorize(centerText(APP_TITLE, BORDER_LONG.length()), BOLD + CYAN));
    System.out.println(colorize(BORDER_LONG, CYAN));
    System.out.println(colorize("  Guess the secret number — you have 10 attempts!", GREEN));
    System.out.println(colorize("  Codveda Technologies — Java Internship", GREEN));
    System.out.println();
  }

  private Difficulty selectDifficulty() {
    System.out.println(colorize("Difficulty:", BOLD));
    System.out.println("  1. Easy (1-50)");
    System.out.println("  2. Medium (1-100)");
    System.out.println("  3. Hard (1-500)");
    System.out.println();
    System.out.print(colorize("Choose Difficulty: ", YELLOW));

    while (true) {
      try {
        int option = scanner.nextInt();
        scanner.nextLine();

        if (InputValidator.isValidDifficultyOption(option)) {
          Difficulty difficulty = Difficulty.fromMenuOption(option);
          System.out.println();
          System.out.println(colorize(
              "Difficulty set to " + difficulty.getDisplayName()
                  + " — guess a number between "
                  + InputValidator.formatDifficultyRange(difficulty) + ".",
              GREEN));
          System.out.println();
          return difficulty;
        }

        printError("Invalid option. Please choose 1, 2, or 3.");
        System.out.print(colorize("Choose Difficulty: ", YELLOW));
      } catch (InputMismatchException exception) {
        clearInvalidInput();
        printError("Invalid input. Please enter a number (1, 2, or 3).");
        System.out.print(colorize("Choose Difficulty: ", YELLOW));
      }
    }
  }

  private void playRound(Difficulty difficulty) {
    game.startNewGame(difficulty);

    while (!game.isGameOver()) {
      int guess = readGuess();
      GuessResult result = game.evaluateGuess(guess);
      displayGuessFeedback(result);
    }

    finalizeRound();
  }


  private int readGuess() {
    while (true) {
      try {
        System.out.print(colorize("Guess the number: ", YELLOW));
        int guess = scanner.nextInt();
        scanner.nextLine();

        if (!InputValidator.isValidGuessRange(guess, game.getMinRange(), game.getMaxRange())) {
          printError(String.format(Locale.US,
              "Guess must be between %d and %d. Try again.",
              game.getMinRange(), game.getMaxRange()));
          continue;
        }

        return guess;
      } catch (InputMismatchException exception) {
        clearInvalidInput();
        printError("Invalid input. Please enter a whole number.");
      }
    }
  }

  private void displayGuessFeedback(GuessResult result) {
    System.out.println();

    if (result == GuessResult.CORRECT) {
      System.out.println(colorize("Congratulations! You guessed the number correctly!", BOLD + GREEN));
      System.out.println(colorize(String.format(Locale.US,
          "You guessed the number in %d attempt%s.",
          game.getAttemptsUsed(),
          game.getAttemptsUsed() == 1 ? "" : "s"), GREEN));
    } else {
      System.out.println(colorize("Wrong!", RED));
      String hint = switch (result) {
        case TOO_HIGH -> "Too High";
        case TOO_LOW -> "Too Low";
        default -> "";
      };
      System.out.println(colorize("Hint: " + hint, MAGENTA));

      if (!game.isGameOver()) {
        System.out.println(colorize("Remaining Attempts: " + game.getRemainingAttempts(), YELLOW));
      }
    }

    System.out.println();
  }


  private void finalizeRound() {
    if (game.isWon()) {
      statistics.recordWin(game.getAttemptsUsed());
    } else {
      statistics.recordLoss();
      System.out.println(colorize("Game Over! You've used all "
          + NumberGuessingGame.MAX_ATTEMPTS + " attempts.", BOLD + RED));
      System.out.println(colorize("The secret number was: " + game.getSecretNumber(), YELLOW));
      System.out.println();
    }
  }

  private boolean promptPlayAgain() {
    while (true) {
      System.out.print(colorize("Play again? (Y/N): ", YELLOW));
      String response = scanner.nextLine().trim();

      if (!InputValidator.isValidPlayAgainResponse(response)) {
        printError("Invalid response. Please enter Y or N.");
        continue;
      }

      if (!InputValidator.shouldPlayAgain(response)) {
        return false;
      }

      System.out.println();
      return true;
    }
  }


  private void displayStatistics() {
    System.out.println(colorize(BORDER_SHORT, CYAN));
    System.out.println(colorize("Game Statistics", BOLD + CYAN));
    System.out.println(colorize(BORDER_SHORT, CYAN));
    System.out.printf(Locale.US, "Games Played : %d%n", statistics.getGamesPlayed());
    System.out.printf(Locale.US, "Games Won    : %d%n", statistics.getGamesWon());
    System.out.printf(Locale.US, "Games Lost   : %d%n", statistics.getGamesLost());

    if (statistics.hasBestScore()) {
      System.out.printf(Locale.US, "Best Score   : %d Attempt%s%n",
          statistics.getBestScore(),
          statistics.getBestScore() == 1 ? "" : "s");
    } else {
      System.out.println("Best Score   : N/A");
    }

    System.out.println(colorize(BORDER_SHORT, CYAN));
    System.out.println();
  }


  private void displayGoodbyeMessage() {
    System.out.println(colorize("Thank you for playing Number Guessing Game!", GREEN));
    System.out.println(colorize("Goodbye! Keep practicing and good luck next time.", GREEN));
    System.out.println();
  }


  private void printError(String message) {
    System.out.println(colorize("Error: " + message, RED));
  }

  private void clearInvalidInput() {
    scanner.nextLine();
  }

  private String colorize(String text, String color) {
    return color + text + RESET;
  }


  private String centerText(String text, int width) {
    int padding = Math.max(0, (width - text.length()) / 2);
    return " ".repeat(padding) + text;
  }
}
