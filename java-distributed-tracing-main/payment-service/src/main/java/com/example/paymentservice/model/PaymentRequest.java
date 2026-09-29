package com.example.paymentservice.model;

import java.math.BigDecimal;

public class PaymentRequest {
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;

    public PaymentRequest() {}

    public PaymentRequest(Long userId, Long orderId, BigDecimal amount, String currency, String paymentMethod) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}