package bowling_lovedownload;

/**
 * Utility class for printing the bowling scoreboard.
 */
public class Scoreboard {
    
    private static final String BOARD_HEADER = "-------------------------------------------";
    private static final String FRAME_NUMBERS = "| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |  10 |";
    
    /**
     * Private constructor to prevent instantiation of utility class
     */
    private Scoreboard() {
        // Utility class should not be instantiated
    }
    
    /**
     * Prints the current state of the bowling game scoreboard.
     * 
     * @param pinDisplay The string representation of pins knocked down
     * @param scoreDisplay The string representation of scores
     */
    public static void print(String pinDisplay, String scoreDisplay) {  
        System.out.println();
        System.out.println(BOARD_HEADER);
        System.out.println(FRAME_NUMBERS);
        System.out.println(BOARD_HEADER);
        System.out.println("|" + pinDisplay);
        System.out.println(BOARD_HEADER);
        System.out.println("|" + scoreDisplay);
        System.out.println(BOARD_HEADER);
    }
}
