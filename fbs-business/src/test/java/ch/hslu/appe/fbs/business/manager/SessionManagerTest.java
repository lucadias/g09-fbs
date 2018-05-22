package ch.hslu.appe.fbs.business.manager;

import org.junit.BeforeClass;
import org.junit.Test;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class SessionManagerTest {

    private static MessageDigest digest;

    @BeforeClass
    public static void init(){
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String generateHash(String password) {
        byte[] hash = new byte[0];
        try {
            hash = digest.digest(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @Test
    public void testLoginWithCorrectPassword() {
        SessionManager sessionManager = SessionManager.getInstance();

        String username = "mischa";
        String password = "gruber";
        String passwordHashed = this.generateHash(password);

        String sessionId = sessionManager.login(username, passwordHashed);
        assertNotEquals(null, sessionId);
    }

    @Test
    public void testLoginWithFalsePassword() {
        SessionManager sessionManager = SessionManager.getInstance();
        String username = "mischa";
        String password = "gruber_";
        String passwordHashed = this.generateHash(password);

        String sessionId = sessionManager.login(username, passwordHashed);
        assertEquals(null, sessionId);
    }
}
