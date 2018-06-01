package ch.hslu.appe.fbs.remote.exception;


/**
 * Exception to indicate that the lock check has failed.
 *
 * @author Mischa Gruber
 */
public final class LockCheckFailedException extends Exception {

    /**
     * Constructor which only passes a describing string to the super class.
     */
    public LockCheckFailedException() {
        super("Lock couldn't be acquired.");
    }
}
