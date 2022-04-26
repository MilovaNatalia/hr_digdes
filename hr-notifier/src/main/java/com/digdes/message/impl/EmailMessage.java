package com.digdes.message.impl;

import com.digdes.message.Message;

import java.util.Arrays;
import java.util.Objects;

public class EmailMessage implements Message {
    private String from;

    private String[] to;

    private String subject;

    private String body;

    public EmailMessage() {
    }

    public EmailMessage(String from, String[] to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailMessage that = (EmailMessage) o;
        return Objects.equals(from, that.from) && Arrays.equals(to, that.to) && Objects.equals(subject, that.subject) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(from, subject, body);
        result = 31 * result + Arrays.hashCode(to);
        return result;
    }

    //todo: pretty to string
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EmailMessage{");
        sb.append("from='").append(from).append('\'');
        sb.append(", to=").append(to == null ? "null" : Arrays.asList(to).toString());
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
