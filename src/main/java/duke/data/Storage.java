package duke.data;

import duke.models.TimeSlot;
import duke.models.MyClass;
import duke.models.manageStudents.MyStudent;
import duke.models.MyTraining;
import duke.models.MyPlan;
import duke.task.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Storage handles all the loading and saving of data
 * from and into the respective text files.
 */
public class Storage {
    /**
     * File path of designated file.
     * Possible alternative:
     * private String filePath = System.getProperty("user.dir");
     */
    private String filePath;
    /**
     * The file of the saved data.
     */
    private Scanner fileInput;


    /**
     * Constructor.
     *
     * @param path The file path of the designated file.
     * @throws FileNotFoundException is thrown when
     *                               file designated cannot be found.
     */
    public Storage(final String path) throws FileNotFoundException {
        filePath = path;
        File f = new File(filePath);
        fileInput = new Scanner(f);
    }

    /**
     * This function saves the newly created task into duke.txt.
     *
     * @param type The type of task created
     * @param e    The task created to be saved
     * @param date The date of the task created
     * @throws IOException io
     */
    public void saveFile(final String type, final Item e, final String date) {
        try {
            if (type.equals("T")) {
                FileWriter fileWriter = new FileWriter(filePath, true);

                fileWriter.write(type + "-" + e.checkStatus()
                    + "-" + e.getInfo() + "-" + e.getDuration() + "\n");
                fileWriter.close();
            } else if (type.equals("C")) {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus()
                    + "-" + e.getInfo() + "-" + date + "\n");
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-"
                    + e.getInfo() + "-" + e.getRawDate() + "\n");
                fileWriter.close();
            }
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * Converts date format.
     *
     * @param date The string of date.
     * @return String of converted date object
     */
    public String dateRevert(final String date) {
        try {
            Date newDateFormat = new SimpleDateFormat(

                "EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
            String oldDateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HHmm").format(newDateFormat);
            return oldDateFormat;
        } catch (ParseException pe) {
            System.err.println("Error: Date in wrong format");
            return date;
        }
    }

    /**
     * This function parses the info of the duke.txt into an ArrayList.
     *
     * @return ArrayList containing all the parsed data from the duke.txt file
     * @throws FileNotFoundException          e
     * @throws ArrayIndexOutOfBoundsException e
     */
    public ArrayList<Item> loadFile() {
        try {
            ArrayList<Item> list = new ArrayList<>();
            while (fileInput.hasNextLine()) { //do something
                final String type;
                final String info;
                final int indexDay = 3;

                Boolean stat;
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                type = data[0];
                stat = (data[1].equals("1"));
                info = data[2];
                switch (type) {
                case "C":
                    String day = data[indexDay];
                    Item myClass = new MyClass(info, stat, day);
                    list.add(myClass);
                    break;

                default:
                    System.out.println("No data");
                }
            }
            fileInput.close();
            return list;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * This function updates the list of tasks.
     * Erases the entire list that exists presently and rewrites the file.
     *
     * @param up The updated ArrayList that must
     *           be used to recreate the updated duke.txt
     * @throws IOException io
     */
    public void updateFile(final ArrayList<Item> up) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        for (Item i : up) {
            try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(i.getType() + "-" + i.checkStatus() + "-"
                    + i.getInfo() + "-" + i.getRawDate() + "\n");
                fileWriter.close();
            } catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
            }
        }
    }

