package com.cs.sbmp.domain;

import java.math.BigDecimal;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quantity that = (Quantity) o;

        if (!getValue().equals(that.getValue())) return false;
        return getUnit() == that.getUnit();

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
