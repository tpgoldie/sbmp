package com.cs.sbmp.domain;

public abstract class Order {
    private final String userId;
    private final OrderType orderType;
    private final Quantity quantity;
    private final Money price;

    protected Order(String userId, OrderType orderType, Quantity quantity, Money price) {
        this.userId = userId;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderType getOrderType() { return orderType; }

    public String getUserId() {
        return userId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }
}
