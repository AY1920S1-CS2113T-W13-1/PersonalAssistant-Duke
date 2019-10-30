package duke.command;

import duke.data.Storage;
import duke.models.Goal;
import duke.models.Lesson;
import duke.view.CliView;

import java.text.ParseException;
import java.util.Scanner;

/**
 * Represents a subclass of Command class which is responsible
 * for the deleting of all types of items.
 */
public class DeleteCommand {
    /**
     * The ui object responsible for showing things to the user.
     */
    private CliView cliView = new CliView();

    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner deleteScan;

    //@@author nottherealedmund
    /**
     * The method to delete a Goal of the day.
     */
    public void deleteGoal(Goal goal, Storage goalStorage, String goalDate) throws ParseException {
        cliView.showGoalPromptDeleteGoal(goalDate);
        String message = deleteScan.nextLine();
        System.out.println(
            goal.removeGoal(
                goalDate, message, goalStorage));
    }

    //@@author nottherealedmund
    /**
     * The method to delete a Lesson of the day.
     */
    public void deleteLesson(Lesson lesson, Storage lessonStorage, String lessonDate) throws ParseException {
        cliView.showLessonPromptDeleteLesson(lessonDate);
        String message = deleteScan.nextLine();
        System.out.println(
            lesson.removeLesson(
                lessonDate, message, lessonStorage));
    }

    //@@author nottherealedmund
    /**
     * The method to delete all Goals of the day.
     */
    public void deleteAllGoals(Goal goal, Storage goalStorage, String goalDate) throws ParseException {
        System.out.println(goal.removeAllGoal(
            goalDate, goalStorage));
    }

    //@@author nottherealedmund
    /**
     * The method to delete all Lessons of the day.
     */
    public void deleteAllLessons(Lesson lesson, Storage lessonStorage, String lessonDate) throws ParseException {
        System.out.println(lesson.removeAllLesson(
            lessonDate, lessonStorage));
    }
}
