package re1kur.verificationservice.mq.publisher;

import re1kur.verificationservice.entity.Code;

public interface EventPublisher {
    void publishGenerationCodeEvent(Code code);

    void publishUserVerificationEvent(String email);
}
