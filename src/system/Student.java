package system;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Student extends Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private double finalAverageGrade;
    private Student parent;
    private Student left;
    private Student right;

    public Student(String name, String lastName, String email, String code){
        super(name, lastName, email, code);
        setFinalAverageGrade(0.0);
    }

    public Student(String name, String lastName, String email, String code, double finalGrade) {
        super(name, lastName, email, code);
        finalAverageGrade = finalGrade;
    }


    public double getFinalAverageGrade() {
        return finalAverageGrade;
    }

    public void setFinalAverageGrade(double finalAverageGrade) {
        this.finalAverageGrade = finalAverageGrade;
    }

    public int compareByLastName(Student s1){
        return getLastName().compareToIgnoreCase(s1.getLastName());
    }

    public int compareByFinalGrade(Student other){
        if(finalAverageGrade == other.getFinalAverageGrade()) {
            return 0;
        }else if(finalAverageGrade > other.getFinalAverageGrade()) {
            return 1;
        }else {
            return -1;
        }
    }

    public Student getParent() {
        return parent;
    }

    public void setParent(Student parent) {
        this.parent = parent;
    }

    public Student getLeft() {
        return left;
    }

    public void setLeft(Student left) {
        this.left = left;
    }

    public Student getRight() {
        return right;
    }

    public void setRight(Student right) {
        this.right = right;
    }

    public String nodeToString(String separator){
        return getName() + separator + getLastName() + separator + getCode() + separator + getFinalAverageGrade() + separator + LocalDateTime.now();
    }


}
