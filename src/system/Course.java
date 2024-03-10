package system;

import java.util.ArrayList;

public class Course {
    private static final long serialVersionUID = 1L;
    private String courseName;
    private ArrayList<Student> students;
    private ArrayList<Activity> activities;
    private ArrayList<Exam> exams;
    private ArrayList<Grade> averageGrades;
    private Course next;

    public Course(String cn) {
        courseName = cn;
        students = new ArrayList<Student>();
        activities = new ArrayList<Activity>();
        exams = new ArrayList<Exam>();
        setAverageGrades(new ArrayList<Grade>());
    }
}
