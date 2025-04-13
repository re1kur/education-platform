package re1kur.authenticationservice.mq.publisher;


import re1kur.authenticationservice.entity.User;

public interface EventPublisher {
    void publishUserRegistrationEvent(User user);

}
