package notifier;

import message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifier implements Notifier{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendMessage(Message message) {
        try {
            javaMailSender.send(convertMessage(message));
            //log
            return true;
        } catch (MailException e) {
            //log
        }
        return false;
    }

    private SimpleMailMessage convertMessage(Message message) {
        if (message == null) {
            //log
            throw new MailSendException("Message can't be empty");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(message.getFrom());
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getBody());
        return mailMessage;
    }
    public boolean setUpSettings(Settings settings) {
        return false;
    }
}
