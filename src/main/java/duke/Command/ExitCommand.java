package duke.Command;

import duke.Ui;
import duke.data.Storage;
import duke.module.Schedule;
import duke.sports.ManageStudents;
import duke.sports.MyPlan;
import duke.task.TaskList;

/**
 * Represents a subclass of Command class which ends the program.
 */
public class ExitCommand extends Command {
    /**
     * Ends the program and shows the farewell message to the user.
     * @param tasks The ArrayList of Task objects.
     * @param ui The Ui object to manage user interface to user.
     * @param storage The Storage object to save and load user's tasks.
     * @param schedule The Schedule object to store classes in timeslots.
     * @param students The ManageStudents object to manage students in classes.
     * @param plan The MyPlan object to manage the training plans.
     */
    public void execute(final TaskList tasks, final Ui ui,
                        final Storage storage, final Schedule schedule,
                        final ManageStudents students, final MyPlan plan) {
        makeExitTrue();
        ui.showGoodBye();
    }
}