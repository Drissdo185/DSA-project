package system;

import java.time.LocalDate;
import java.util.ArrayList;

public class Workshop extends Activity{

    private static final long serialVersionUID = 1L;
    private String answers;

    public Workshop(String topic, int percentage, String content, LocalDate date, String answers){
        super(topic, percentage, content, date);
        this.setAnswers(answers);
    }

    public Workshop(String topic, int percentage, String content, LocalDate date, String answers, ArrayList<String> helpLinks){
        super(topic, percentage, content, date);
        this.setAnswers(answers);
        super.setHelpLinks(getHelpLinks());
    }

    public String getAnswers() {
        return answers;
    }


    public void setAnswers(String answers) {
        this.answers = answers;
    }

}
