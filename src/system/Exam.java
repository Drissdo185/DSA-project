package system;

public class Exam extends  Evaluation{
    private int timeLimit;

    public Exam(String topic, int percentage, String content, int timeLimit){
        super(topic, percentage, content);
        this.setTimeLimit(timeLimit);
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
