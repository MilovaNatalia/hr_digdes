package com.digdes.dto;

import java.util.Objects;

public class SettingsDto {
    private String smtp_server;
    private String smtp_port;
    private String smtp_auth_user;
    private String smtp_auth_pwd;

    public SettingsDto() {
    }

    public SettingsDto(String smtp_server, String smtp_port, String smtp_auth_user, String smtp_auth_pwd) {
        this.smtp_server = smtp_server;
        this.smtp_port = smtp_port;
        this.smtp_auth_user = smtp_auth_user;
        this.smtp_auth_pwd = smtp_auth_pwd;
    }

    public String getSmtp_server() {
        return smtp_server;
    }

    public void setSmtp_server(String smtp_server) {
        this.smtp_server = smtp_server;
    }

    public String getSmtp_port() {
        return smtp_port;
    }

    public void setSmtp_port(String smtp_port) {
        this.smtp_port = smtp_port;
    }

    public String getSmtp_auth_user() {
        return smtp_auth_user;
    }

    public void setSmtp_auth_user(String smtp_auth_user) {
        this.smtp_auth_user = smtp_auth_user;
    }

    public String getSmtp_auth_pwd() {
        return smtp_auth_pwd;
    }

    public void setSmtp_auth_pwd(String smtp_auth_pwd) {
        this.smtp_auth_pwd = smtp_auth_pwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingsDto that = (SettingsDto) o;
        return Objects.equals(smtp_server, that.smtp_server) && Objects.equals(smtp_port, that.smtp_port) && Objects.equals(smtp_auth_user, that.smtp_auth_user) && Objects.equals(smtp_auth_pwd, that.smtp_auth_pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(smtp_server, smtp_port, smtp_auth_user, smtp_auth_pwd);
    }
}
