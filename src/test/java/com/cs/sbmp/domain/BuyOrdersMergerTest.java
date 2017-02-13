package com.cs.sbmp.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.cs.sbmp.domain.CreateOrder.buy;
import static com.cs.sbmp.domain.CreateOrder.sell;
import static com.cs.sbmp.domain.Currency.GBP;
import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;
import static com.cs.sbmp.domain.Unit.Kilogram;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BuyOrdersMergerTest extends SbmpTest {
    @Test
    public void mergeOrdersByPrice_ordersToMerge_buyOrdersMergedByPrice() {
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

        Order order3 = buy()
                .userId("user-3")
                .quantity(new BigDecimal(1.5), Kilogram)
                .price(GBP, new BigDecimal(303))
                .createOrder();

        Order order4 = sell()
                .userId("user-4")
                .quantity(new BigDecimal(5.5), Kilogram)
                .price(GBP, new BigDecimal(323))
                .createOrder();

        Order order5 = buy()
                .userId("user-5")
                .quantity(new BigDecimal(0.5), Kilogram)
                .price(GBP, new BigDecimal(203))
                .createOrder();

        BuyOrdersMerger ordersMerger = new BuyOrdersMerger(asList(order1, order2, order3, order4, order5));

        List<Order> actualList = ordersMerger.merge();

        assertThat(actualList.size(), is(2));

        List<Order> actualSellOrders = filterByOrderType(actualList, SELL);
        assertThat(actualSellOrders, is(empty()));

        Order expectedBuyOrder = buy()
                .quantity(new BigDecimal("5.0"), Kilogram)
                .price(GBP, new BigDecimal(303))
                .createOrder();

        assertThat(actualList, contains(expectedBuyOrder, order5));
    }
}
