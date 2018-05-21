package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.remote.FBSFeedback;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticleManagerTest {

    private static String sessionId = "";

    @Test
    public void testLock() {
        ArticleManager articleManager = ArticleManager.getInstance();

        String lockHash = articleManager.lock(sessionId, 10002);
        FBSFeedback feedback = articleManager.release(sessionId, 10002, lockHash);

        assertNotEquals(null, lockHash);
    }

    @Test
    public void testRelease() {
        ArticleManager articleManager = ArticleManager.getInstance();

        String lockHash = articleManager.lock(sessionId, 10002);
        FBSFeedback feedback = articleManager.release(sessionId, 10002, lockHash);

        assertEquals(FBSFeedback.SUCCESS, feedback);
    }
}
