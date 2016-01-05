
package mechanics;

import data.OpponentDB;
import data.ProgramExecuter;
import java.io.IOException;

import java.util.Random;

/**
 * This class is used for deciding in which way the computer AI will play.
 */
public class AIPlayer {
    private final OpponentDB db;
    private final ProgramExecuter exe;

    public AIPlayer(OpponentDB db, ProgramExecuter exe) {
        this.db = db;
        this.exe = exe;
    }

    public int play() {
        try {
            int ai_move = exe.nextAiMoveAgainst(db.getOpponent());
            System.out.println(exe.methodForChoosingAiMove());
            return ai_move;
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex);
            return new Random().nextInt(3);
        }
    }
}
