package com.cs.sbmp.domain;

import java.util.ArrayList;
import java.util.List;

public class LiveOrderBoard {
    private List<Order> liveOrders = new ArrayList<>();

    public void register(Order order) {
        liveOrders.add(order);
    }

    public List<Order> liveOrders() {
        return liveOrders;
    }

    public void cancelOrder(Order order) {
        liveOrders.remove(order);
    }
}
