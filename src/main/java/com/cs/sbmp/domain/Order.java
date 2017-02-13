package com.cs.sbmp.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Optional;

import static java.util.Optional.empty;

public abstract class Order {
    static Optional<Order> add(Optional<Order> order1, Optional<Order> order2) {
        if (order1.isPresent() && order2.isPresent()) { return order1.get().add(order2.get()); }

        if (order1.isPresent() && !order2.isPresent()) { return order1; }

        if (!order1.isPresent()) { return order2; }

        return empty();
    }

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

    public abstract Optional<Order> add(Order order);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order that = (Order) o;

        return new EqualsBuilder()
                .append(that.getUserId(), this.getUserId())
                .append(that.getOrderType(), this.getOrderType())
                .append(that.getQuantity(), this.getQuantity())
                .append(that.getPrice(), this.getPrice())
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + getOrderType().hashCode();
        result = 31 * result + getQuantity().hashCode();
        result = 31 * result + getPrice().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s @ %s)", userId.length() == 0 ? "-" : userId, orderType, quantity, price);
    }
}
