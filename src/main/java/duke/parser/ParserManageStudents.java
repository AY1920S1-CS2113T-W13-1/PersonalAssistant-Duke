package duke.parser;

import duke.models.students.ManageStudents;
import duke.models.students.MyStudent;
import java.util.Scanner;
import duke.view.CliView;

/**
 * This is the parser for manage students.
 * @author danisheddie
 */
public final class ParserManageStudents implements IParser {
    /**
     * Boolean status to check if the class can exit.
     */
    private boolean isRunning = true;
    /**
     * Declaring Manage Students Object.
     */
    private ManageStudents students;
    /**
     * The scanner object to take input.
     */
    private Scanner sc;

    /**
     * Constructor for Manage Students Parser.
     */
    ParserManageStudents() {
        students = new ManageStudents();
        sc = new Scanner(System.in);
    }

    /**
     * To parse ManageStudents commands.
     *
     * @param input command.
     */
    @Override
    public void parseCommand(final String input) {
        String[] word = input.split(" ");
        String cmd = word[0];
        boolean runManageStudent = true;
        switch (cmd) {
        case "add":
            System.out.println("____________________________"
                   + "________________________");
            System.out.println("Insert [Name],[Age],[Address] "
                    + "to add new student.\n"
                    + "Insert 1 to exit.");
            new CliView().showDontKnow();
            addCommand();
            break;
        // Format: student delete [index]
        case "delete":
            students.deleteStudent(Integer.parseInt(word[1]));
            break;

        case "details":
            System.out.println("Details for: ");
            if (sc.equals("add details")) {
                System.out.println("Details for: ");

            }
            String studentName = sc.nextLine();
            students.findName(studentName);
            //add student details
            break;

        case "edit":
            System.out.print("What do you want to edit for ");
            students.getStudentName(Integer.parseInt(word[1]));
            System.out.println("?");
            // editStudentDetails(detail)
            break;

        case "list":
            students.listAllStudents();
            break;

        case "search":
            final int limit = 5;
            String searchName = input.substring(limit);
            students.findName(searchName);
            break;

        case "select":
            System.out.print("You have selected: ");
            students.getStudentName(Integer.parseInt(word[1]));
            break;

        case "particulars":
            // Edit particulars of the student
            break;

        case "progress":
            //Add student progress
            break;

        case "back":
            runManageStudent = false;
            break;

        default:
            System.out.println("Incorrect Command.");
        }
    }

    /**
     * Method to parse add command.
     */
    public void addCommand() {
        String newStudent = sc.nextLine();
        String[] splitByComma = newStudent.split(",");
        String name = splitByComma[0];
        String age = splitByComma[1];
        String address = splitByComma[2];
        MyStudent myNewStudent = new MyStudent(
                name, age, address);
        students.addStudent(myNewStudent);
    }
}
