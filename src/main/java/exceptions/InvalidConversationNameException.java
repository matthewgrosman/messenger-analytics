package exceptions;

/**
 * Exception that is thrown when a user enters a conversation name that doesn't exist.
 */
public class InvalidConversationNameException extends Exception {
    /**
     * Exception constructor for exception that can be thrown with an error message.
     *
     * @param message   A String containing some details about the error.
     */
    public InvalidConversationNameException(String message) {
        super(message);
    }
}
