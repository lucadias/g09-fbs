package ch.hslu.appe.fbs.business.log;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.simple.SimpleLogger;;
import java.io.*;
import java.sql.SQLException;

import java.util.*;



public class FBSLogger {

    // Define a static logger variable so that it references the
    // Logger instance named "MyApp".
    private static final Logger log = LogManager.getLogger(FBSLogger.class);

    public static void main(final String... args) {

        // Set up a simple configuration that logs on the console.

        log.trace("Entering application.");

        log.trace("Exiting application.");
    }
}
