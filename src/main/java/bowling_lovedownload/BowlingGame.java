package bowling_lovedownload;

import java.util.Scanner;

/**
 * Represents a game of bowling, handling scoring and display.
 */
public class BowlingGame {
    
    private int[] pinsKnockedDown = new int[24];  // Array to track pins knocked down in each roll
    private int consecutiveStrikes = 0;           // Counter for consecutive strikes
    private int spareFlag = 0;                    // Flag to track if previous frame was a spare
    private int totalScore = 0;                   // Current total score
    private int lastFrameIndex = 0;               // Tracks the last frame index for bonus rolls
    private String pinDisplay = "";               // String representation of pins knocked down (for display)
    private String scoreDisplay = "";             // String representation of scores (for display)
    private Scanner scanner = new Scanner(System.in);  // Scanner for user input
    
    /**
     * Process the tenth frame of the bowling game.
     * Tenth frame has special handling for strikes and spares.
     * 
     * @param frameIndex The starting index for the tenth frame
     */
    public void processTenthFrame(int frameIndex) {
        
        System.out.println();
        System.out.print("10th frame first roll: ");        
        
        for(int rollIndex = frameIndex; rollIndex < 20; rollIndex++) {
            lastFrameIndex = rollIndex;
            if(rollIndex > 18 && consecutiveStrikes > 0) {
                System.out.println();
                System.out.print("10th frame second roll: ");
            }
            
            if(rollIndex > 18 && spareFlag == 1) {
                break;
            }
        
            pinsKnockedDown[rollIndex] = scanner.nextInt();
        
            validateFirstRoll(pinsKnockedDown[rollIndex], rollIndex);
            
            if(rollIndex > 18 && pinsKnockedDown[rollIndex] < 10) {
                recordPinScore(rollIndex);
                break;
            }
            
            if(pinsKnockedDown[rollIndex] == 10) {
                recordStrike(rollIndex);
                continue;
            }
            
            if(pinsKnockedDown[rollIndex] < 10) {
                recordPinScore(rollIndex);
                System.out.println();
                System.out.print("10th frame second roll: ");
                pinsKnockedDown[rollIndex+1] = scanner.nextInt();
                validateSecondRoll(pinsKnockedDown[rollIndex+1], rollIndex);
                recordSpare(rollIndex);
            }
        }
    
        // Handle bonus rolls
        if(consecutiveStrikes == 1 || consecutiveStrikes == 2 || spareFlag == 1) {
            System.out.println();
            System.out.print("10th frame bonus roll: ");
            pinsKnockedDown[lastFrameIndex + 1] = scanner.nextInt();
        }
        
        // Handle strike in first roll of 10th frame
        if(consecutiveStrikes == 1) {
            validateSecondRoll(pinsKnockedDown[lastFrameIndex + 1], lastFrameIndex);
            
            if(pinsKnockedDown[lastFrameIndex] + pinsKnockedDown[lastFrameIndex+1] == 10) {
                pinDisplay += "/|";
                totalScore = totalScore + 20;
            }
            
            if(pinsKnockedDown[lastFrameIndex] + pinsKnockedDown[lastFrameIndex + 1] < 10) {
                pinDisplay += pinsKnockedDown[lastFrameIndex + 1] + "|";
                totalScore = totalScore + 10 + pinsKnockedDown[lastFrameIndex] + pinsKnockedDown[lastFrameIndex + 1];
            }
            
            scoreDisplay += String.format("%5d|", totalScore);
            consecutiveStrikes = 0;
            Scoreboard.print(pinDisplay, scoreDisplay);
        }
        
        // Handle consecutive strikes in 10th frame
        if(consecutiveStrikes == 2) {
            validateFirstRoll(pinsKnockedDown[lastFrameIndex + 1], lastFrameIndex);
            
            if(pinsKnockedDown[lastFrameIndex + 1] == 10) {
                pinDisplay += "X|";
                totalScore = totalScore + 30;
            }
            
            if(pinsKnockedDown[lastFrameIndex + 1] < 10) {
                pinDisplay += pinsKnockedDown[lastFrameIndex + 1] + "|";
                totalScore = totalScore + 20 + pinsKnockedDown[lastFrameIndex + 1];
            }
            
            scoreDisplay += String.format("%5d|", totalScore);
            consecutiveStrikes = 0;
            Scoreboard.print(pinDisplay, scoreDisplay);
        }
        
        // Handle spare in 10th frame
        if(spareFlag == 1) {
            validateFirstRoll(pinsKnockedDown[lastFrameIndex + 1], lastFrameIndex);
            
            if(pinsKnockedDown[lastFrameIndex + 1] == 10) {
                pinDisplay += "X|";
                totalScore = totalScore + 20;
            }
            
            if(pinsKnockedDown[lastFrameIndex + 1] < 10) {
                pinDisplay += pinsKnockedDown[lastFrameIndex + 1] + "|";
                totalScore = totalScore + 10 + pinsKnockedDown[lastFrameIndex + 1];
            }
            
            scoreDisplay += String.format("%5d|", totalScore);
            spareFlag = 0;
            Scoreboard.print(pinDisplay, scoreDisplay);
        }
        
        endGame();
    }

