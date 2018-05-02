package ch.hslu.appe.fbs.business.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class SessionIdGeneratorTest {

    @Test
    public void testLength() {
        assertEquals(SessionIdGenerator.getSessionIdLength(), SessionIdGenerator.getNewId().length());
    }

    @Test
    public void testDifferentIds() {
        String sessionId1 = SessionIdGenerator.getNewId();
        String sessionId2 = SessionIdGenerator.getNewId();
        String sessionId3 = SessionIdGenerator.getNewId();
        assertNotEquals(sessionId1, sessionId2);
        assertNotEquals(sessionId1, sessionId3);
        assertNotEquals(sessionId2, sessionId3);
    }
}
