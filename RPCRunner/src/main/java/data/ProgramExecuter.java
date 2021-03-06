
package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes programs from outside Java package.
 */
public class ProgramExecuter {

    private final String IMAGE_INTERPETER;
    private final String GAME_STATISTICS;
    private final String AI_MOVE;
    private BufferedReader stdInput;

    public ProgramExecuter(String IMAGE_INTERPETER, String GAME_STATISTICS,
            String AI_MOVE) {
        this.IMAGE_INTERPETER = IMAGE_INTERPETER;
        this.GAME_STATISTICS = GAME_STATISTICS;
        this.AI_MOVE = AI_MOVE;
    }

    public ProgramExecuter() {
        this("../MachineLearning/prophet.py",
                "../MachineLearning/statistics.py",
                "../MachineLearning/rpc_ai.py");
    }

    /**
     * Predicts the sign of an image saved to disc as temp file.
     * 0 = rock
     * 1 = paper
     * 2 = scissors
     * @return sign predicted
     */
    public int predictImageSign() throws IOException, InterruptedException {
        execute(new String[]{IMAGE_INTERPETER});
        return Integer.parseInt(stdInput.readLine());
    }

    public GameStatistics getGameStatistics(String user)
            throws IOException, InterruptedException {
        execute(new String[]{GAME_STATISTICS, user});
        return new GameStatistics(
                Integer.parseInt(stdInput.readLine()),
                Integer.parseInt(stdInput.readLine()),
                Integer.parseInt(stdInput.readLine()));
    }

    public int nextAiMoveAgainst(String user)
            throws IOException, InterruptedException {
        execute(new String[]{AI_MOVE, user});
        return Integer.parseInt(stdInput.readLine());
    }

    public String methodForChoosingAiMove() {
        try {
            String method = stdInput.readLine();
            return "AI chose using " + method;
        } catch (Exception ex) {
            return "Could not read how AI move was selected.";
        }
    }

    private void execute(String[] programName)
            throws IOException, InterruptedException {
        Process process = new ProcessBuilder(programName).start();
        process.waitFor();

        stdInput = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
    }
}
