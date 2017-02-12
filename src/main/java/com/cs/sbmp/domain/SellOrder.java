package com.cs.sbmp.domain;

import static com.cs.sbmp.domain.OrderType.SELL;

public class SellOrder extends Order {
    public SellOrder(String userId, Quantity quantity, Money price) {
        super(userId, quantity, price);
    }

    @Override
    public OrderType getOrderType() {
        return SELL;
    }
}
