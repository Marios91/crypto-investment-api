package com.xm.cryptoinvestmentapi.service;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;

import java.util.List;

public interface CryptoCalculatorService {

    Cryptocurrency findOldest(List<Cryptocurrency> cryptocurrencies);

    Cryptocurrency findNewest(List<Cryptocurrency> cryptocurrencies);

    Cryptocurrency findMinPrice(List<Cryptocurrency> cryptocurrencies);

    Cryptocurrency findMaxPrice(List<Cryptocurrency> cryptocurrencies);

}
