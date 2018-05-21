package ch.hslu.appe.fbs.business.utils;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class UserNotLoggedInException extends RuntimeException {

    public UserNotLoggedInException() {
        super("User is not logged in.");
    }
}
