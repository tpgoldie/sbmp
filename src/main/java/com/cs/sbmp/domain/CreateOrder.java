package com.cs.sbmp.domain;

import java.math.BigDecimal;
import java.util.Optional;

import static com.cs.sbmp.domain.OrderType.BUY;
import static com.cs.sbmp.domain.OrderType.SELL;
import static com.cs.sbmp.domain.Unit.findBySymbol;

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

        public OrderBuilder quantitySpec(String value) {
            String[] tokens = value.split(" ");

            if (tokens.length != 2) { throw new RuntimeException("Incorrect data entered for quantity"); }

            Optional<Unit> unit = findBySymbol(tokens[1].trim());

            if (!unit.isPresent()) { throw new RuntimeException("Incorrect data entered for quantity"); }

            return quantity(new BigDecimal(tokens[0].trim()), unit.get());
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
