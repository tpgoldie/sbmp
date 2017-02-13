package com.cs.sbmp.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public final class SellOrdersMerger extends OrdersMerger {
    public SellOrdersMerger(List<Order> orders) {
        super(orders, SELL);
    }

    @Override
    protected void sortOrders(List<Order> mergedOrders) {
        mergedOrders.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
    }
}
