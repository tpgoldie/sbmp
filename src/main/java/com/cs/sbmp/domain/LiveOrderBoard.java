package com.cs.sbmp.domain;

import java.util.ArrayList;
import java.util.List;

public class LiveOrderBoard {
    private List<Order> orders = new ArrayList<>();

    public void register(Order order) {
        orders.add(order);
    }

    public List<Order> liveOrders() {
        return orders;
    }
}
