
package data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Opponent database will handle writing and reading of opponent data from file.
 */
public class OpponentDB {
    private final Path CURRENT_PATH;
    private final String OPPONENT_DIR;
    private String currentOpponent;

    public OpponentDB(String dataDirectory) {
        CURRENT_PATH = Paths.get(dataDirectory + "/current");
        OPPONENT_DIR = dataDirectory + "/players/";
        try {
            currentOpponent = Files.readAllLines(CURRENT_PATH,
                    Charset.forName("UTF-8")).get(0);
        } catch (IOException ex) {
            currentOpponent = "";
        }
    }

    public String getOpponent() {
        return currentOpponent;
    }

    public void setOpponent(String name) throws IOException {
        currentOpponent = name;
        writeCurrentOpponentToFile();
    }

    public void saveMatchOutcome(int opponent, int ai) {
        try {
            makeSureDirectoryExists(OPPONENT_DIR);
            makeSureFileExists(OPPONENT_DIR + currentOpponent);
            Files.write(Paths.get(OPPONENT_DIR + currentOpponent),
                    Arrays.asList(opponent + " " + ai),
                    Charset.forName("UTF-8"),
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
        }
    }

    public boolean nameIsValid(String name) {
        return name != null
                && !name.isEmpty()
                && !name.equals("Choose name first!");
    }

    private void writeCurrentOpponentToFile() throws IOException {
        makeSureFileExists(CURRENT_PATH.toString());
        Files.write(CURRENT_PATH,
                Arrays.asList(currentOpponent),
                Charset.forName("UTF-8"));
    }

    private void makeSureDirectoryExists(String directory) throws IOException {
        if (Files.notExists(Paths.get(directory))) {
            new File(directory).mkdir();
        }
    }

    private void makeSureFileExists(String file) throws IOException {
        if (Files.notExists(Paths.get(file))) {
            new File(file).createNewFile();
        }
    }
}
