package ch.hslu.appe.fbs.business.utils;

import java.security.SecureRandom;

/**
 * Random Session Id Generator.
 *
 * @author Mischa Gruber
 */
public final class SessionIdGenerator {

    private static final int SESSION_ID_LENGTH = 128;
    private static final String UPPER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERICS = "0123456789";
    private static final String ALPHANUM = UPPER_CHARS + LOWER_CHARS + NUMERICS;

    /**
     * Private constructor to prevent having a public one.
     */
    private SessionIdGenerator() {

    }

    /**
     * Generates a new session id string.
     * @return new generated session id
     */
    public static String getNewId() {
        SecureRandom random = new SecureRandom();
        char[] buffer = new char[SESSION_ID_LENGTH];
        char[] symbols = ALPHANUM.toCharArray();

        for (int i = 0; i < SESSION_ID_LENGTH; i++) {
            buffer[i] = symbols[random.nextInt(symbols.length)];
        }

        return new String(buffer);
    }

    /**
     * Returns the length of session id strings.
     * @return length of session id strings
     */
    public static int getSessionIdLength() {
        return SESSION_ID_LENGTH;
    }
}
