package com.example.catalogueservice.controller;

import com.example.catalogueservice.service.OrderService;
import com.example.dto.OrderDto;
import com.example.dto.PageDto;
import com.example.filter.OrderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<?> readAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @ModelAttribute @Nullable OrderFilter filter
    ) {
        PageDto<OrderDto> body = orderService.readAll(page, size, filter);
        return ResponseEntity.ok(body);
    }

//    @GetMapping("/products")
//    public ResponseEntity<?> readAll(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @ModelAttribute @Nullable OrderFilter filter
//    ) {
//        PageDto<OrderDto> body = orderService.readAll(page, size, filter);
//        return ResponseEntity.ok(body);
//    }
}
