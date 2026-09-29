package com.example.paymentservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {
    private String transactionId;
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String message;
    private LocalDateTime processedAt;

    public PaymentResponse() {}

    public PaymentResponse(String transactionId, Long userId, Long orderId, BigDecimal amount,
                           String currency, String status, String message, LocalDateTime processedAt) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.message = message;
        this.processedAt = processedAt;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}