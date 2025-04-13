package re1kur.authenticationservice.mq.event;

import java.io.Serializable;

public record UserRegistrationEvent(String id, String email) implements Serializable {
}
