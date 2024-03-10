package system;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Teacher extends Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String password;
    private boolean fullTime; // To check the teacher is full or part-time with school
    private String career;
    private int contEvaluations;
    private Course firstCourse;
    private Teacher next;

    public Teacher(String name, String lastName, String email, String code, String password,
                   boolean fullTime, String career, int cont){
        super(name, lastName, email, code);
        this.password = password;
        this.fullTime = fullTime;
        this.career = career;
        contEvaluations = cont;
    }

    public Teacher getNext() {
        return next;
    }

    public void setNext(Teacher next) {
        this.next = next;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getContEvaluations() {
        return contEvaluations;
    }

    public void setContEvaluations(int contEvaluations) {
        this.contEvaluations = contEvaluations;
    }

    //-----------------------------------------------
    public Course getFirstCourse(){
        return firstCourse;
    }

    public ArrayList<Course> getCourses(){
        ArrayList<Course> courses = new ArrayList<Course>();
        if(firstCourse != null){
            courses.add(firstCourse);
            getCourses(firstCourse.getNext(), courses);
        }
    }


}
