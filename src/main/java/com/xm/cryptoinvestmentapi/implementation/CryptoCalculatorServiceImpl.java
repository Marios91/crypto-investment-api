package com.xm.cryptoinvestmentapi.implementation;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.enumeration.CryptocurrencyType;
import com.xm.cryptoinvestmentapi.service.CryptoCalculatorService;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public List<Cryptocurrency> cryptocurrenciesNormalizedRange(List<Cryptocurrency> cryptocurrencies) {

        List<Cryptocurrency> cryptocurrenciesNormalizedRange = Arrays.stream(Arrays.stream(CryptocurrencyType.class.getEnumConstants())
                .map(Enum::name).toArray(String[]::new))
                .map(cryptocurrencyName -> getCryptocurrencyNormalizedRange(cryptocurrencyName, cryptocurrencies))
                .sorted(Comparator.comparingDouble(Cryptocurrency::getPrice).reversed())
                .collect(Collectors.toList());

        return cryptocurrenciesNormalizedRange;

    }

    @Override
    public Cryptocurrency findOldestRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol) {
        return cryptocurrencies.stream()
                .filter(cryptocurrency -> cryptocurrencySymbol.equals(cryptocurrency.getSymbol()))
                .min(Comparator.comparing(Cryptocurrency::getLocalDateTime))
                .orElseThrow(() -> new NoSuchElementException("requested crypto not found!"));
    }

    @Override
    public Cryptocurrency findNewestRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol) {
        return cryptocurrencies.stream()
                .filter(cryptocurrency -> cryptocurrencySymbol.equals(cryptocurrency.getSymbol()))
                .max(Comparator.comparing(Cryptocurrency::getLocalDateTime))
                .orElseThrow(() -> new NoSuchElementException("requested crypto not found!"));
    }

    @Override
    public Cryptocurrency findMinRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol) {
        return cryptocurrencies.stream()
                .filter(cryptocurrency -> cryptocurrencySymbol.equals(cryptocurrency.getSymbol()))
                .min(Comparator.comparingDouble(Cryptocurrency::getPrice))
                .orElseThrow(() -> new NoSuchElementException("requested crypto not found!"));
    }

    @Override
    public Cryptocurrency findMaxRequested(List<Cryptocurrency> cryptocurrencies, String cryptocurrencySymbol) {
        return cryptocurrencies.stream()
                .filter(cryptocurrency -> cryptocurrencySymbol.equals(cryptocurrency.getSymbol()))
                .max(Comparator.comparingDouble(Cryptocurrency::getPrice))
                .orElseThrow(() -> new NoSuchElementException("requested crypto not found!"));
    }

    private Cryptocurrency getCryptocurrencyNormalizedRange(String cryptocurrencyName, List<Cryptocurrency> cryptocurrencies) {

        Cryptocurrency cryptocurrencyMinPrice = cryptocurrencies.stream()
                .filter(cryptocurrency -> cryptocurrencyName.equals(cryptocurrency.getSymbol()))
                .min(Comparator.comparingDouble(Cryptocurrency::getPrice))
                .orElseThrow(() -> new NoSuchElementException("no crypto found!"));

        Cryptocurrency cryptocurrencyMaxPrice = cryptocurrencies.stream()
                .filter(cryptocurrency -> cryptocurrencyName.equals(cryptocurrency.getSymbol()))
                .max(Comparator.comparingDouble(Cryptocurrency::getPrice))
                .orElseThrow(() -> new NoSuchElementException("no crypto found!"));

        double normalizedRange = (cryptocurrencyMaxPrice.getPrice() - cryptocurrencyMinPrice.getPrice() ) / cryptocurrencyMinPrice.getPrice();

        return new Cryptocurrency(cryptocurrencyName, normalizedRange);

    }

}

