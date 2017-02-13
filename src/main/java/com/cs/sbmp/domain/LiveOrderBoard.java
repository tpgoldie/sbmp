package com.cs.sbmp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class LiveOrderBoard {
    private List<Order> liveOrders = new ArrayList<>();

    public void register(Order order) {
        List<Order> ordersSnapshot = new ArrayList<>(liveOrders);
        ordersSnapshot.add(order);

        BuyOrdersMerger buyOrdersMerger = new BuyOrdersMerger(ordersSnapshot);
        SellOrdersMerger sellordersMerger = new SellOrdersMerger(ordersSnapshot);

        List<Order> mergedBuyOrders = buyOrdersMerger.merge();
        List<Order> mergedSellOrders = sellordersMerger.merge();

        synchronized (liveOrders) {
            liveOrders.clear();
            liveOrders.addAll(mergedBuyOrders);
            liveOrders.addAll(mergedSellOrders);
        }
    }

    private List<Order> filterOrdersByOrderType(List<Order> ordersSnapshot, OrderType orderType) {
        List<Order> buyOrders = ordersSnapshot.stream().filter(o -> o.getOrderType().equals(orderType)).collect(toList());
        Map<Money, List<Order>> groupedByPrice = buyOrders.stream().collect(groupingBy(Order::getPrice));
        groupedByPrice.values().stream().forEach(value -> mergeOrders(value));

        return buyOrders;
    }

    private Optional<Order> mergeOrders(List<Order> orders) {
        if (orders.isEmpty()) { return empty(); }
        if (orders.size() == 1) { return of(orders.get(0)); }

        Optional<Order> zero = empty();

        return orders.stream().map(Optional::of).reduce(zero, Order::add);
    }

    public List<Order> liveOrders( ) {
        return unmodifiableList(liveOrders);
    }

    public void cancelOrder(Order order) {
        liveOrders.remove(order);
    }
}
