package com.cs.sbmp.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.cs.sbmp.domain.Currency.GBP;
import static com.cs.sbmp.domain.OrderType.SELL;
import static com.cs.sbmp.domain.Unit.Kilogram;
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

        assertThat(actual, hasProperty("userId", is("user-1")));
        assertThat(actual, hasProperty("quantity", is(new Quantity(new BigDecimal(3.5), Kilogram))));
        assertThat(actual, hasProperty("price", is(new Money(GBP, new BigDecimal(303)))));
        assertThat(actual, hasProperty("orderType", is(SELL)));
    }
}
