package com.cs.sbmp.domain;

public enum Unit {
    Kilogram("kg"), Litre("l");

    private final String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
