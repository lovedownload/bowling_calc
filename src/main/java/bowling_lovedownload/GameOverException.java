package bowling_lovedownload;

/**
 * Exception thrown when a bowling game is completed.
 */
public class GameOverException extends Exception {

    /**
     * Serial version UID for serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a GameOverException with no message.
     */
    public GameOverException() {
        super();
    }

    /**
     * Constructs a GameOverException with the specified message.
     * 
     * @param message the detail message
     */
    public GameOverException(String message) {
        super(message);
    }
}
