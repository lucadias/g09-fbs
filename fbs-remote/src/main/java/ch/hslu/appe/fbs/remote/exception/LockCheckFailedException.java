package ch.hslu.appe.fbs.remote.exception;

import java.rmi.RemoteException;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class LockCheckFailedException extends Exception {

    public LockCheckFailedException() {
        super("Lock couldn't be acquired.");
    }
}
