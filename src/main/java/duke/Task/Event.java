package duke.Task;

import javafx.concurrent.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends item {

    protected Date at;

    /**
     * This method is the constructor used to create the Event class
     * @param info This is the information about the task being added
     * @param status This determines if whether the item added is completed or uncompleted
     */
    public Event(String info, Boolean status, String at) {
        super(info, status);
        super.setType("E");
        this.at = TaskList.dateConvert(at);
    }

    /**
     * This function takes the "at" data in the Event class and converts it into the string output format
     *  Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate () {
        return TaskList.dateToStringFormat(at);
    }

    /**
     * Function gets the unformatted date of at
     *
     * @return at
     */
    @Override
    public Date getRawDate() {
        return this.at;
    }

    /**
     * This function gets the type, information, and date of the task
     * @return String phrase with the type, info and date
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + getDate() + ")";
    }
}