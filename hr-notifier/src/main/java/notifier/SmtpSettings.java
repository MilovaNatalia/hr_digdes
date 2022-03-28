package notifier;

public class SmtpSettings implements Settings{
    private String smtp_server;
    private String smtp_port;
    private String smtp_auth_user;
    private String smtp_auth_pwd;
    //TODO add field for JavaMail smtp server


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
}
