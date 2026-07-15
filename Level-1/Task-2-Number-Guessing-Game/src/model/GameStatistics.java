package model;

/**
 * Tracks session-level statistics for the Number Guessing Game.
 * <p>
 * Records games played, wins, losses, and the best score (fewest attempts to win).
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class GameStatistics {

  private static final int NO_BEST_SCORE = -1;

  private int gamesPlayed;
  private int gamesWon;
  private int gamesLost;
  private int bestScore;

  /**
   * Creates a new statistics tracker with zeroed counters.
   */
  public GameStatistics() {
    this.gamesPlayed = 0;
    this.gamesWon = 0;
    this.gamesLost = 0;
    this.bestScore = NO_BEST_SCORE;
  }

  /**
   * Records a won game and updates the best score if applicable.
   *
   * @param attemptsUsed the number of attempts the player used to win
   */
  public void recordWin(int attemptsUsed) {
    gamesPlayed++;
    gamesWon++;
    updateBestScore(attemptsUsed);
  }

  /**
   * Records a lost game.
   */
  public void recordLoss() {
    gamesPlayed++;
    gamesLost++;
  }

  /**
   * Updates the best score to the minimum attempts across all wins.
   *
   * @param attemptsUsed the attempts used in the latest win
   */
  private void updateBestScore(int attemptsUsed) {
    if (bestScore == NO_BEST_SCORE || attemptsUsed < bestScore) {
      bestScore = attemptsUsed;
    }
  }

  /**
   * Returns the total number of games played in this session.
   *
   * @return total games played
   */
  public int getGamesPlayed() {
    return gamesPlayed;
  }

  /**
   * Returns the number of games won in this session.
   *
   * @return games won
   */
  public int getGamesWon() {
    return gamesWon;
  }

  /**
   * Returns the number of games lost in this session.
   *
   * @return games lost
   */
  public int getGamesLost() {
    return gamesLost;
  }

  /**
   * Returns the best score (minimum attempts to win), or {@code -1} if no games won.
   *
   * @return best score in attempts, or {@code -1} if none
   */
  public int getBestScore() {
    return bestScore;
  }

  /**
   * Checks whether at least one game has been played.
   *
   * @return {@code true} if games have been played; {@code false} otherwise
   */
  public boolean hasPlayedGames() {
    return gamesPlayed > 0;
  }

  /**
   * Checks whether a best score has been recorded.
   *
   * @return {@code true} if a best score exists; {@code false} otherwise
   */
  public boolean hasBestScore() {
    return bestScore != NO_BEST_SCORE;
  }
}
