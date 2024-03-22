package system;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        if(firstCourse != null) {
            courses.add(firstCourse);
            getCourses(firstCourse.getNext(), courses);

        }
        return courses;
    }


    public void getCourses(Course course, ArrayList<Course> courses){
        if(firstCourse != null) {
            courses.add(firstCourse);
            getCourses(firstCourse.getNext(), courses);
        }
    }

    public void setFirstCourse(Course firstCourse){
        this.firstCourse = firstCourse;
    }

    public void addCourse(String name){
        Course newCourse = new Course(name);
        if(firstCourse == null){
            firstCourse = newCourse;
        }else{
            addCourse(firstCourse, newCourse);
        }
    }
    public void addCourse(Course current, Course newTeacher) {
        if(current.getNext() == null) {
            current.setNext(newTeacher);
        }else {
            addCourse(current.getNext(), newTeacher);
        }
    }

    public void updateCourseName(Course c, String newName) {
        c.setCourseName(newName);
    }

    @SuppressWarnings("unused")
    public void removeCourse(Course c) {
        Course thisCourse = searchCourse(c.getCourseName());
        thisCourse = null;
    }

    public Course searchCourse(String name) {
        Course courseSearched = null;
        if(firstCourse != null) {
            if(firstCourse.getCourseName().equals(name)) {
                courseSearched = firstCourse;
            }else {
                return searchCourse(name, firstCourse.getNext());
            }
        }else {
            return courseSearched;
        }
        return courseSearched;
    }

    public Course searchCourse(String name, Course current) {
        Course courseSearched = null;
        if(current != null) {
            if(current.getCourseName().equals(name)) {
                courseSearched = current;
            }else {
                courseSearched = searchCourse(name, current.getNext());
            }
        }
        return courseSearched;
    }

    public Course binarySearchCourse(String courseName) {
        Comparator<Course> name = new Comparator<Course>() {
            @Override
            public int compare(Course obj1, Course obj2) {
                String n1 = obj1.getCourseName().toLowerCase();
                String n2 = obj2.getCourseName().toLowerCase();

                return n2.compareTo(n1);
            }
        };

        Course key = new Course(courseName);
        int index = Collections.binarySearch(getCourses(), key, name);
        if (index < 0) {
            key = null;
        } else {
            key = getCourses().get(index);
        }
        return key;
    }
}
