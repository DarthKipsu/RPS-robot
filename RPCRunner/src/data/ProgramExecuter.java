
package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes programs from outside Java package.
 */
public class ProgramExecuter {
    
    public void execute(String programName) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(programName).start();
        process.waitFor();
        
        BufferedReader stdInput = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        System.out.printf("Output of running MachineLearning is %s", stdInput.readLine());
    }
}
