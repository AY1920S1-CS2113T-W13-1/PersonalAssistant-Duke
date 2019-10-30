package duke.models;

import duke.view.CliView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

/**
 * Loads a training plan from a txt file, create new plan, or edit a plan.
 */

public class MyPlan {

    /**
     * The ui object responsible for showing things to the user.
     */
    private CliView cliView;

    /**
     * Represents the list for the current loaded plan to be viewed or edited.
     */
    private ArrayList<MyTraining> list = new ArrayList<>();
    /**
     * Represents the list for the current number of plans saved.
     */
    private ArrayList<String> toc = new ArrayList<>();
    /**
     * Represents the map of all lists loaded from the text file.
     */
    private Map<String, ArrayList<MyTraining>> map = new HashMap<>();
    /**
     * Represents the name of the individual activity in a plan.
     */
    private String name;

    /**
     * The constructor for MyPlan.
     * @param mapOfPlans map of plans
     */
    public MyPlan(final Map<String, ArrayList<MyTraining>> mapOfPlans) {
        this.map = mapOfPlans;
    }

    /**
     * A getter to retrieve the activity name in a plan.
     * @return name of activity
     */
    public String getName() {
        return this.name;
    }

    /**
     * A getter to retrieve the list of current plan loaded.
     *
     * @return the list of current plan loaded.
     */
    private ArrayList<MyTraining> getList() {
        return this.list;
    }

    /**
     * A setter to set the private list to a specified list.
     *
     * @param newList List to be loaded as into the private list
     */
    public void setList(final ArrayList<MyTraining> newList) {
        this.list = newList;
    }

    /**
     * A getter to retrieve the list of the plans present in the map.
     *
     * @return the list of present plans in the map
     */
    public ArrayList<String> getCont() {
        return this.toc;
    }

    /**
     * Creates a key for the map for the corresponding intensity & plan number.
     *
     * @param intensity intensity level of current plan
     * @param num       plan number
     * @return the key in variable type Integer.
     */
    public String createKey(final String intensity, final int num) {
        return intensity + num;
    }

