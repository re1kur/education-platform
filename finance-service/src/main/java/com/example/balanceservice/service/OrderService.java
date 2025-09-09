package com.example.balanceservice.service;

import payload.OrderPayload;

public interface OrderService {
    void createOrder(OrderPayload payload);
}
