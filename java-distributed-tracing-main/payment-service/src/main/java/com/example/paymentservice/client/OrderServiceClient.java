package com.example.paymentservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OrderServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceClient.class);

    private final RestTemplate restTemplate;

    @Value("${services.order-service.url:http://localhost:8082}")
    private String orderServiceUrl;

    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean orderExists(Long orderId) {
        try {
            logger.info("Verifying order {} via order-service", orderId);
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    orderServiceUrl + "/api/orders/" + orderId,
                    Map.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            logger.error("Failed to reach order-service for orderId {}: {}", orderId, e.getMessage());
            return false;
        }
    }

    public void confirmOrder(Long orderId) {
        try {
            logger.info("Confirming order {} via order-service", orderId);
            restTemplate.patchForObject(
                    orderServiceUrl + "/api/orders/" + orderId + "/status",
                    Map.of("status", "CONFIRMED"),
                    Map.class
            );
            logger.info("Order {} confirmed", orderId);
        } catch (Exception e) {
            logger.error("Failed to confirm order {}: {}", orderId, e.getMessage());
        }
    }
}
