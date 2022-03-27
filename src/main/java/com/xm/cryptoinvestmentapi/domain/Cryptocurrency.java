package com.xm.cryptoinvestmentapi.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Cryptocurrency {

    private LocalDateTime localDateTime;
    private String symbol;
    private double price;
    private double normalizedRange;

    public Cryptocurrency(LocalDateTime localDateTime, String symbol, double price) {
        this.localDateTime = localDateTime;
        this.symbol = symbol;
        this.price = price;
    }

    public Cryptocurrency(String symbol, double normalizedRange) {
        this.symbol = symbol;
        this.normalizedRange = normalizedRange;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

    public double getNormalizedRange() {
        return normalizedRange;
    }

    @Override
    public String toString() {
        return "Cryptocurrency{" +
                "localDateTime=" + localDateTime +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", normalizedRange=" + normalizedRange +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cryptocurrency that = (Cryptocurrency) o;
        return Double.compare(that.price, price) == 0
                && Double.compare(that.normalizedRange, normalizedRange) == 0
                && Objects.equals(localDateTime, that.localDateTime)
                && Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDateTime, symbol, price, normalizedRange);
    }
}
