package system;

public class Grade {
    private double grade;
    private Student student;

    public Grade(double g, Student s){
        grade = g;
        student = s;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
