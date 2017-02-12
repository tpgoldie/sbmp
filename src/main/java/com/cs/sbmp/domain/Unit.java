package com.cs.sbmp.domain;

public enum Unit {
    Kilogram("kg");

    private final String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
