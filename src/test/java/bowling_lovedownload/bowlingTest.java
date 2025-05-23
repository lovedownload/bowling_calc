package bowling_lovedownload;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test class for BowlingGame
 */
public class BowlingTest {
    
    private BowlingGame bowlingGame;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @Before
    public void setup() {
        bowlingGame = new BowlingGame();
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outContent));
    }
    
    /**
     * Test method to validate first roll validation
     */
    @Test
    public void testValidateFirstRoll() {
        // Test with valid input
        int result = bowlingGame.validateFirstRoll(5, 0);
        assertEquals(5, result);
        
        // Test with input that would be invalid (but we don't test
        // the interactive validation here as it uses Scanner)
    }
    
    /**
     * Test method to validate second roll validation
     */
    @Test
    public void testValidateSecondRoll() {
        // Set up first roll
        bowlingGame.setFirstRoll(3, 0);
        
        // Test with valid input for second roll
        int result = bowlingGame.validateSecondRoll(7, 0);
        assertEquals(7, result);
        
        // We don't test interactive validation here
    }
    
    /**
     * Test the formatting of pin display
     */
    @Test
    public void testFormatPinDisplay() {
        // Test gutter ball (0 pins) in normal frame
        bowlingGame.setSecondRoll(0, 0);
        assertEquals("-|", bowlingGame.formatPinDisplay(0));
        
        // Test gutter ball in 10th frame
        bowlingGame.setSecondRoll(0, 18);
        assertEquals("-|-|", bowlingGame.formatPinDisplay(18));
        
        // Test normal pins
        bowlingGame.setSecondRoll(5, 2);
        assertEquals("5|", bowlingGame.formatPinDisplay(2));
    }
    
    /**
     * Test a simple game with all gutters (0 pins)
     */
    @Test
    public void testAllGutterGame() {
        simulateGame(new int[]{0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0});
        assertEquals(0, bowlingGame.getTotalScore());
    }
    
    /**
     * Test a perfect game (all strikes)
     */
    @Test
    public void testPerfectGame() {
        simulateGame(new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10});
        assertEquals(300, bowlingGame.getTotalScore());
    }
    
    /**
     * Utility method to simulate a game with predefined rolls
     * 
     * @param rolls The array of pin counts for each roll
     */
    private void simulateGame(int[] rolls) {
        // Build input string from roll values
        StringBuilder input = new StringBuilder();
        for (int roll : rolls) {
            input.append(roll).append(System.lineSeparator());
        }
        
        // Set the input
        System.setIn(new ByteArrayInputStream(input.toString().getBytes()));
        
        // TODO: Implement automated game simulation
        // This would require refactoring of BowlingGame to accept programmatically
        // provided rolls instead of always reading from System.in
    }
    
    /**
     * Reset System.out and System.in after tests
     */
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(System.in);
    }
}
