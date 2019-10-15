package duke.module;

import duke.Data.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Goal {

    /**
     * A hash map which holds the optional goals of the day for any day.
     */
    private Map<Date, ArrayList<String>> goals;

    /**
     * Constructor for Goal objects.
     * @param dateArrayListMap The hash map of goals of the day from loading the goals.txt text file.
     */
    public Goal(Map<Date, ArrayList<String>> dateArrayListMap) {
        this.goals = dateArrayListMap;
    }

    /**
     * Shows all goals on a specific day.
     * @param day The day to view all goals.
     * @return A message containing all the goals of the day to be printed.
     * @throws ParseException if the user input is in wrong format.
     */
    public String viewGoal(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        String message = "";
        boolean hasGoal = false;
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                if (!goals.get(d).isEmpty()) {
                    hasGoal = true;
                    for (String str : goals.get(d)) {
                        message += str + "\n";
                    }
                }
            }
        }
        if (!hasGoal) {
            return "There is no goal of the day";
        } else {
            return message;
        }
    }

    /**
     * Adds a goal to the goals hash map.
     * @param date The date to add the goal to.
     * @param message The goal to add to the goals hash map.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String addGoal(String date, String message, Storage goalStorage) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(date);
        boolean alreadyHaveDate = false;
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                alreadyHaveDate = true;
                goals.get(d).add(message);
            }
        }
        if (!alreadyHaveDate) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(message);
            goals.put(today, temp);
        }
        goalStorage.updateGoal(goals);
        return "New goal of the day has been added";
    }

    /**
     * Removes a goal from the goals hash map.
     * @param day The date to remove the goal from.
     * @param message The specific goal to remove from the hash map.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String removeGoal(String day, String message, Storage goalStorage) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        if (goals.containsKey(today)) {
            goals.get(today).remove(message);
            if (goals.get(today).isEmpty()) {
                goals.remove(today);
            }
        }
        goalStorage.updateGoal(goals);
        return "Goal of the day on " + day + " has been removed";
    }

    /**
     * Removes all the goals from the goals hash map for a day.
     * @param day The date to remove all the goals from.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String removeAllGoal(String day, Storage goalStorage) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        if (goals.containsKey(today)) {
            goals.remove(today);
            goalStorage.updateGoal(goals);
            return "All the goals for the day " + day + " have been cleared";
        } else {
            return "There are no goals of the day to remove for " + day;
        }
    }
}
