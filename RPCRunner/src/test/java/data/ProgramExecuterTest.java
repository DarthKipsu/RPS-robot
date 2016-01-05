
package data;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ProgramExecuterTest {
    private ProgramExecuter exe;
    
    @Before
    public void setUp() {
        exe = new ProgramExecuter(
            "../MachineLearning/mocks/mprophet.py",
            "../MachineLearning/mocks/mstatistics.py",
            "../MachineLearning/mocks/mrpc_ai.py");
    }

    @Test
    public void predictImageSignReturnsThePredictedSign() throws Exception {
        assertEquals(0, exe.predictImageSign());
    }

    @Test
    public void statisticsReturnsGameStatistics() throws Exception {
        assertEquals(GameStatistics.class,
                exe.getGameStatistics("some user").getClass());
    }

    @Test
    public void statisticsReturnsCorrectStatisticsString() throws Exception {
        String expected = "25 games played, were you have \nwon 6 times\nlost "
                + "10 times\nplayed 9 draws\n";
        assertEquals(expected, exe.getGameStatistics("some user").statistics());
    }

    @Test
    public void statisticsReturnsCorrectAiWinRatioString() throws Exception {
        String expected = "AI win ratio is 62%";
        assertEquals(expected, exe.getGameStatistics("some user").aiWinRatio());
    }

    @Test
    public void nextAiMoveReturnsNextAiMove() throws Exception {
        assertEquals(1, exe.nextAiMoveAgainst("Verna"));
    }

    @Test
    public void afterNextAiMoveSelectionMethodIsAvailable() throws Exception {
        exe.nextAiMoveAgainst("Verna");
        assertEquals("AI chose using random", exe.methodForChoosingAiMove());
    }

    @Test
    public void noMethodIsAvailableWhenMoveHasNotBeenMade() throws Exception {
        assertEquals("Could not read how AI move was selected.",
                exe.methodForChoosingAiMove());
    }
}
