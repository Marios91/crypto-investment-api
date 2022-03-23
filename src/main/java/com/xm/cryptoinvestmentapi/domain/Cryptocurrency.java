package com.xm.cryptoinvestmentapi.domain;

import java.time.LocalDateTime;

public class Cryptocurrency {

    private final LocalDateTime localDateTime;
    private final String symbol;
    private final double price;

    public Cryptocurrency(LocalDateTime localDateTime, String symbol, Double price) {
        this.localDateTime = localDateTime;
        this.symbol = symbol;
        this.price = price;
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

    @Override
    public String toString() {
        return "Cryptocurrency{" +
                "timestamp=" + localDateTime +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
