package ch.hslu.appe.fbs.remote.exception;


/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException() {
        super("User is not logged in.");
    }
}
