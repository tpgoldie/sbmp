package com.cs.sbmp.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.cs.sbmp.domain.CreateOrder.buy;
import static com.cs.sbmp.domain.CreateOrder.sell;
import static com.cs.sbmp.domain.Currency.GBP;
import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;
import static com.cs.sbmp.domain.Unit.Kilogram;
import static com.cs.sbmp.domain.Unit.Litre;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SellOrderTest extends SbmpTest {
    @Test
    public void addTwoSellOrdersWithSamePrice_twoSellOrders_newCompositeSellOrderCreated() {
        SellOrder order1 = (SellOrder) sell()
                .quantitySpec("10 kg")
                .price(GBP, new BigDecimal(245))
                .createOrder();

        SellOrder order2 = (SellOrder) sell()
                .quantitySpec("15 kg")
                .price(GBP, new BigDecimal(245))
                .createOrder();

        SellOrder actual = (SellOrder) order1.add(order2).get();

        asList(actual, order1, order2).forEach(o -> assertPrice(o, GBP, 245));

        assertQuantity(actual, 25, Kilogram);

        assertQuantity(order1, 10, Kilogram);

        assertQuantity(order2, 15, Kilogram);
    }

    @Test
    public void handleAddingTwoSellOrdersWithDifferentPrices_twoSellOrdersWithDifferentPrices_emptyReturned() {
        SellOrder order1 = (SellOrder) sell()
                .quantitySpec("10 kg")
                .price(GBP, new BigDecimal(245))
                .createOrder();

        SellOrder order2 = (SellOrder) sell()
                .quantitySpec("15 kg")
                .price(GBP, new BigDecimal(145))
                .createOrder();

        Optional<Order> actual = order1.add(order2);

        assertThat(actual, is(empty()));

        assertQuantity(order1, 10, Kilogram);
        assertPrice(order1, GBP, 245);

        assertQuantity(order2, 15, Kilogram);
        assertPrice(order2, GBP, 145);
    }

    @Test
    public void handleAddingTwoSellOrdersWithDifferentUnits_twoSellOrdersWithDifferentUnits_emptyReturned() {
        SellOrder order1 = (SellOrder) sell()
                .quantitySpec("10 kg")
                .price(GBP, new BigDecimal(245))
                .createOrder();

        SellOrder order2 = (SellOrder) sell()
                .quantitySpec("15 l")
                .price(GBP, new BigDecimal(245))
                .createOrder();

        Optional<Order> actual = order1.add(order2);

        assertThat(actual, is(empty()));

        asList(order1, order2).forEach(o -> assertPrice(o, GBP, 245));

        assertQuantity(order1, 10, Kilogram);

        assertQuantity(order2, 15, Litre);
    }

    @Test
    public void handleAddingTwoDifferentTypesOfOrders_twoDifferentTypesOfOrders_emptyReturned() {
        BuyOrder order1 = (BuyOrder) buy()
                .quantitySpec("10 kg")
                .price(GBP, new BigDecimal(245))
                .createOrder();

        SellOrder order2 = (SellOrder) sell()
                .quantitySpec("15 kg")
                .price(GBP, new BigDecimal(245))
                .createOrder();

        Optional<Order> actual = order1.add(order2);

        assertThat(actual, is(empty()));

        asList(order1, order2).forEach(o -> assertPrice(o, GBP, 245));

        assertQuantity(order1, 10, Kilogram);
        assertOrderType(order1, BUY);

        assertQuantity(order2, 15, Kilogram);
        assertOrderType(order2, SELL);
    }
}
