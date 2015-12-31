
package mechanics;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameRulesTest {
    private GameRules rules = new GameRules();

    @Test
    public void canTellIfAGameIsADraw() {
        assertEquals("It's a draw!", rules.winner(0, 0));
        assertEquals("It's a draw!", rules.winner(1, 1));
        assertEquals("It's a draw!", rules.winner(2, 2));
    }

    @Test
    public void canTellIfAiWins() {
        assertEquals("You lose!", rules.winner(0, 1));
        assertEquals("You lose!", rules.winner(1, 2));
        assertEquals("You lose!", rules.winner(2, 0));
    }

    @Test
    public void canTellIfHumanWins() {
        assertEquals("You win!", rules.winner(0, 2));
        assertEquals("You win!", rules.winner(1, 0));
        assertEquals("You win!", rules.winner(2, 1));
    }
}
