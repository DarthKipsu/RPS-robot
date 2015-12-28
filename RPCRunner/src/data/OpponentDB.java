
package data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Opponent database will handle writing and reading of opponent data from file.
 */
public class OpponentDB {
    private final Path CURRENT_PATH = Paths.get("data/current");
    private final String OPPONENT_DIR = "data/players/";
    
    private String currentOpponent;

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

    public File selectOpponent(String name) throws IOException {
        currentOpponent = name;
        File playList = new File(OPPONENT_DIR, currentOpponent);
        if (!playList.exists()) {
            playList.createNewFile();
        }
        return playList;
    }

    public boolean nameIsValid(String name) {
        return name != null
                && !name.isEmpty()
                && !name.equals("Choose name first!");
    }
}
