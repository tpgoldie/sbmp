package com.cs.sbmp.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.cs.sbmp.domain.Unit.Kilogram;
import static com.cs.sbmp.domain.Unit.Litre;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QuantityTest {
    @Test
    public void addTwoQuantitiesWithSameUnit_twoQuantities_sumOfTwoQuantitiesCreated() {
        Quantity q1 = new Quantity(new BigDecimal(25), Kilogram);
        Quantity q2 = new Quantity(new BigDecimal(15), Kilogram);

        Quantity actual = q1.add(q2).get();

        assertThat(actual, hasProperty("value", is(new BigDecimal(40))));
        assertThat(actual, hasProperty("unit", is(Kilogram)));
    }

    @Test
    public void addTwoQuantitiesWithDifferentUnit_twoQuantities_sumOfTwoQuantitiesCreated() {
        Quantity q1 = new Quantity(new BigDecimal(25), Litre);
        Quantity q2 = new Quantity(new BigDecimal(15), Kilogram);

        Optional<Quantity> actual = q1.add(q2);

        assertThat(actual, is(Optional.empty()));
    }
}
