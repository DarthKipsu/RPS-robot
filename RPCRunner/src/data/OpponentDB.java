
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
    private final Path CURRENT_PATH = Paths.get("data/current");
    private final String OPPONENT_DIR = "data/players/";
    
    private String currentOpponent;
    private File opponentFile;

    public OpponentDB() {
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

    public File setOpponent(String name) throws IOException {
        currentOpponent = name;
        writeCurrentOpponentToFile();
        findOpponentFile();
        return opponentFile;
    }

    public void saveMatchOutcome(int opponent, int ai) {
        try {
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
        Files.write(CURRENT_PATH,
                Arrays.asList(currentOpponent),
                Charset.forName("UTF-8"));
    }

    private void findOpponentFile() throws IOException {
        opponentFile = new File(OPPONENT_DIR, currentOpponent);
        if (!opponentFile.exists()) {
            opponentFile.createNewFile();
        }
    }
}
