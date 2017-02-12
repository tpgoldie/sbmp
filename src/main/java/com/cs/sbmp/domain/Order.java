package com.cs.sbmp.domain;

public abstract class Order {
    private final String userId;
    private final Quantity quantity;
    private final Money price;

    protected Order(String userId, Quantity quantity, Money price) {
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
    }

    public abstract OrderType getOrderType();

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
