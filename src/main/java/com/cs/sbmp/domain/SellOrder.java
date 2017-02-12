package com.cs.sbmp.domain;

import static com.cs.sbmp.domain.OrderType.SELL;

public class SellOrder extends Order {
    SellOrder(String userId, Quantity quantity, Money price) {
        super(userId, SELL, quantity, price);
    }
}
