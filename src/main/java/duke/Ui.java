package duke;

import duke.Data.Parser;
import duke.Sports.ManageStudents;
import duke.Task.TaskList;
import duke.Data.Storage;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class Ui {

    /**
     * This function prints out the welcome message of Duke
     */
    public void welcome() {
        System.out.println("Hello! I'm Duke, your personal sports manager!\n" +
                "What can I do for you?");
    }

    public void mainMenu() {
        System.out.println("SPORTS MANAGER\n" +
                "1. View Training Schedule\n" +
                "2. Manage Students\n" +
                "3. Training Circuits");
    }

    /**
     * This function prints out the goodbye message of Duke
     */
    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * This function prints out the heading when option 1 is chosen
     */
    public void trainingScheduleHeading() {
        System.out.flush();
        System.out.println("TRAINING SCHEDULE:\n");
    }

    /**
     * This function prints out the heading when option 2 is chosen
     */
    public void manageStudentsHeading() {
        System.out.flush();
        System.out.println("MANAGE STUDENTS:\n");
    }

    /**
     * This function prints out the heading when option 3 is chosen
     */
    public void trainingProgramHeading() {
        System.out.flush();
        System.out.println("TRAINING PROGRAM:\n");
    }
    /**
     * This function takes the standard input defined by the user and passes it
     * into the Parser class
     * @param input This is the string input defined by the user
     */
    public void readCommand(String input, TaskList tasks, Storage storage, ManageStudents students) throws FileNotFoundException, ParseException {
        Parser parser = new Parser();
        parser.parseInput(input, tasks, storage, students);
    }

}
