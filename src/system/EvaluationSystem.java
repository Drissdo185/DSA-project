package system;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class EvaluationSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    Teacher firstTeacher;
    Teacher logged;

    public EvaluationSystem(){
        firstTeacher = null;
    }

    public Teacher getFirstTeacher(){
        return firstTeacher;
    }

    public void setFirstTeacher(Teacher firstTeacher){
        this.firstTeacher = firstTeacher;
    }

    public Teacher getLogged() {
        return logged;
    }

    public void setLogged(Teacher logged) {
        this.logged = logged;
    }

    public void addTeacher(String name, String lastName, String email, String code, String password, boolean fullTime, String career, int contEvaluations) throws IOException {
        Teacher newTeacher = new Teacher(name, lastName, email, code, password, fullTime, career, contEvaluations);
        if(firstTeacher == null) {
            firstTeacher = newTeacher;
        }else {
            addTeacher(firstTeacher, newTeacher);
        }
    }

    private void addTeacher(Teacher current, Teacher newTeacher) throws IOException {
        if(current.getNext() == null) {
            current.setNext(newTeacher);
        }else {
            addTeacher(current.getNext(), newTeacher);
        }
    }

    public void updateTeacher(String name, String lastName, String email, String code, String password, boolean fullTime) throws IOException {
        logged.setName(name);
        logged.setLastName(lastName);
        logged.setEmail(email);
        logged.setCode(code);
        logged.setPassword(password);
        logged.setFullTime(fullTime);
    }

    public Teacher searchTeacher(String code) {
        Teacher teacherSearched = null;
        if(firstTeacher != null) {
            if(firstTeacher.getCode().equals(code)) {
                teacherSearched = firstTeacher;
            }else {
                return searchTeacher(code, firstTeacher.getNext());
            }
        }else {
            return teacherSearched;
        }
        return teacherSearched;
    }

    private Teacher searchTeacher(String code, Teacher current) {
        Teacher teacherSearched = null;
        if(current != null) {
            if(current.getCode().equals(code)) {
                teacherSearched = current;
            }else {
                teacherSearched = searchTeacher(code, current.getNext());
            }
        }
        return teacherSearched;
    }

    public void importTeachers(String fileName, String separator) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line!=null ) {
            String[] parts = line.split(separator);
            String name = parts[0];
            String lastName = parts[1];
            String email = parts[2];
            String code = parts[3];
            String password = parts[4];
            boolean fullTime = Boolean.parseBoolean(parts[5]);
            String career = parts[6];
            int contEvaluations = Integer.parseInt(parts[7]);
            addTeacher(name, lastName, email, code, password, fullTime, career, contEvaluations);
            line = br.readLine();
        }
        br.close();
    }

    public void exportTeachers(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        Teacher current = firstTeacher;
        while(current != null) {
            pw.write(current.getName() + separator + current.getLastName() + separator + current.getEmail() + separator + current.getCode()
                    + separator + current.getPassword() + separator + current.isFullTime() + separator + current.getCareer() + separator + current.getContEvaluations());
            current = current.getNext();
        }
        pw.close();
    }

}
