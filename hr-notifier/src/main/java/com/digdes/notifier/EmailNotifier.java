package com.digdes.notifier;

import com.digdes.message.Message;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Properties;


@Component
public class EmailNotifier implements Notifier{

    public static final String FROM_EMAIL = "javaschool2022@gmail.com";

    private final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    public EmailNotifier() {
        configureMailSender(javaMailSender);
    }

    @Override
    public boolean sendMessage(Message message) {
        try {
            javaMailSender.send(convertMessage(message));
            //log
            //todo exception
            return true;
        } catch (MailException e) {
            //log
        }
        return false;
    }

    private SimpleMailMessage convertMessage(Message message) {
        if (message == null) {
            throw new MailSendException("Message can't be empty");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(message.getFrom());
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getBody());
        return mailMessage;
    }
    public boolean setUpSettings(SmtpSettings settings) {
        try {
            Properties properties = new Properties();
            File fileProps = ResourceUtils.getFile("classpath:gmail.properties");
            try (FileWriter writer = new FileWriter(fileProps)){
                properties.setProperty("host", settings.getHost());
                properties.setProperty("port", String.valueOf(settings.getPort()));
                properties.setProperty("username", settings.getUsername());
                properties.setProperty("password", settings.getPassword());
                properties.store(writer, "Change settings");
                writer.flush();
            }
            configureMailSender(javaMailSender);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void configureMailSender(JavaMailSenderImpl javaMailSender){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(ResourceUtils.getFile("classpath:gmail.properties")));
            javaMailSender.setHost(properties.getProperty("host"));
            javaMailSender.setPort(Integer.parseInt(properties.getProperty("port")));
            javaMailSender.setUsername(properties.getProperty("username"));
            javaMailSender.setPassword(properties.getProperty("password"));
            Properties props = javaMailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.debug", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //todo: log this
        }

    }
}
