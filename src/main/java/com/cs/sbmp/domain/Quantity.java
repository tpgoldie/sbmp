package com.cs.sbmp.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class Quantity {
    private final BigDecimal value;
    private final Unit unit;

    public Quantity(BigDecimal value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    public Optional<Quantity> add(Quantity that) {
        if (!getUnit().equals(that.getUnit())) { return empty(); }

        return of(new Quantity(getValue().add(that.getValue()), getUnit()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity)) return false;

        Quantity that = (Quantity) o;

        return new EqualsBuilder()
                .append(that.getValue(), this.getValue())
                .append(that.getUnit(), this.getUnit())
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = getValue().hashCode();
        result = 31 * result + getUnit().hashCode();
        return result;
    }

    @Override
    public String toString() { return String.format("%.2f %s", value, unit.getSymbol()); }
}
