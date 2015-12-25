
package data;

import java.io.IOException;

/**
 * Executes programs from outside Java package.
 */
public class ProgramExecuter {
    
    public void execute(String programName) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(programName).start();
        process.waitFor();

        System.out.printf("Output of running MachineLearning is %s", process.exitValue());
    }
}
