package system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Activity extends Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate assignmentDate;
    private ArrayList<String> helpLinks;

    public Activity(String topic, int percentage, String content, LocalDate date){
        super(topic, percentage, content);
        assignmentDate = date;
        helpLinks = new ArrayList<String>();
    }


    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDate assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public ArrayList<String> getHelpLinks() {
        return helpLinks;
    }

    public void setHelpLinks(ArrayList<String> helpLinks) {
        this.helpLinks = helpLinks;
    }

}
