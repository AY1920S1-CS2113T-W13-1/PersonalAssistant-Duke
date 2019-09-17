package Task;

import Data.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * TaskList handles all the operations Duke uses.
 */
public class TaskList {
    private static ArrayList<item> list = new ArrayList<>();

    /**
     * This method loads all the ArrayList items from the previous load file into the current ArrayList.
     */
    public static void addAllList () {
        try {
            list.addAll(Objects.requireNonNull(Storage.loadFile()));
        }
        catch (NullPointerException e) {
            System.out.println("No previous list loaded");
        }
    }

    /**
     * This method adds tasks to the list of tasks in duke
     *
     * @param i This is the first parameter, it takes the newly created Deadline/ToDo/Event
     * @param type This is the second parameter, defines the type of task that has been created
     */
    public static void addTask(item i, String type) {
        list.add(i);
        System.out.println("Got it. I've added this task:\n " +
                list.get(list.size() - 1).toString()+"\n" +
                "Now you have " + (list.size()) + " tasks in the list.");
        Data.Storage.saveFile(type, i, i.getDate());
    }

    /**
     * This function prints out the complete list of tasks in the ArrayList.
     */
    public static void getList() {
        int count = 1;
        for (item i: list) {
            System.out.println(count++ +"."+ i.toString());
        }
    }

    /**
     * This function changes the status of a task from incomplete to complete.
     *
     * @param index This is the index location of the task to be changed in the ArrayList
     */
    public static void doneTask (int index) {
        list.get(index).changeStatus();
        System.out.println("Nice! I've marked this task as done:\n " +
                list.get(index).toString());
        Storage.updateFile(list);
    }

    /**
     * This function deletes the task from the list of tasks.
     * After deleting the function will print out what was deleted and the number of remaining
     * tasks left in the list.
     *
     * @param index This is the index location of the task to be deleted in the ArrayList
     * @throws IndexOutOfBoundsException e
     */
    public static void deleteTask(int index) {
        System.out.println("Noted. I've removed this task:\n " + list.get(index).toString());
        System.out.println("Now you have " + (list.size() - 1) + " tasks in the list.");
        try {
            list.remove(index);
            Storage.updateFile(list);
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Opps! Sorry that item does not exist!");
        }
    }

    /**
     * This function locates all tasks that contain a keyword as defined by the user
     *
     * @param word This parameter is the defined key word that must be searched for
     */
    public static void findTask (String word) {
        int cnt = 1;
        for (item i : list) {
            if (i.getInfo().contains(word)) {
                if (cnt == 1)
                    System.out.println("Here are the matching tasks in your list:");
                System.out.println(cnt++ + ". " + i.toString());
            }
        }

        if (cnt == 1) {
            System.out.println("Sorry, there are no tasks matching your search");
        }
    }

    /**
     * This function takes in an integer number adds its correct number ordinal to the number.
     *
     * @param num This parameter is the number taken
     * @return String of the input number with the ordinal attached to the end of the number
     */
    public static String numOrdinal (int num) {
        String[] suffix = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (num) {
            case 11:
            case 12:
            case 13:
                return num + "th";
            default:
                return num + suffix[num % 10];
        }
    }

    /**
     * This function takes specific date format dd/mm/yyyy hhmm and turns it into a string phrase.
     *
     * @param date The date taken in by the function
     * @return The date that has been converted into a string phrase, if in incorrect format return original date
     * @throws StringIndexOutOfBoundsException e
     * @throws ArrayIndexOutOfBoundsException e
     * @throws ParseException thrown when date input is in incorrect format
     */
    public static Date dateConvert (String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date formatDate = simpleDateFormat.parse(date);
            return formatDate;
        }
        catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid date format");
            return null;
        }
        catch (ParseException pe) {
            System.out.println("Date error");
            return null;
        }
    }

    public static String dateRevert (String date) {
        try {
            Date newDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
            String oldDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm").format(newDateFormat);
            return oldDateFormat;
        }
        catch (ParseException pe) {
            System.err.println("Error: Date in wrong format");
            return date;
        }
    }

    /**
     * Function converts the date object to the date format 2nd of December 2019, 6pm
     *
     * @param date the date to be converted
     * @return String of new dat format
     */
    public static String dateToStringFormat (Date date) {
        String hour =  new SimpleDateFormat("h").format(date);
        String min = new SimpleDateFormat("mm").format(date);
        String marker = new SimpleDateFormat("a").format(date);
        String day = new SimpleDateFormat("d").format(date);
        String monthYear = new SimpleDateFormat("MMMMM yyyy").format(date);
        String newDateFormat = TaskList.numOrdinal(Integer.parseInt(day)) + " of " + monthYear + ", " +
                hour + (min.equals("00") ? marker : ("." + min + marker));
        return newDateFormat;
    }

    /**
     * Function checks to see which deadlines are between now and the specified end date
     *
     * @param todayDate the present time
     * @param endDate the specified end date and time
     * @return deadlineList if there are deadlines before end date they are returned as a list
     * @return null if there are no deadlines
     */
    public static ArrayList<item> getReminderList (Date todayDate, Date endDate) {
        ArrayList<item> deadlineList = new ArrayList<>();
        Boolean isNotEmpty = false;
        for (item i: list) {
            // check if deadline is before today's date,
            if (i.getType().equals("D") && i.getRawDate().before(endDate) && todayDate.before(i.getRawDate()) && !i.status) {
                deadlineList.add(i);
                isNotEmpty = true;
            }
        }
        if (isNotEmpty)
            return deadlineList;

        return null;
    }

}
