
package rpcrunner;

import java.io.File;
import java.util.Random;

/**
 * This class is used for deciding in which way the computer AI will play.
 */
public class AIPlayer {
    private final File opponentFile;

    public AIPlayer(File opponentFile) {
        this.opponentFile = opponentFile;
    }

    public int play() {
        return new Random().nextInt(3);
    }
}
