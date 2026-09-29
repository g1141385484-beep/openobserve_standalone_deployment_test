package com.example.orderservice.controller;

import com.example.orderservice.client.UserServiceClient;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;

    public OrderController(OrderRepository orderRepository, UserServiceClient userServiceClient) {
        this.orderRepository = orderRepository;
        this.userServiceClient = userServiceClient;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("Fetching all orders");
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        logger.info("Fetching orders for userId: {}", userId);
        return ResponseEntity.ok(orderRepository.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        logger.info("Creating order for userId: {}", order.getUserId());

        // Cross-service call: validate user exists via user-service
        if (!userServiceClient.userExists(order.getUserId())) {
            logger.warn("Order creation rejected: user {} not found", order.getUserId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with id " + order.getUserId() + " does not exist");
        }

        Order savedOrder = orderRepository.save(order);
        logger.info("Order created with id: {}", savedOrder.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        logger.info("Updating status for order {}", id);
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(Order.OrderStatus.valueOf(body.get("status").toUpperCase()));
                    Order updated = orderRepository.save(order);
                    logger.info("Order {} status updated to {}", id, updated.getStatus());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
