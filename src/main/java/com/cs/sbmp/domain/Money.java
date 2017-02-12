package com.cs.sbmp.domain;

import java.math.BigDecimal;

public final class Money {
    private final Currency currency;
    private final BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;

        Money that = (Money) o;

        if (!getCurrency().equals(that.getCurrency())) return false;
        return getAmount().equals(that.getAmount());

    }

    @Override
    public int hashCode() {
        int result = getCurrency().hashCode();
        result = 31 * result + getAmount().hashCode();
        return result;
    }

    @Override
    public String toString() { return String.format("%.2f %s", amount, currency.getSymbol()); }
}
