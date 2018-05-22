package ch.hslu.appe.fbs.business.utils;

import java.rmi.RemoteException;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class LockCheckFailedException extends RemoteException {

    public LockCheckFailedException() {
        super("Lock couldn't be acquired.");
    }
}
