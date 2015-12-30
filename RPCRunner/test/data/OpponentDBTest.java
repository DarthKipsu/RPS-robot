
package data;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OpponentDBTest {
    private final Path vernaPath = Paths.get("testdata/players/Verna");
    private final Path currentPath = Paths.get("testdata/current");
    private OpponentDB db;
    
    @Before
    public void setUp() {
        new File("testdata").mkdir();
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("testdata"));
    }

    @Test
    public void getOpponentReturnsEmptyWhenNoCurrentPlayerIsSet() {
        db = new OpponentDB("testdata");
        assertEquals("", db.getOpponent());
    }

    @Test
    public void getOpponentReturnsTheNAmeOfTheCurrentPlayerFromFile() throws Exception {
        Files.write(currentPath,
                Arrays.asList("Verna"),
                Charset.forName("UTF-8"));

        db = new OpponentDB("testdata");
        assertEquals("Verna", db.getOpponent());
    }

    @Test
    public void setOpponentChangesTheOpponentWhenNoPreviousIsSelected() throws Exception {
        db = new OpponentDB("testdata");
        db.setOpponent("Ilari");
        assertEquals("Ilari", db.getOpponent());
    }

    @Test
    public void setOpponentChangesTheOpponentWhenThereIsPreviousOpponent() throws Exception {
        Files.write(currentPath,
                Arrays.asList("Verna"),
                Charset.forName("UTF-8"));

        db = new OpponentDB("testdata");
        db.setOpponent("Ilari");
        assertEquals("Ilari", db.getOpponent());
    }

    @Test
    public void matchOutcomeIsSavedForNewOpponent() throws Exception {
        db = new OpponentDB("testdata");
        db.setOpponent("Verna");
        db.saveMatchOutcome(2, 0);
        List<String> outcomes = Files.readAllLines(vernaPath, Charset.forName("UTF-8"));
        assertEquals("2 0", outcomes.get(0));
    }

    @Test
    public void matchOutcomeIsAppendedForExistingOpponent() throws Exception {
        Files.write(currentPath,
                Arrays.asList("Verna"),
                Charset.forName("UTF-8"));
        new File("testdata/players").mkdir();
        new File(vernaPath.toString()).createNewFile();
        Files.write(vernaPath, Arrays.asList("1 2", "0 0"), Charset.forName("UTF-8"));

        db = new OpponentDB("testdata");
        db.saveMatchOutcome(2, 0);
        List<String> outcomes = Files.readAllLines(vernaPath, Charset.forName("UTF-8"));
        assertEquals("1 2", outcomes.get(0));
        assertEquals("0 0", outcomes.get(1));
        assertEquals("2 0", outcomes.get(2));
    }

    @Test
    public void nameIsValidReturnsTrueWhenEverythingIsOkay() {
        db = new OpponentDB("testdata");
        assertTrue(db.nameIsValid("Verna"));
    }

    @Test
    public void emptyNameIsNotValid() {
        db = new OpponentDB("testdata");
        assertFalse(db.nameIsValid(""));
        assertFalse(db.nameIsValid(null));
    }

    @Test
    public void chooseNameFirstIsNotValidName() {
        db = new OpponentDB("testdata");
        assertFalse(db.nameIsValid("Choose name first!"));
    }
}
