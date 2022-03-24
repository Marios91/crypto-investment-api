package com.xm.cryptoinvestmentapi.service;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;

import java.util.List;

public interface CryptoCalculatorService {

    Cryptocurrency findOldest(List<Cryptocurrency> cryptocurrencies);

    Cryptocurrency findNewest(List<Cryptocurrency> cryptocurrencies);

    Cryptocurrency findMinPrice(List<Cryptocurrency> cryptocurrencies);

    Cryptocurrency findMaxPrice(List<Cryptocurrency> cryptocurrencies);

    List<Cryptocurrency> cryptocurrenciesNormalizedRange(List<Cryptocurrency> cryptocurrencies);

    Cryptocurrency findOldestRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol);

    Cryptocurrency findNewestRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol);

    Cryptocurrency findMinRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol);

    Cryptocurrency findMaxRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol);

}