    /**
     * End the game and display the final score.
     * Throws GameOverException to signal the end of game.
     */
    public void endGame() {
        try {
            System.out.println();
            System.out.println("Game Over!");
            System.out.println("Your final score is " + totalScore + "!");
            throw new GameOverException("Game completed successfully");
        } catch (GameOverException e) {
            System.out.println();
            e.printStackTrace();
        }
    }
    
    /**
     * Validate the first roll of a frame.
     * 
     * @param firstRoll The number of pins knocked down in first roll
     * @param frameIndex The current frame index
     * @return The validated pin count
     */
    public int validateFirstRoll(int firstRoll, int frameIndex) {
        
        while(firstRoll < 0 || firstRoll > 10) {
            System.out.println("Please enter a number between 0 and 10.");
            System.out.print("Enter: ");        
            firstRoll = scanner.nextInt();    
        }    
        return firstRoll;
    }
    
    /**
     * Validate the second roll of a frame, ensuring total pins don't exceed 10.
     * 
     * @param secondRoll The number of pins knocked down in second roll
     * @param frameIndex The current frame index
     * @return The validated pin count
     */
    public int validateSecondRoll(int secondRoll, int frameIndex) {
        
        while(secondRoll + pinsKnockedDown[frameIndex] > 10 || secondRoll + pinsKnockedDown[frameIndex] < 0) {
            System.out.println("Total pins cannot exceed 10. Please try again.");
            System.out.print("Enter: ");
            secondRoll = scanner.nextInt();
        }    
        return secondRoll;
    }
    
    /**
     * Record the pin score and update the display.
     * 
     * @param frameIndex The current frame index
     */
    public void recordPinScore(int frameIndex) {
        
        pinDisplay += pinsKnockedDown[frameIndex] + "|";
        
        if((consecutiveStrikes == 0 || consecutiveStrikes == 1) && spareFlag == 0) {
            Scoreboard.print(pinDisplay, scoreDisplay);    
        }
        
        if (consecutiveStrikes == 2) {
            for(int j = 0; j < consecutiveStrikes; j++) {
                totalScore = totalScore + 10;
            }
            
            totalScore = totalScore + pinsKnockedDown[frameIndex];
            scoreDisplay += String.format("%3d|", totalScore);
            consecutiveStrikes = consecutiveStrikes - 1;
            Scoreboard.print(pinDisplay, scoreDisplay);
        }
        
        if(spareFlag == 1) {
            totalScore = totalScore + 10 + pinsKnockedDown[frameIndex];
            scoreDisplay += String.format("%3d|", totalScore);
            spareFlag = 0;
            Scoreboard.print(pinDisplay, scoreDisplay);
        }
    }
    
