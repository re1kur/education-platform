package re1kur.authenticationservice.mq.publisher;


public interface EventPublisher {
    void publishUserRegistrationEvent(String email);

}
