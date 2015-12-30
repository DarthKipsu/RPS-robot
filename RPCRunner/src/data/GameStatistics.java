
package data;

/**
 * Keeps a record of game statistics from previous games.
 */
public class GameStatistics {
    private final int games;
    private final int draws;
    private final int ai_wins;
    private final int user_wins;

    public GameStatistics(int games, int draws, int ai_wins) {
        this.games = games;
        this.draws = draws;
        this.ai_wins = ai_wins;
        user_wins = games - draws - ai_wins;
    }

    public String statistics() {
        return games + " games played, were you have \nwon "
                + user_wins + " times\nlost "
                + ai_wins + " times\nplayed "
                + draws + " draws\n";
    }

    public String aiWinRatio() {
        return "AI win ratio is "
                + (int) (ai_wins / (float) (ai_wins + user_wins) * 100)
                + "%";
    }
}
