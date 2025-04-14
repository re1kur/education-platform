package re1kur.orderservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import payload.OrderPayload;
import lombok.RequiredArgsConstructor;
import re1kur.orderservice.service.OrderService;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("create")
    public void createOrder(@RequestParam Integer goodsId, @AuthenticationPrincipal Jwt jwt) {
        service.createOrder(new OrderPayload(jwt.getSubject(), goodsId));
    }
}