    /**
     * Handle strike scoring and display.
     * 
     * @param frameIndex The current frame index
     */
    public void recordStrike(int frameIndex) {
        if(frameIndex >= 18) {
            pinDisplay += "X|";
        }
        
        if(frameIndex < 18) {
            pinDisplay += "X| |";
            pinsKnockedDown[frameIndex+1] = 0;
        }
        
        if(consecutiveStrikes < 3) {
            consecutiveStrikes = consecutiveStrikes + 1;
        }
        
        if(spareFlag == 0 && consecutiveStrikes < 3) {
            Scoreboard.print(pinDisplay, scoreDisplay);
            return;
        }
        
        if(consecutiveStrikes == 3) {
            totalScore = totalScore + 30;
            scoreDisplay += String.format("%3d|", totalScore);
            consecutiveStrikes = consecutiveStrikes - 1;
            Scoreboard.print(pinDisplay, scoreDisplay);
            return;
        }
        
        if(spareFlag == 1) {
            totalScore = totalScore + 20;
            scoreDisplay += String.format("%3d|", totalScore);
            spareFlag = 0;
            Scoreboard.print(pinDisplay, scoreDisplay);
            return;
        }        
    }
    
    /**
     * Handle spare scoring and display.
     * 
     * @param frameIndex The current frame index
     */
    public void recordSpare(int frameIndex) {
        if(consecutiveStrikes == 1) {
            totalScore = totalScore + 10 + pinsKnockedDown[frameIndex] + pinsKnockedDown[frameIndex+1];
            scoreDisplay += String.format("%3d|", totalScore);
            consecutiveStrikes = 0;
        }
        
        if(pinsKnockedDown[frameIndex] + pinsKnockedDown[frameIndex+1] == 10 && frameIndex < 19) {
            spareFlag = 1;
            pinDisplay += "/|";
            Scoreboard.print(pinDisplay, scoreDisplay);
            return;
        }
        
        if(pinsKnockedDown[frameIndex] + pinsKnockedDown[frameIndex+1] < 10) {
            pinDisplay += formatPinDisplay(frameIndex);
            totalScore = totalScore + pinsKnockedDown[frameIndex] + pinsKnockedDown[frameIndex+1];
            formatAndDisplayScore(frameIndex);
        }
    }
    
    /**
     * Format and display the score for non-strike/spare frames.
     * 
     * @param frameIndex The current frame index
     */
    public void formatAndDisplayScore(int frameIndex) {
        // Tenth frame case
        if(frameIndex == 18) {
            scoreDisplay += String.format("%5d|", totalScore);
            Scoreboard.print(pinDisplay, scoreDisplay);
            endGame();
            return;
        }
        
        scoreDisplay += String.format("%3d|", totalScore);
        Scoreboard.print(pinDisplay, scoreDisplay);
        return;
    }
    
    /**
     * Format the pin display, handling gutter balls (0 pins).
     * 
     * @param frameIndex The current frame index
     * @return Formatted pin display string
     */
    public String formatPinDisplay(int frameIndex) {
        
        if(pinsKnockedDown[frameIndex+1] == 0 && frameIndex < 18) {
            return "-|";
        }
        
        if(pinsKnockedDown[frameIndex+1] == 0 && frameIndex == 18) {
            return "-|-|";
        }
        
        return pinsKnockedDown[frameIndex+1] + "|";
    }
    
    /**
     * Set the first roll of a frame.
     * 
     * @param firstRoll The number of pins knocked down
     * @param frameIndex The current frame index
     */
    public void setFirstRoll(int firstRoll, int frameIndex) {
        pinsKnockedDown[frameIndex] = firstRoll;
    }
    
    /**
     * Set the second roll of a frame.
     * 
     * @param secondRoll The number of pins knocked down
     * @param frameIndex The current frame index
     */
    public void setSecondRoll(int secondRoll, int frameIndex) {
        pinsKnockedDown[frameIndex+1] = secondRoll;
    }

    /**
     * Get the current total score.
     * 
     * @return The total score
     */
    public int getTotalScore() {
        return totalScore;
    }
}
