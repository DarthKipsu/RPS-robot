
package mechanics;

/**
 * Contains methods for applying game rules.
 */
public class GameRules {

    /**
     * Checks which layer won a match.
     * @param human_played integer representing which sign was predicted for
     * human player
     * @param ai_played integer representing which sign AI played
     * @return string telling the outcome in human understandable way
     */
    public String winner(int human_played, int ai_played) {
        if (itsADraw(human_played, ai_played)) {
            return "It's a draw!";
        } else if (humanPlayerWins(human_played, ai_played)) {
            return "You win!";
        } else {
            return "You lose!";
        }
    }

    private boolean itsADraw(int human_played, int ai_played) {
        return human_played == ai_played;
    }

    private boolean humanPlayerWins(int human_played, int ai_played) {
        return (human_played == 0 && ai_played == 2)
                | (human_played == 1 && ai_played == 0)
                | (human_played == 2 && ai_played == 1);
    }
}
