package ch.hslu.appe.fbs.business.utils;

import java.security.SecureRandom;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class SessionIdGenerator {

    private final static int sessionIdLength = 128;
    private final static String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String lowerChars = "abcdefghijklmnopqrstuvwxyz";
    private final static String numerics = "0123456789";
    private final static String alphanum = upperChars + lowerChars + numerics;

    public static String getNewId() {
        SecureRandom random = new SecureRandom();
        char[] buffer = new char[sessionIdLength];
        char[] symbols = alphanum.toCharArray();

        for (int i=0; i<sessionIdLength; i++) {
            buffer[i] = symbols[random.nextInt(symbols.length)];
        }

        return new String(buffer);
    }

    public static int getSessionIdLength() {
        return sessionIdLength;
    }
}
