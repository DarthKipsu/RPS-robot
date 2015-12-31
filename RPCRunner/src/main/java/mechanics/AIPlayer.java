
package mechanics;

import data.OpponentDB;

import java.util.Random;

/**
 * This class is used for deciding in which way the computer AI will play.
 */
public class AIPlayer {
    private OpponentDB db;

    public AIPlayer(OpponentDB db) {
        this.db = db;
    }

    public int play() {
        return new Random().nextInt(3);
    }
}
