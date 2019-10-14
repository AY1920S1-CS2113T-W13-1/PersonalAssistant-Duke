package duke.sports;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a student.
 */
public class MyStudent {

    /**
     * Represents the name of the student.
     */
    private String name;

    /**
     * Represents the age of the student.
     */
    private String age;

    /**
     * Represents the address of the student.
     */
    private String address;

    private String filePath;

    private Scanner fileInput;


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Constructor for the students.
     *
     * @param myName Name of the student
     * @param myAge age of the student
     * @param myAddress address of the student
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public MyStudent(String myName, String myAge,
                      String myAddress) {
        this.name = myName;
        this.age = myAge;
        this.address = myAddress;
    }

//    public MyStudent() throws FileNotFoundException {
//        filePath = ".\\src\\main\\java\\duke\\Data\\studentList.txt";
//        File f = new File(filePath);
//        fileInput = new Scanner(f);
//        //division = new Storage(filePath).loadPMap(getMap());
//    }

    /**
     * This method is to retrieve the name of the student.
     * @return name of student
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method is to retrieve the age of the student.
     * @return age of student
     */
    private String getAge() {
        return this.age;
    }

// --Commented out by Inspection START (8/10/2019 3:55 PM):
//    /**
//     * This method is to edit the name of the student.
//     * @param newName Represents the name of the student to change to.
//     */
//    public void changeName(final String newName) {
//        this.name = newName;
//    }
// --Commented out by Inspection STOP (8/10/2019 3:55 PM)
    /**
     * This method is to retrieve the address of the student.
     * @return Represents the address of the student.
     */
    private String getAddress() {
        return address;
    }

    /**
     * This method prints out the student name and their address.
     * (Or any RELEVANT details)
     * @return Represents a string containing the student details to be shown,
     * name and address.
     */
    public String toString() {
        return "Name: " + getName() + "\nAge: " + getAge() + "\nAddress: " + getAddress();
    }


}
