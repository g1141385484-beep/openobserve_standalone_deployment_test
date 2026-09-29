package com.example.paymentservice.controller;

import com.example.paymentservice.client.OrderServiceClient;
import com.example.paymentservice.model.PaymentRequest;
import com.example.paymentservice.model.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final OrderServiceClient orderServiceClient;

    public PaymentController(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    /**
     * Process a payment for an order.
     *
     * This endpoint creates a cross-service trace spanning:
     *   payment-service → order-service → user-service
     *
     * All hops are automatically captured by the OpenTelemetry Java Agent
     * and sent to OpenObserve as a single distributed trace.
     */
    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        logger.info("Processing payment for orderId={} userId={} amount={}",
                request.getOrderId(), request.getUserId(), request.getAmount());

        // Step 1: Verify order exists (calls order-service, which may call user-service)
        if (!orderServiceClient.orderExists(request.getOrderId())) {
            logger.warn("Payment rejected: order {} not found", request.getOrderId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new PaymentResponse(null, request.getUserId(), request.getOrderId(),
                            request.getAmount(), request.getCurrency(),
                            "FAILED", "Order not found", LocalDateTime.now())
            );
        }

        // Step 2: Simulate payment gateway processing
        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        logger.info("Payment gateway approved. transactionId={}", transactionId);

        // Step 3: Confirm order as paid (calls order-service to update status)
        orderServiceClient.confirmOrder(request.getOrderId());

        PaymentResponse response = new PaymentResponse(
                transactionId,
                request.getUserId(),
                request.getOrderId(),
                request.getAmount(),
                request.getCurrency() != null ? request.getCurrency() : "INR",
                "SUCCESS",
                "Payment processed successfully",
                LocalDateTime.now()
        );

        logger.info("Payment completed. transactionId={} status=SUCCESS", transactionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("payment-service is running");
    }
}
