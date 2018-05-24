package ch.hslu.appe.fbs.client;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joel salzmann
 */
public class LoginViewControllerTest {

    /**
     * Test of sha256 method, of class LoginViewController when password is empty
     */
    @Test
    public void testSha256() {
        LoginViewController viewController = new LoginViewController();
        String result = viewController.sha256("");
        assertEquals(result.length(), 64);
    }

    /**
     * Test of checkLogin method, of class LoginViewController when username and password are empty
     */
    @Test
    public void testCheckEmptyLogin() {
        LoginViewController viewController = new LoginViewController();
        Boolean result = viewController.checkLogin("", "");
        assertEquals(result, false);
    }
    
}
