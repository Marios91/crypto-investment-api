package com.xm.cryptoinvestmentapi.domain;

import java.time.LocalDateTime;

public class Cryptocurrency {

    private LocalDateTime localDateTime;
    private String symbol;
    private double price;

    public Cryptocurrency(LocalDateTime localDateTime, String symbol, double price) {
        this.localDateTime = localDateTime;
        this.symbol = symbol;
        this.price = price;
    }

    public Cryptocurrency(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public Cryptocurrency() {
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
