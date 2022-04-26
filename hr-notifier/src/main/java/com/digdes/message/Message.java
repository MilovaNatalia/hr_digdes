package com.digdes.message;

import java.util.List;

public interface Message {
    String getFrom();
    void setFrom(String from);
    String[] getTo();
    void setTo(String[] to);
    String getSubject();
    void setSubject(String subject);
    String getBody();
    void setBody(String body);
}
