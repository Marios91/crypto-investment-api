package com.xm.cryptoinvestmentapi.implementation;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CryptoCalculatorService;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CryptoCalculatorServiceImpl implements CryptoCalculatorService {

    private final CsvFileRecordsReaderService csvFileRecordsReaderService;

    public CryptoCalculatorServiceImpl(CsvFileRecordsReaderService csvFileRecordsReaderService) {
        this.csvFileRecordsReaderService = csvFileRecordsReaderService;
    }

    @Override
    public Cryptocurrency findOldest(List<Cryptocurrency> cryptocurrencies) {
        return cryptocurrencies.stream()
                .min(Comparator.comparing(Cryptocurrency::getLocalDateTime))
                .orElseThrow(() -> new NoSuchElementException("no crypto found!"));
    }

    @Override
    public Cryptocurrency findNewest(List<Cryptocurrency> cryptocurrencies) {
        return cryptocurrencies.stream()
                .max(Comparator.comparing(Cryptocurrency::getLocalDateTime))
                .orElseThrow(() -> new NoSuchElementException("no crypto found!"));
    }

    @Override
    public Cryptocurrency findMinPrice(List<Cryptocurrency> cryptocurrencies) {
        return cryptocurrencies.stream()
                .min(Comparator.comparingDouble(Cryptocurrency::getPrice))
                .orElseThrow(() -> new NoSuchElementException("no crypto found!"));
    }

    public Cryptocurrency findMaxPrice(List<Cryptocurrency> cryptocurrencies) {

        return cryptocurrencies.stream()
                .max(Comparator.comparingDouble(Cryptocurrency::getPrice))
                .orElseThrow(() -> new NoSuchElementException("no crypto found!"));
    }

}

