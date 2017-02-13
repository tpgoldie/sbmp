package com.cs.sbmp.domain;

import java.math.BigDecimal;

import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;

public class CreateOrder {
    public static OrderBuilder sell() {
        return new OrderBuilder().orderType(SELL);
    }

    public static OrderBuilder buy() {
        return new OrderBuilder().orderType(BUY);
    }

    static class OrderBuilder {
        private String userId = "";
        private Quantity quantity;
        private Money price;
        private OrderType orderType;

        public OrderBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public OrderBuilder quantity(BigDecimal amount, Unit unit) {
            this.quantity = new Quantity(amount, unit);
            return this;
        }

        public OrderBuilder price(Currency currency, BigDecimal amount) {
            this.price = new Money(currency, amount);
            return this;
        }

        private OrderBuilder orderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public Order createOrder() {
            if (orderType == SELL) {
                return new SellOrder(userId, quantity, price);
            }

            return new BuyOrder(userId, quantity, price);
        }
    }
}
