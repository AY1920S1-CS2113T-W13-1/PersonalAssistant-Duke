package duke.parser;

import duke.CLIView;
import duke.data.Storage;
import duke.module.Schedule;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

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
     * Constants to represent the index 6.
     */
    private final int indexSix = 6;
    /**
     * Constants to represent the index 7.
     */
    private final int indexSeven = 7;
    /**
     * Constants to represent the index 10.
     */
    private final int indexTen = 10;
    /**
     * Boolean status to check if the class can exit.
     */
    private boolean isRunning = true;
    /**
     * The ui object responsible for showing things to the user.
     */
    private CLIView CLIView;
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
     * @throws FileNotFoundException if file does not exist
     * @throws ParseException if user input is not in the correct format
     */
    public ParserSchedule() throws FileNotFoundException, ParseException {
        CLIView = new CLIView();
        scheduleStorage = new Storage(
            ".\\src\\main\\java\\duke\\data\\timeslots.txt");
        schedule = new Schedule(scheduleStorage.loadSchedule());
        sc = new Scanner(System.in);
    }
    /**
     * Method to run when entering daily schedule.
     * @throws ParseException if user input is not in the correct format
     */
    public void dailySchedule() throws ParseException {
        CLIView.showSchedulePromptDate();
        String scheduleDate = sc.next();
        schedule.getDay(scheduleDate);
        while (isRunning) {
            try {
                CLIView.showScheduleAllActions(scheduleDate);
                int executeType = sc.nextInt();
                sc.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    System.out.println(schedule.getDay(scheduleDate));
                    break;

                case 2:
                    CLIView.showPromptStartTime();
                    String startTime = sc.nextLine();
                    CLIView.showPromptEndTime();
                    String endTime = sc.nextLine();
                    CLIView.showPromptClassLocation();
                    String location = sc.nextLine();
                    CLIView.showPromptClassName();
                    String className = sc.nextLine();
                    System.out.println(schedule.addClass(startTime,
                        endTime, location,
                        className, scheduleStorage));
                    break;

                case indexThree:
                    CLIView.showPromptStartTime();
                    String delstartTime = sc.nextLine();
                    CLIView.showPromptClassName();
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
                    CLIView.showQuitClass();
                    break;
                default:
                    CLIView.showDontKnow();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                CLIView.showFullCommand();
            } catch (ParseException e) {
                CLIView.showCorrectFormat();
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
}
