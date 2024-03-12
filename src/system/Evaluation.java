package system;

import java.io.Serializable;

public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String topic;
    private int percentage;
    private String content;

    public Evaluation(String topic, int percentage, String content){
        this.topic = topic;
        this.percentage = percentage;
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
