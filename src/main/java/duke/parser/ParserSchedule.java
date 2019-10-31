package duke.parser;

import duke.view.CliView;
import duke.data.Storage;
import duke.models.Schedule;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

//@@author Sfloydzy
public class ParserSchedule {
    /**
     * Constants to represent the index 3.
     */
    private final int indexThree = 3;
    /**
     * Constants to represent the index 4.
     */
    private final int indexFour = 4;
    /**
     * Constants to represent the index 5.
     */
    private final int indexFive = 5;

    /**
     * Boolean status to check if the class can exit.
     */
    private boolean isRunning = true;
    /**
     * The ui object responsible for showing things to the user.
     */
    private CliView cliView;
    /**
     * The schedule object.
     */
    private Schedule schedule;
    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner sc;
    /**
     * The storage object for Schedule.
     */
    private Storage scheduleStorage;

    /**
     * Constructor for ParserSchedule.
     *
     * @throws FileNotFoundException if file does not exist
     * @throws ParseException        if user input is not in the correct format
     */
    public ParserSchedule() throws FileNotFoundException, ParseException {
        cliView = new CliView();
        sc = new Scanner(System.in);
        schedule = new Schedule();
    }

    /**
     * Method to run when entering daily schedule.
     *
     * @throws ParseException if user input is not in the correct format
     */
    public void dailySchedule() {
        cliView.showSchedulePromptDate();
        sc.nextLine();
        String scheduleDate = sc.nextLine();
        // String day = schedule.getDay(scheduleDate);
        // schedule of timeslot.txt cannot be loaded
        // todo must fix the storage loading capabilities
        String day = "empty";
        if (day.equals("empty")) {
            isRunning = false;
            cliView.showEmptyList();
        }
        while (isRunning) {
            try {
                cliView.showScheduleAllActions(scheduleDate);
                int executeType = sc.nextInt();
                sc.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    System.out.println(schedule.getDay(scheduleDate));
                    break;

                case 2:
                    cliView.showPromptStartTime();
                    cliView.showPromptEndTime();
                    cliView.showPromptClassLocation();
                    String className = sc.nextLine();
                    String startTime = sc.nextLine();
                    String endTime = sc.nextLine();
                    String location = sc.nextLine();
                    cliView.showPromptClassName();
                    System.out.println(schedule.addClass(startTime,
                        endTime, location,
                        className, scheduleStorage));
                    break;

                case indexThree:
                    cliView.showPromptStartTime();
                    String delstartTime = sc.nextLine();
                    cliView.showPromptClassName();
                    String delclassName = sc.nextLine();
                    System.out.println(
                        schedule.delClass(
                            delstartTime, delclassName, scheduleStorage));
                    break;

                case indexFour:
                    System.out.println(
                        schedule.delAllClass(scheduleDate, scheduleStorage));
                    break;

                case indexFive:
                    isRunning = false;
                    cliView.showQuitClass();
                    break;
                default:
                    cliView.showDontKnow();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                cliView.showFullCommand();
            } catch (ParseException e) {
                cliView.showCorrectFormat();
            }
        }
    }

    /**
     * Method to run when entering weekly schedule.
     */
    public void weeklySchedule() {
        System.out.println(schedule.getWeek());
    }

    /**
     * Method to run when entering monthly schedule.
     */
    public void monthlySchedule() {
        System.out.println(schedule.getMonth());
    }

    //@@author Sfloydzy

    /**
     * Method to parse the command input from the menu.
     * @throws ParseException Parse
     * @throws FileNotFoundException File not found
     */
    public void parseCommand() throws ParseException, FileNotFoundException {
        final int dailySchedule = 1;
        final int weeklySchedule = 2;
        final int monthlySchedule = 3;
        final int back = 4;
        int input;
        boolean runSchedule = true;
        while (runSchedule) {
            cliView.trainingScheduleHeading();
            input = sc.nextInt();
            switch (input) {
            case dailySchedule:
                boolean runDaily = true;
                while (runDaily) {
                    cliView.dailyScheduleHeading();
                    input = sc.nextInt();
                    if (input == 1) { //access daily schedule
                        dailySchedule();
                    } else if (input == 2) { //access daily goals
                        new ParserGoal().runGoal();
                    } else if (input == 3) { //access daily lessons
                        new ParserLesson().runLesson();
                    } else if (input == 4) { //back
                        runDaily = false;
                    } else {
                        cliView.showDontKnow();
                    }
                }
                break;
            case weeklySchedule:
                weeklySchedule();
                break;
            case monthlySchedule:
                monthlySchedule();
                break;
            case back:
                runSchedule = false;
                break;
            default:
                cliView.showDontKnow();
            }
        }
    }
}
