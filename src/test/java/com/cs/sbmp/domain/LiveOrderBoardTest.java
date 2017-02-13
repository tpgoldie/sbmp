package com.cs.sbmp.domain;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.cs.sbmp.domain.CreateOrder.buy;
import static com.cs.sbmp.domain.CreateOrder.sell;
import static com.cs.sbmp.domain.Currency.GBP;
import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;
import static com.cs.sbmp.domain.Unit.Kilogram;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LiveOrderBoardTest extends SbmpTest {
    private LiveOrderBoard liveOrderBoard = new LiveOrderBoard();

    @Test
    public void registerASellOrder_sellOrder_liveOrderBoardUpdatedWithSellOrder() {
        Order order = sell()
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
        Order order = buy()
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
        Order order1 = buy()
                        .userId("user-1")
                        .quantity(new BigDecimal(3.5), Kilogram)
                        .price(GBP, new BigDecimal(303))
                        .createOrder();

        Order order2 = sell()
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

    @Test
    public void mergeOrders_ordersToMerge_ordersMergedAccordingToMergeRules() {
        Order order1 = buy()
                .userId("user-1")
                .price(GBP, new BigDecimal(205))
                .quantity(new BigDecimal(25000), Kilogram)
                .createOrder();

        Order order2 = sell()
                .userId("user-2")
                .price(GBP, new BigDecimal(215))
                .quantity(new BigDecimal(25500), Kilogram)
                .createOrder();

        Order order3 = buy()
                .userId("user-3")
                .price(GBP, new BigDecimal(205))
                .quantity(new BigDecimal(15000), Kilogram)
                .createOrder();

        Order order4 = sell()
                .userId("user-4")
                .price(GBP, new BigDecimal(215))
                .quantity(new BigDecimal(15500), Kilogram)
                .createOrder();

        Order order5 = buy()
                .userId("user-5")
                .price(GBP, new BigDecimal(195))
                .quantity(new BigDecimal(17500), Kilogram)
                .createOrder();

        Order order6 = sell()
                .userId("user-6")
                .price(GBP, new BigDecimal(220))
                .quantity(new BigDecimal(18500), Kilogram)
                .createOrder();

        asList(order1, order2, order3, order4, order5, order6).forEach(o -> liveOrderBoard.register(o));

        List<Order> actualList = liveOrderBoard.liveOrders();
        assertThat(actualList.size(), is(4));

        List<Order> actualBuyOrders = filterByOrderType(actualList, BUY);
        assertThat(actualBuyOrders.size(), is(2));

        BuyOrder expectedBuyOrder = (BuyOrder) buy()
                .price(GBP, new BigDecimal(205))
                .quantity(new BigDecimal(40000), Kilogram)
                .createOrder();

        assertThat(actualList, hasItems(expectedBuyOrder, order5));
        assertThat(actualBuyOrders, contains(expectedBuyOrder, order5));

        List<Order> actualSellOrders = filterByOrderType(actualList, SELL);
        assertThat(actualSellOrders.size(), is(2));

        SellOrder expectedSellOrder = (SellOrder) sell()
                .price(GBP, new BigDecimal(215))
                .quantity(new BigDecimal(41000), Kilogram)
                .createOrder();

        assertThat(actualList, hasItems(expectedSellOrder, order6));
        assertThat(actualSellOrders, contains(expectedSellOrder, order6));

        assertThat(actualList, contains(expectedBuyOrder, order5, expectedSellOrder, order6));
    }

    @Test
    public void examplesTest() {
        Order order1 = sell()
                .userId("user1")
                .quantitySpec("3.5 kg")
                .price(GBP, new BigDecimal(306))
                .createOrder();

        Order order2 = sell()
                .userId("user2")
                .quantitySpec("1.2 kg")
                .price(GBP, new BigDecimal(310))
                .createOrder();

        Order order3 = sell()
                .userId("user3")
                .quantitySpec("1.5 kg")
                .price(GBP, new BigDecimal(307))
                .createOrder();

        Order order4 = sell()
                .userId("user4")
                .quantitySpec("2.0 kg")
                .price(GBP, new BigDecimal(306))
                .createOrder();

        asList(order1, order2, order3, order4).forEach(o -> liveOrderBoard.register(o));

        List<Order> actualList = liveOrderBoard.liveOrders();

        assertThat(actualList.size(), is(3));

        Order order5 = sell()
                .quantitySpec("5.5 kg")
                .price(GBP, new BigDecimal(306))
                .createOrder();

        assertThat(actualList, contains(order5, order3, order2));
    }
}
