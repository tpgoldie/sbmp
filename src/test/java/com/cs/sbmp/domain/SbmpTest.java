package com.cs.sbmp.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

abstract class SbmpTest {
    List<Order> filterByOrderType(List<Order> orders, OrderType orderType) {
        return orders.stream().filter(o -> o.getOrderType().equals(orderType))
                .collect(Collectors.toList());
    }

    void assertQuantity(Order order, int value, Unit unit) {
        assertThat(order, hasProperty("quantity", is(new Quantity(new BigDecimal(value), unit))));
    }

    void assertPrice(Order order, Currency currency, int value) {
        assertThat(order, hasProperty("price", is(new Money(currency, new BigDecimal(value)))));
    }

    void assertOrderType(Order order, OrderType orderType) {
        assertThat(order, hasProperty("orderType", is(orderType)));
    }
}