    /**
     * Retrieves an sorted arraylist of keys from the map.
     *
     * @return the arraylist of keys, sorted by intensity and plan number
     */
    public ArrayList<String> keyList() {
        Set<String> keys = map.keySet();
        ArrayList<String> kl = new ArrayList<>(keys);
        Collections.sort(kl, new Comparator<String>() {
            public int compare(final String a, final String b) {
                return extractInt(a) - extractInt(b);
            }

            int extractInt(final String s) {
                String num = s.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
        return kl;
    }

    /**
     * To show the plans available for user to view.
     */
    public void showPlanList() {
        ArrayList<String> planList = keyList();
        int index = 1;
        for (String s : planList) {
            if (index == 1) {
                System.out.println(s);
            } else {
                System.out.println("\n" + s);
            }
            index++;
        }
    }

    /**
     * Clear the plan currently loaded in the list.
     */
    private void clearPlan() {
        getList().clear();
    }

    /**
     * Add an activity to a plan in the current list.
     *
     * @param newName name of new activity
     * @param newSets number of sets for the new activity
     * @param newReps number of reps for the new activity
     * @return A string to inform user of result
     */
    public String addActivity(final String newName, final int newSets,
                              final int newReps) {
        MyTraining newActivity = new MyTraining(newName, newSets, newReps);
        getList().add(newActivity);
        MyTraining t = new MyTraining(newName, newSets, newReps);
        return "You have added this activity, " + t.toString();
    }

    /**
     * Switch activity positions for current plan in the list.
     *
     * @param initial initial position of activity
     * @param end     final position of activity
     */
    public void switchPos(final int initial, final int end) {
        MyTraining s = getList().get(initial);
        getList().add(end - 1, s);
        if (initial > end) {
            getList().remove(initial + 1);
        } else {
            getList().remove(initial);
        }
    }

    /**
     * Creates an enum for intensity values to restrict it to specified values.
     */
    public enum Intensity {
        /**
         * High intensity value.
         */
        high(1),
        /**
         * Moderate intensity value.
         */
        moderate(2),
        /**
         * Relaxed intensity value.
         */
        relaxed(3);
        /**
         * Represents the value attached to the enum strings.
         */
        private final int val;

        Intensity(final int number) {
            this.val = number;
        }

        /**
         * A getter to retrieve the number attached to each enum.
         *
         * @return the attached number
         */
        public int getVal() {
            return val;
        }

        /**
         * Represents the set of enum values.
         */
        private static final Set<String> SET = new HashSet<String>(
            Intensity.values().length);
        /**
         * Represents the map of enum values.
         */
        private static final Map<Integer, Intensity> MAP = new HashMap<>();

        static {
            for (Intensity i : Intensity.values()) {
                SET.add(i.name());
                MAP.put(i.val, i);
            }
        }

        /**
         * Check if a certain string is present in the enum.
         *
         * @param value string to be checked
         * @return a boolean, true if string is present in enum and vice versa
         */
        private static boolean contains(final String value) {
            return SET.contains(value);
        }

        /**
         * Get the enum from the attached number.
         *
         * @param value number attached to enum value
         * @return enum value(high,moderate,relaxed)
         */
        public static Intensity valueOf(final int value) {
            return MAP.get(value);
        }
    }

    /**
     * View the plan loaded into the current list.
     *
     * @return the activities in the list.
     */
    public String viewPlan() {
        StringBuilder message = new StringBuilder();
        int x = 1;
        if (!getList().isEmpty()) {
            for (MyTraining i : getList()) {
                message.append("Activity ");
                message.append(x).append(": ").append(i.toString());
                if (x < getList().size() - 1) {
                    message.append("\n");
                }
                x++;
            }
            return message.toString();
        }
        return "";
    }

    /**
     * load the plan of specified intensity and value into the list.
     * @param intensity intensity of plan to be loaded
     * @param plan plan number passed as a string
     */
    public void loadPlanToList(final String intensity, final String plan) {
        clearPlan();
        if (!Intensity.contains(intensity)) {
            cliView.showIntensityLevel();
        } else {
            int planNum = Integer.parseInt(plan.split("/")[1]);
            String key = createKey(intensity, planNum);
            if (map.containsKey(key)) {
                for (MyTraining t : map.get(key)) {
                    getList().add(t);
                }
                cliView.showPlanLoaded(planNum, intensity);
            }
        }
    }

    /**
     * Save the plan in the list to the map if it is edited or newly created.
     *
     * @param newList   List to be saved to map
     * @param intensity intensity value associated with the plan
     * @param key       key associated with the plan
     */
    private void saveToMap(final ArrayList<MyTraining> newList,
                           final String intensity, final String key) {
        if (key.equals("0")) {
            int planNum = 0;
            Set<String> keys = map.keySet();
            for (String k : keys) {
                if (k.contains(intensity)) {
                    planNum++;
                }
            }
            String k = createKey(intensity, planNum);
            map.put(k, newList);
        } else {
            map.put(key, newList);
        }
    }

    /**
     * Create a plan of specified intensity.
     *
     * @param intensity intensity of plan to be created.
     */
    public void createPlan(final String intensity) {
        clearPlan();
        if (Intensity.contains(intensity)) {
            cliView.showPlanCreating(intensity);
            while (true) {
                Scanner sc = new Scanner(System.in);
                if (sc.hasNextLine()) {
                    String input = sc.nextLine();
                    if (input.equals("finalize")) {
                        cliView.showPlanCreated();
                        cliView.showSavePlanToMap();
                        break;
                    } else if (input.equals("show")) {
                        if (getList().isEmpty()) {
                            cliView.showNoActivity();
                        } else {
                            cliView.showViewPlan(viewPlan());
                            cliView.showPlanPrompt1();
                        }
                    } else {
                        String[] details = input.split(" ");
                        MyTraining a = new MyTraining(details[0],
                            Integer.parseInt(details[1]),
                            Integer.parseInt(details[2]));
                        getList().add(a);
                        int temp = getList().size() - 1;
                        cliView.showActivityAdded(getList().get(temp));
                        cliView.showPlanPrompt2();
                    }
                }
            }
            saveToMap(getList(), intensity, "0");
        } else {
            cliView.showIntensityLevel();
        }
    }

    /**
     * Delete a plan from the map.
     *
     * @param intensity intensity of plan to be deleted
     * @param planNum   plan number
     * @throws IOException IO
     */
    public void deletePlan(final String intensity,
                           final int planNum) throws IOException {
        String key = createKey(intensity, planNum);
        if (!map.containsKey(key)) {
            cliView.showIntensityAndNumber();
        } else {
            map.remove(key);
            cliView.showPlanRemoved();
        }
        //new Storage(getFilePath()).savePlans(getMap());
    }
}
