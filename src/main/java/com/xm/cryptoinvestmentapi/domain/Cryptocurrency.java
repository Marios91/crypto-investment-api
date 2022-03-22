package com.xm.cryptoinvestmentapi.domain;

public class Cryptocurrency {

    private final String timestamp;
    private final String symbol;
    private final double price;

    public Cryptocurrency(String timestamp, String symbol, Double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
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
                "timestamp=" + timestamp +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
