package notifier;

import message.Message;

public interface Notifier {
    //TODO notifier properties
    boolean setUpSettings(Settings settings);
    boolean sendMessage(Message message);
}