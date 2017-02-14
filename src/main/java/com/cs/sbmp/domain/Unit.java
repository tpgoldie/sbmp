package com.cs.sbmp.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public final class Unit {
    public static final Unit Kilogram = new Unit("Kilogram", "kg");

    public static final Unit Litre = new Unit("Litre", "l");

    static final List<Unit> units = asList(Kilogram, Litre);

    public static Optional<Unit> findBySymbol(String symbol) {
        String key = symbol.trim().toLowerCase();
        return units.stream().filter(u -> u.getSymbol().equals(key)).findAny();
    }

    private final String name;

    private final String symbol;

    Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;

        Unit that = (Unit) o;

        return new EqualsBuilder()
                .append(that.getName(), this.getName())
                .append(that.getSymbol(), this.getSymbol())
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getSymbol().hashCode();
        return result;
    }

    @Override
    public String toString() { return getSymbol(); }
}
