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

        public OrderBuilder quantitySpec(String spec) {
            String[] tokens = spec.split(" ");

            if (tokens.length != 2) { throw new RuntimeException("Incorrect spec entered for quantity"); }

            Optional<Unit> unit = findBySymbol(tokens[1].trim());

            if (!unit.isPresent()) { throw new RuntimeException("Incorrect spec entered for quantity"); }

            return quantity(new BigDecimal(tokens[0].trim()), unit.get());
        }

        public OrderBuilder quantity(BigDecimal amount, Unit unit) {
            this.quantity = new Quantity(amount, unit);
            return this;
        }

        public OrderBuilder priceSpec(String spec) {
            String key = spec.trim();

            if (key.length() < 2) { throw new RuntimeException("Incorrect spec entered for price"); }

            String[] tokens = { spec.substring(0, 1), spec.substring(1) };

            Optional<Currency> currency = Currency.findBySymbol(tokens[0]);
            if (!currency.isPresent()) { throw new RuntimeException("Incorrect spec entered for price"); }

            return price(currency.get(), new BigDecimal(tokens[1].trim()));
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
