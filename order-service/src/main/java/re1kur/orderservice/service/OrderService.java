package re1kur.orderservice.service;

import payload.OrderPayload;

public interface OrderService {
    void createOrder(OrderPayload payload);
}
