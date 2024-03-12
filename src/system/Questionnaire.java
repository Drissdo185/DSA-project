package system;
import java.time.LocalDate;
import java.util.ArrayList;
public class Questionnaire extends Activity {
    private static final long serialVersionUID = 1L;
    private int attempts;

    public Questionnaire(String topic, int percentage, String content, LocalDate date, int attempts){
        super(topic, percentage, content, date);
        this.setAttempts(attempts);
    }

    public Questionnaire(String topic, int percentage, String content, LocalDate date, int attempts, ArrayList<String> helpLinks){
        super(topic, percentage, content, date);
        this.setAttempts(attempts);
        super.setHelpLinks(getHelpLinks());
    }

    public int getAttempts(){
        return attempts;
    }

    public void setAttempts(int attempts){
        this.attempts = attempts;
    }
}
