package ch.hslu.appe.fbs.remote.exception;


/**
 * Exception to indicate that user is not logged in.
 * Is thrown when the sessionId is invalid
 *
 * @author Mischa Gruber
 */
public final class UserNotLoggedInException extends Exception {

    /**
     * Constructor which only passes a describing string to the super class.
     */
    public UserNotLoggedInException() {
        super("User is not logged in.");
    }
}
