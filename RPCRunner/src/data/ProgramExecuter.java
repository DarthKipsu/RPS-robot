
package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes programs from outside Java package.
 */
public class ProgramExecuter {

    private final String IMAGE_INTERPETER = "../MachineLearning/prophet.py";

    /**
     * Predicts the sign of an image saved to disc as temp file.
     * 0 = rock
     * 1 = paper
     * 2 = scissors
     * @return sign predicted
     */
    public int predictImageSign() throws IOException, InterruptedException {
        return execute(IMAGE_INTERPETER);
    }

    private int execute(String programName)
            throws IOException, InterruptedException {
        Process process = new ProcessBuilder(programName).start();
        process.waitFor();

        BufferedReader stdInput = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        return Integer.parseInt(stdInput.readLine());
    }
}
