
package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes programs from outside Java package.
 */
public class ProgramExecuter {
    
    /**
     * Execute a program based on given string. Executed program needs to print
     * one integer, which will be returned.
     * @param programName execute path
     * @return output as a single integer
     */
    public int execute(String programName) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(programName).start();
        process.waitFor();
        
        BufferedReader stdInput = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        return Integer.parseInt(stdInput.readLine());
    }
}
