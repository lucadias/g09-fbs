package ch.hslu.appe.fbs.remote;

/**
 * Enum with feedback codes in FBS.
 *
 * @author Mischa Gruber
 */
public enum FBSFeedback {
    SUCCESS,
    UNKNOWN_ERROR,
    LOCK_LOST,
    MISMATCHING_HASH,
    ARTICLE_NOT_LOCKED,
    ORDER_NOT_LOCKED,
    SESSION_ID_NOT_EXISTING,
    SESSION_ID_USERNAME_NOT_MATCHING
}
