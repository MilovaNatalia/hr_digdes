package notifier;

import message.Message;

public class EmailNotifier implements Notifier{
    public boolean sendMessage(Message message) {
        return false;
    }

    public boolean setUpSettings(Settings settings) {
        return false;
    }
}
