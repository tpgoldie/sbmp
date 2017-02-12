package com.cs.sbmp.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.cs.sbmp.domain.Currency.GBP;
import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;
import static com.cs.sbmp.domain.Unit.Kilogram;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LiveOrderBoardTest {
    private LiveOrderBoard liveOrderBoard = new LiveOrderBoard();

    @Test
    public void registerASellOrder_sellOrder_liveOrderBoardUpdatedWithSellOrder() {
        Order order = CreateOrder
                        .sell()
                        .userId("user-1")
                        .quantity(new BigDecimal(3.5), Kilogram)
                        .price(GBP, new BigDecimal(303))
                        .createOrder();

        liveOrderBoard.register(order);

        List<Order> actualList = liveOrderBoard.liveOrders();

        assertThat(actualList.size(), is(1));

        SellOrder actual = (SellOrder) actualList.get(0);

        assertOrder(actual, SELL);
    }

    @Test
    public void registerABuyOrder_buyOrder_liveOrderBoardUpdatedWithBuyOrder() {
        Order order = CreateOrder
                        .buy()
                        .userId("user-1")
                        .quantity(new BigDecimal(3.5), Kilogram)
                        .price(GBP, new BigDecimal(303))
                        .createOrder();

        liveOrderBoard.register(order);

        List<Order> actualList = liveOrderBoard.liveOrders();

        assertThat(actualList.size(), is(1));

        BuyOrder actual = (BuyOrder) actualList.get(0);

        assertOrder(actual, BUY);
    }

    private void assertOrder(Order actual, OrderType orderType) {
        assertThat(actual, hasProperty("userId", is("user-1")));
        assertThat(actual, hasProperty("quantity", is(new Quantity(new BigDecimal(3.5), Kilogram))));
        assertThat(actual, hasProperty("price", is(new Money(GBP, new BigDecimal(303)))));
        assertThat(actual, hasProperty("orderType", is(orderType)));
    }

    @Test
    public void cancelAnOrder_orderToCancel_orderREmovedFromLiveOrderBoard() {
        Order order1 = CreateOrder
                .buy()
                .userId("user-1")
                .quantity(new BigDecimal(3.5), Kilogram)
                .price(GBP, new BigDecimal(303))
                .createOrder();

        Order order2 = CreateOrder
                .sell()
                .userId("user-2")
                .quantity(new BigDecimal(5.5), Kilogram)
                .price(GBP, new BigDecimal(313))
                .createOrder();

        asList(order1, order2).stream().forEach(o -> liveOrderBoard.register(o));

        List<Order> liveOrders = liveOrderBoard.liveOrders();

        assertThat(liveOrders.size(), is(2));

        assertThat(filterByOrderType(liveOrders, BUY).size(), is(1));
        assertThat(filterByOrderType(liveOrders, SELL).size(), is(1));

        liveOrderBoard.cancelOrder(order2);

        List<Order> actualList = liveOrderBoard.liveOrders();

        assertThat(actualList.size(), is(1));

        assertThat(filterByOrderType(liveOrders, BUY).size(), is(1));
        assertThat(filterByOrderType(liveOrders, SELL), is(empty()));
    }

    private List<Order> filterByOrderType(List<Order> orders, OrderType orderType) {
        return orders.stream().filter(o -> o.getOrderType().equals(orderType))
                .collect(Collectors.toList());
    }
}
