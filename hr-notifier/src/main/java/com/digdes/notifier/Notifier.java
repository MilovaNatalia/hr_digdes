package com.digdes.notifier;

import com.digdes.message.Message;

public interface Notifier {
    boolean setUpSettings(SmtpSettings settings);
    boolean sendMessage(Message message);
}