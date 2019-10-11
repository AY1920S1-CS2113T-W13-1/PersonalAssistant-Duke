package duke.Module;

import duke.sports.MyStudent;

public class Details {

    public String studentName;

    public String detailDescription;

    public String detailMessage;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    /**
     * Constructor for Detail class
     * @param studentName
     * @param detailDescription
     * @param detailMessage
     */
    public Details (String studentName, String detailDescription, String detailMessage) {
        this.studentName = studentName;
        this.detailDescription = detailDescription;
        this.detailMessage = detailMessage;
    }






}
