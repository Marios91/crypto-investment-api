package com.xm.cryptoinvestmentapi.implementation;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.enumeration.CryptocurrencyType;
import com.xm.cryptoinvestmentapi.service.CryptoCalculatorService;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

    @Override
    public Cryptocurrency findMaxPrice(List<Cryptocurrency> cryptocurrencies) {

        return cryptocurrencies.stream()
                .max(Comparator.comparingDouble(Cryptocurrency::getPrice))
                .orElseThrow(() -> new NoSuchElementException("no crypto found!"));
    }

    @Override
    public List<Cryptocurrency> cryptocurrenciesNormalizedRange(List<Cryptocurrency> cryptocurrencies) {

        List<Cryptocurrency> cryptocurrenciesNormalizedRange = Arrays.stream(Arrays.stream(CryptocurrencyType.class.getEnumConstants())
                .map(Enum::name).toArray(String[]::new))
                .map(cryptocurrencyName -> getCryptocurrencyNormalizedRangeByCryptoName(cryptocurrencyName, cryptocurrencies))
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

    @Override
    public Cryptocurrency findMaxNormalizedRangeDayRequested(List<Cryptocurrency> cryptocurrencies, LocalDate startDate) {

        Map<String, List<Cryptocurrency>> cryptocurrencyListMap = cryptocurrencies.stream()
        .collect(Collectors.groupingBy(Cryptocurrency::getSymbol));

        Map<String, List<Cryptocurrency>> cryptocurrencyListFilteredByDateMap = cryptocurrencyListMap.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().stream()
                        .filter(cryptocurrency -> startDate.isEqual(cryptocurrency.getLocalDateTime().toLocalDate()))
                        .collect(Collectors.toList())));

        Map<String, Cryptocurrency> cryptocurrencySymbolNormalizedRangeMap = cryptocurrencyListFilteredByDateMap.entrySet().stream()
                        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> getCryptocurrencyNormalizedRange(entry.getValue(), entry.getKey())));

        return cryptocurrencySymbolNormalizedRangeMap.values().stream()
                .max(Comparator.comparingDouble(Cryptocurrency::getNormalizedRange))
                        .orElseThrow(() -> new NoSuchElementException("no crypto found!"));
    }

    private Cryptocurrency getCryptocurrencyNormalizedRangeByCryptoName(String cryptocurrencyName, List<Cryptocurrency> cryptocurrencies) {
        List<Cryptocurrency> cryptocurrenciesBySymbol = cryptocurrencies.stream()
                .filter(cryptocurrency -> cryptocurrencyName.equals(cryptocurrency.getSymbol()))
                .collect(Collectors.toList());

        return getCryptocurrencyNormalizedRange(cryptocurrenciesBySymbol, cryptocurrencyName);
    }

    private Cryptocurrency getCryptocurrencyNormalizedRange(List<Cryptocurrency> cryptocurrencies, String symbol) {
        Cryptocurrency cryptocurrencyMinPrice = findMinPrice(cryptocurrencies);
        Cryptocurrency cryptocurrencyMaxPrice = findMaxPrice(cryptocurrencies);

        double normalizedRange = getNormalizedRange(cryptocurrencyMaxPrice, cryptocurrencyMinPrice);

        return new Cryptocurrency(symbol, normalizedRange);
    }

    private double getNormalizedRange(Cryptocurrency cryptocurrencyMaxPrice, Cryptocurrency cryptocurrencyMinPrice) {
        return (cryptocurrencyMaxPrice.getPrice() - cryptocurrencyMinPrice.getPrice()) / cryptocurrencyMinPrice.getPrice();
    }
}

