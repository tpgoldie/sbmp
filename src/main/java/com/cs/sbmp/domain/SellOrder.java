package com.cs.sbmp.domain;

import java.util.Optional;

import static com.cs.sbmp.domain.CreateOrder.sell;
import static com.cs.sbmp.domain.OrderType.SELL;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class SellOrder extends Order {
    SellOrder(String userId, Quantity quantity, Money price) {
        super(userId, SELL, quantity, price);
    }

    @Override
    public Optional<Order> add(Order order) {
        if (!order.getPrice().equals(getPrice())) { return empty(); }

        if (!(order instanceof SellOrder)) { return empty(); }
        SellOrder that = (SellOrder) order;

        Optional<Quantity> sum = getQuantity().add(that.getQuantity());
        if (!sum.isPresent()) { return empty(); }

        Money price = getPrice();

        return of(sell()
                .quantity(sum.get().getValue(), sum.get().getUnit())
                .price(price.getCurrency(), price.getAmount())
                .createOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof SellOrder)) { return false; }

        return super.equals(o);
    }
}
