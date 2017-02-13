package com.cs.sbmp.domain;

import java.util.List;

import static com.cs.sbmp.domain.OrderType.BUY;

public final class BuyOrdersMerger extends OrdersMerger {

    BuyOrdersMerger(List<Order> orders) {
        super(orders, BUY);
    }

    @Override
    protected void sortOrders(List<Order> mergedOrders) {
        mergedOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
    }
}
