/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.client;

import ch.hslu.appe.fbs.client._old.JavaFXViewController;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author salzm
 */
public class JavaFXViewControllerTest {
    
    public JavaFXViewControllerTest() {
    }
    
    /**
     * Test of getTopPane method, of class JavaFXViewController.
     * Test for not Null
     */
    @Test
    public void testGetTopPane() {
        JavaFXViewController jvc = new JavaFXViewController();
        assertNotNull(jvc.getTopPane());
    }


    /**
     * Test of getCenterPane method, of class JavaFXViewController.
     * Test for not Null
     */
    @Test
    public void testGetCenterPane() {
        JavaFXViewController jvc = new JavaFXViewController();
        assertNotNull(jvc.getCenterPane());
    }
    
    /**
     * Test of getRightPane method, of class JavaFXViewController.
     * Test for not Null
     */
    @Test
    public void testGetRightPane() {
        JavaFXViewController jvc = new JavaFXViewController();
        assertNotNull(jvc.getRightPane());
    }
    
    /**
     * Test of getLeftPane method, of class JavaFXViewController.
     * Test for not Null
     */
    @Test
    public void testGetLeftPane() {
        JavaFXViewController jvc = new JavaFXViewController();
        assertNotNull(jvc.getLeftPane());
    }

    /**
     * Test of getBottomPane method, of class JavaFXViewController.
     * Test for not Null
     */
    @Test
    public void testGetBottomPane() {
        JavaFXViewController jvc = new JavaFXViewController();
        assertNotNull(jvc.getBottomPane());
    }
    
}
