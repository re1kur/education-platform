package re1kur.mailservice.service;

public interface MailService {
    void sendGenerationCode(String message);

    void sendWelcomeMail(String message);
}
