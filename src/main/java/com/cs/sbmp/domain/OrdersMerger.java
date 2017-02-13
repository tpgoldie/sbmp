package com.cs.sbmp.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public abstract class OrdersMerger {
    final OrderType orderType;
    final List<Order> orders;

    OrdersMerger(List<Order> orders, OrderType orderType) {
        this.orderType = orderType;
        this.orders = unmodifiableList(orders);
    }

    public List<Order> merge() {
        List<Order> mergedOrders = mergeOrders();

        sortOrders(mergedOrders);

        return mergedOrders;
    }

    private List<Order> mergeOrders() {
        if (orders.isEmpty()) { return emptyList(); }

        List<Order> filteredOrders = orders.stream().filter(o -> o.getOrderType().equals(orderType)).collect(toList());

        Map<Money, List<Order>> groupedByPrice = filteredOrders.stream().collect(groupingBy(Order::getPrice));

        List<Optional<Order>> aggregatedOptionalOrders = groupedByPrice.values().stream().map(this::mergeOrders).collect(toList());

        List<Order> aggregatedOrders = aggregatedOptionalOrders.stream().filter(oo -> oo.isPresent()).map(oo -> oo.get()).collect(toList());

        return aggregatedOrders;
    }

    private Optional<Order> mergeOrders(List<Order> orders) {
        if (orders.isEmpty()) { return empty(); }
        if (orders.size() == 1) { return of(orders.get(0)); }

        Optional<Order> zero = empty();

        return orders.stream().map(Optional::of).reduce(zero, Order::add);
    }

    protected abstract void sortOrders(List<Order> mergedOrders);
}
