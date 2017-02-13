package com.cs.sbmp.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;

public final class Currency implements Comparable<Currency> {
    public static Currency GBP = new Currency("Pound Sterling", "GBP", "£");
    private final String description;
    private final String isoCode;
    private final String symbol;

    private Currency(String description, String isoCode, String symbol) {
        this.description = description;
        this.isoCode = isoCode;
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        Currency that = (Currency) o;

        return new EqualsBuilder()
                .append(that.getIsoCode(), this.getIsoCode())
                .append(that.getSymbol(), this.getSymbol())
                .append(that.getDescription(), this.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + (getIsoCode() != null ? getIsoCode().hashCode() : 0);
        result = 31 * result + (getSymbol() != null ? getSymbol().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() { return isoCode; }

    @Override
    public int compareTo(Currency that) {
        return this.getIsoCode().compareTo(that.getIsoCode());
    }
}
