package duke.GUI;

import duke.Duke;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public final class Launcher {
    private Launcher() {
        //do nothing
    }

    /**
     * Begin GUI application and run main duke class.
     * @param args expects array of string objects
     */
    public static void main(final String[] args) {
        Application.launch(Duke.class, args);
    }
}
