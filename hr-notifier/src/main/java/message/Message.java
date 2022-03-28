package message;

import java.util.List;

public abstract class Message {
    protected String sender;
    protected List<String> receivers;
    protected String messageBody;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receiver) {
        this.receivers = receiver;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("message.Message{");
        sb.append("sender='").append(sender).append('\'');
        sb.append(", receiver='").append(receivers).append('\'');
        sb.append(", messageBody='").append(messageBody).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
