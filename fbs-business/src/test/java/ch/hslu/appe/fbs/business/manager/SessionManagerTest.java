package ch.hslu.appe.fbs.business.manager;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class SessionManagerTest {

    @Test
    public void testLoginWithCorrectPassword() {
        SessionManager sessionManager = SessionManager.getInstance();

        String sessionId = sessionManager.login("mischa", "82ddf06044ac2494ea18823e7541ca425689043b237707204218e414109d942e");
        assertNotEquals(null, sessionId);
    }

    @Test
    public void testLoginWithFalsePassword() {
        SessionManager sessionManager = SessionManager.getInstance();

        String sessionId = sessionManager.login("mischa", "gruber");
        assertEquals(null, sessionId);
    }
}
