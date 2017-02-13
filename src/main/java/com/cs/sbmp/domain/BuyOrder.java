package com.cs.sbmp.domain;

import java.util.Optional;

import static com.cs.sbmp.domain.CreateOrder.buy;
import static com.cs.sbmp.domain.OrderType.BUY;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class BuyOrder extends Order {
    BuyOrder(String userId, Quantity quantity, Money price) {
        super(userId, BUY, quantity, price);
    }

    @Override
    public Optional<Order> add(Order order) {
        if (!order.getPrice().equals(getPrice())) { return empty(); }

        if (!(order instanceof BuyOrder)) { return empty(); }

        BuyOrder that = (BuyOrder) order;

        Optional<Quantity> sum = getQuantity().add(that.getQuantity());
        if (!sum.isPresent()) { return empty(); }

        Money price = getPrice();

        return of(buy()
                .quantity(sum.get().getValue(), sum.get().getUnit())
                .price(price.getCurrency(), price.getAmount())
                .createOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof BuyOrder)) { return false; }

        return super.equals(o);
    }
}
