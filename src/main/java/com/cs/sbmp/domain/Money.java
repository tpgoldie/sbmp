package com.cs.sbmp.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;

public final class Money implements Comparable<Money> {
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

        return new EqualsBuilder()
                .append(that.getCurrency(), this.getCurrency())
                .append(that.getAmount(), this.getAmount())
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = getCurrency().hashCode();
        result = 31 * result + getAmount().hashCode();
        return result;
    }

    @Override
    public String toString() { return String.format("%s%.2f", currency.getSymbol(), amount); }

    @Override
    public int compareTo(Money that) {
        if (!that.getCurrency().equals(getCurrency())) { return that.getCurrency().compareTo(getCurrency()); }

        return that.getAmount().compareTo(getAmount());
    }
}
