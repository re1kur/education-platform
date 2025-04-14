package re1kur.orderservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.orderservice.mapper.EventMapper;

@Component
@RequiredArgsConstructor
public class DefaultEventMapper implements EventMapper {
    private final ObjectMapper serializer;

    @SneakyThrows
    @Override
    public String message(OrderCreatedEvent event) {
        return serializer.writeValueAsString(event);
    }
}
