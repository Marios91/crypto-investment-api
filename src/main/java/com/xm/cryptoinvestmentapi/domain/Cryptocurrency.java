package com.xm.cryptoinvestmentapi.domain;

import java.time.LocalDateTime;

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

    public Cryptocurrency(double normalizedRange) {
        this.normalizedRange = normalizedRange;
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
}
