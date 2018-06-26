package game;

import engine.SimpleGameEngine;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EngineLogger {
    public final static Logger LOGGER = Logger.getLogger(SimpleGameEngine.class.getName());

    public static void initializeLogger(String path) {
        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler(path);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        }
        catch (SecurityException error) {
            System.err.println("Logging permission missing: " + error.getMessage());
        }
        catch (IOException error) {
            System.err.println("Could not open logfile: " + error.getMessage());
        }
    }
}
