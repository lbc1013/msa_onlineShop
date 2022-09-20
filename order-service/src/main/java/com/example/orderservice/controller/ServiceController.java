package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.OrderResponse;
import com.example.orderservice.vo.RequestOrder;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
public class ServiceController {
    Environment env;
    OrderService orderService;

    @Autowired
    public ServiceController(Environment env, OrderService orderService) {
        this.env = env;
        this.orderService = orderService;
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createdOrder = orderService.createOrder(orderDto);

        OrderResponse orderResponse = mapper.map(createdOrder, OrderResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);

        List<OrderResponse> response = new ArrayList<>();
        orders.forEach(order -> {
                response.add(new ModelMapper().map(order, OrderResponse.class));
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