    /**
     * Reads filePath, takes in Strings and turns them into a
     * list of TimeSlot objects.
     *
     * @return lists of TimeSlot objects
     * @throws ParseException when parsing fails
     */
    public ArrayList<TimeSlot> loadSchedule() throws ParseException {
        try {
            ArrayList<TimeSlot> temp = new ArrayList<>();
            final int indexStartDate = 1;
            final int indexEndDate = 2;
            final int indexLocation = 3;
            final int indexName = 4;

            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                String startDate = data[indexStartDate];
                String endDate = data[indexEndDate];
                String location = data[indexLocation];
                String name = data[indexName];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy HHmm");
                Date date1 = simpleDateFormat.parse(startDate);
                Date date2 = simpleDateFormat.parse(endDate);
                TimeSlot t = new TimeSlot(date1, date2, location, name);
                temp.add(t);
            }
            fileInput.close();
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function saves the newly created TimeSlot into timeslots.txt.
     *
     * @param t The TimeSlot object created to be saved
     */
    public void saveSchedule(final TimeSlot t) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateFormat df = new SimpleDateFormat("HHmm");
            fileWriter.write(t.getClassName() + "-"
                + df.format(t.getStartTime()) + "-"
                + df.format(t.getEndTime()) + "-"
                + t.getLocation() + "\n");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * This function updates the list of tasks.
     *
     * @param up The updated ArrayList that must be used to recreate the
     *           updated timeslots.txt
     */
    public void updateSchedule(final ArrayList<TimeSlot> up) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        for (TimeSlot t : up) {
            try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HHmm");
                fileWriter.write(t.getClassName() + "-"
                    + df.format(t.getStartTime()) + "-"
                    + df.format(t.getEndTime()) + "-"
                    + t.getLocation() + "\n");
                fileWriter.close();
            } catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
            }
        }
    }

    /**
     * Reads filePath, takes in Strings and turns them into a hash map of goals.
     *
     * @return A hash map of goals.
     * @throws ParseException if the user input is in wrong format.
     */
    public Map<Date, ArrayList<String>> loadGoal() throws ParseException {
        try {
            Map<Date, ArrayList<String>> temp = new HashMap<>();
            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy");
                Date date = simpleDateFormat.parse(data[0]);
                ArrayList<String> temp2 = new ArrayList<>();
                for (String str : data) {
                    if (!str.equals(data[0])) {
                        temp2.add(str);
                    }
                }
                temp.put(date, temp2);
            }
            fileInput.close();
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function updates the hash map of goals.
     * Erases the entire hash map that exists presently and rewrites the file.
     *
     * @param goals The updated hash map that must be used to recreate
     *              the updated goals.txt
     * @throws IOException io if the file cannot be found.
     */
    public void updateGoal(final Map<Date, ArrayList<String>> goals) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            for (Map.Entry<Date, ArrayList<String>> entry : goals.entrySet()) {
                String extra = "";
                ArrayList<String> temp = entry.getValue();
                for (String str : temp) {
                    extra += "-" + str;
                }
                fileWriter.write(df.format(entry.getKey()) + extra + "\n");
            }
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * Reads filePath, takes in Strings and turns them into a
     * hash map of lessons learnt for the day.
     *
     * @return A hash map of lessons learnt for the day.
     * @throws ParseException if the user input is in wrong format.
     */
    public Map<Date, ArrayList<String>> loadLesson() throws ParseException {
        try {
            Map<Date, ArrayList<String>> temp = new HashMap<>();
            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy");
                Date date = simpleDateFormat.parse(data[0]);
                ArrayList<String> temp2 = new ArrayList<>();
                for (String str : data) {
                    if (!str.equals(data[0])) {
                        temp2.add(str);
                    }
                }
                temp.put(date, temp2);
            }
            fileInput.close();
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function updates the hash map of lessons learnt for the day.
     * Erases the entire hash map that exists presently and rewrites the file.
     *
     * @param lessons The updated hash map that must be used
     *                to recreate the updated lessons.txt
     * @throws IOException io if the file cannot be found.
     */
    public void updateLesson(final Map<Date, ArrayList<String>> lessons) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateFormat df = new SimpleDateFormat(
                "dd/MM/yyyy");
            for (Map.Entry<Date, ArrayList<String>> entry
                : lessons.entrySet()) {
                String extra = "";
                ArrayList<String> temp = entry.getValue();
                for (String str : temp) {
                    extra += "-" + str;
                }
                fileWriter.write(df.format(entry.getKey()) + extra + "\n");
            }
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * Update the student list file.
     *
     * @param student The list of students to be changed
     */
    public void updateStudentList(final ArrayList<MyStudent> student) {
        File studentListFile = new File(
            ".\\src\\main\\java\\duke\\data\\studentList.txt");
        try {
            PrintWriter printWriter = new PrintWriter(studentListFile);
            for (MyStudent x : student) {
                printWriter.println(x.toString() + "\n");
            }
            printWriter.close();
            printWriter.write("");
            printWriter.close();
        } catch (IOException io) {
            System.out.println("File not found: " + io.getMessage());
        }
    }

    /**
     * Load the file for the student list.
     *
     * @param student list to be read
     */
    public void readStudentListFile(final ArrayList<MyStudent> student) {
        String fileName = "studentList.txt";
        String line;
        ArrayList loadStudent = new ArrayList();

        try {
            FileReader fr = new FileReader(
                ".\\src\\main\\java\\duke\\data\\studentList.txt");
            BufferedReader input = new BufferedReader(fr);
            if (!input.ready()) {
                throw new IOException();
            }
            while ((line = input.readLine()) != null) {
                String[] splitter = line.split("\n");
                for (int i = 0; i < splitter.length; i++) {
                    splitter[i] = splitter[i].trim();
                }
                MyStudent studentInfo = new MyStudent(splitter[0],
                    splitter[1], splitter[2]);
                student.add(studentInfo);
            }
            fr.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Load plans from the text file to a map.
     *
     * @param map the map of plans to be saved to
     * @throws FileNotFoundException File not found
     */
    public void loadPlans(final Map<String,
        ArrayList<MyTraining>> map) throws FileNotFoundException {
        //MyPlan plan = new MyPlan();
        ArrayList<MyTraining> list = new ArrayList<>();

        File f = new File(".\\src\\main\\java\\duke\\data\\plan.txt");
        String intensity = "";
        int planNum = 0;
        try {
            if (f.length() == 0) {
                System.out.println("Plan file is empty. Loading failed.");
            } else {
                while (fileInput.hasNextLine()) {
                    String in = fileInput.nextLine();

                    if (in.contains("Intensity")) {
                        String[] line = in.split(": ");
                        if (line[1].equals("high")) {
                            MyPlan.Intensity x = MyPlan.Intensity.high;
                            intensity = x.toString();
                        } else if (line[1].equals("moderate")) {
                            MyPlan.Intensity x = MyPlan.Intensity.moderate;
                            intensity = x.toString();
                        } else if (line[1].equals("relaxed")) {
                            MyPlan.Intensity x = MyPlan.Intensity.relaxed;
                            intensity = x.toString();
                        }
                    }

                    if (in.contains("Plan")) {
                        String[] line = in.split(": ");
                        planNum = Integer.parseInt(line[1]);
                    }

                    if (in.contains(" | ")) {
                        String[] line = in.split(" \\| ");
                        MyTraining ac = new MyTraining(line[0],
                            Integer.parseInt(line[1]),
                            Integer.parseInt(line[2]));
                        list.add(ac);
                    }

                    if (in.equals("\n")) {
                        //String key = plan.createKey(intensity, planNum);
                        //map.put(key, list);
                        System.out.println("Check");
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception: " + e);
        }
    }

    /**
     * Saves the map of plans to the text file after clearing it.
     *
     * @param map Updated map of plans to be saved
     * @throws IOException IO
     */
    public void savePlans(final Map<String, ArrayList<MyTraining>> map)
        throws IOException {
        MyPlan plan = new MyPlan();
        ArrayList<String> keys = plan.keyList();

        PrintWriter clear = new PrintWriter(
            ".\\src\\main\\java\\duke\\data\\plan.txt");
        clear.close();

        BufferedWriter buffer = new BufferedWriter(
            new FileWriter(".\\src\\main\\java\\duke\\data\\plan.txt",
                true));

        for (int i = 1; i <= MyPlan.Intensity.values().length; i++) {
            MyPlan.Intensity x = MyPlan.Intensity.valueOf(i);
            buffer.write("Intensity: " + x);
            buffer.write("\r\n");
            for (String s : keys) {
                if (s.contains(x.toString())) {
                    ArrayList<MyTraining> p = map.get(s);
                    buffer.write("Plan: " + s.substring(s.length()));
                    for (MyTraining y : p) {
                        buffer.write(y.toFile());
                        buffer.write("\r\n");
                    }
                }
            }
        }
        buffer.close();
    }
}
