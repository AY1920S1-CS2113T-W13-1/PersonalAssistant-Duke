package duke.parser;

import duke.view.CliView;

import java.io.FileNotFoundException;

import java.text.ParseException;
import java.util.Scanner;

public class ParserCommand implements IParser {
    /**
     * Declaring type ManageStudentsParser.
     */
    private ParserManageStudents parserManageStudents
        = new ParserManageStudents();
    /**
     * Declaring ParserTrainingPLan type.
     */
    private ParserTrainingPlan parserTrainingPlan
        = new ParserTrainingPlan();

    /**
     * Parse the respective command.
     *
     * @param input command.
     * @throws FileNotFoundException throws exception.
     */
    public void parseCommand(final String input) {
        try {
            CliView cliView = new CliView();
            Scanner sc = new Scanner(System.in);
            final String scheduleMenu = "1";
            switch (input) {
            case scheduleMenu:
                ParserSchedule parserSchedule = new ParserSchedule();
                parserSchedule.parseCommand();
                break;
            case "2":
                cliView.manageStudentsHeading();
                String studentsInput = sc.nextLine();
                parserManageStudents.parseCommand(studentsInput);
                break;
            case "3":
                cliView.trainingProgramHeading();
                String trainingInput = sc.nextLine();
                parserTrainingPlan.parseCommand(trainingInput);
                break;
            default:
                System.out.println("OOPS!!! I'm sorry,"
                    + "but I don't know what that means :-(");
                break;
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}
