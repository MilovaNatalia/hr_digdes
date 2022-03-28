package notifier;

import message.Message;

public interface Notifier {
    boolean sendMessage(Message message);
    boolean setUpSettings(Settings settings);
}
