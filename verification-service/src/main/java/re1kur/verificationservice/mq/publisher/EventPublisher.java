package re1kur.verificationservice.mq.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import re1kur.verificationservice.entity.Code;

public interface EventPublisher {
    void publishGenerationCodeEvent(Code code) throws JsonProcessingException;

    void publishUserVerificationEvent(String email);
}
