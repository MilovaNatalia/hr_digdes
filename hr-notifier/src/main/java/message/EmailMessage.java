package message;

public class EmailMessage extends Message {
    //TODO other fields for email message?
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EmailMessage{");
        sb.append("subject='").append(subject).append('\'');
        sb.append(", sender='").append(sender).append('\'');
        sb.append(", receivers=").append(receivers);
        sb.append(", messageBody='").append(messageBody).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
