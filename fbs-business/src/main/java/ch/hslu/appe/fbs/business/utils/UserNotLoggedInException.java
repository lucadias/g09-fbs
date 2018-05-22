package ch.hslu.appe.fbs.business.utils;

import java.rmi.RemoteException;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class UserNotLoggedInException extends RemoteException {

    public UserNotLoggedInException() {
        super("User is not logged in.");
    }
}
