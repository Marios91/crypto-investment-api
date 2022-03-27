package com.xm.cryptoinvestmentapi.controller;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CryptoCalculatorService;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class RecommendationController {

    private final CsvFileRecordsReaderService csvFileRecordsReaderService;
    private final CryptoCalculatorService cryptoCalculatorService;

    public RecommendationController(CsvFileRecordsReaderService csvFileRecordsReaderService, CryptoCalculatorService cryptoCalculatorService) {
        this.csvFileRecordsReaderService = csvFileRecordsReaderService;
        this.cryptoCalculatorService = cryptoCalculatorService;
    }

    @GetMapping("/data")
    private List<Cryptocurrency> getCsvRecords() {
        return csvFileRecordsReaderService.getRecords();
    }

    @GetMapping("/oldest")
    private Cryptocurrency getOldestCrypto() {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findOldest(cryptocurrencies);
    }

    @GetMapping("/newest")
    private Cryptocurrency getNewestCrypto() {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findNewest(cryptocurrencies);
    }

    @GetMapping("/min")
    private Cryptocurrency getMinCrypto() {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findMinPrice(cryptocurrencies);
    }


    @GetMapping("/max")
    private Cryptocurrency getMaxCrypto() {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findMaxPrice(cryptocurrencies);
    }

    @GetMapping("/normalized-range")
    private List<Cryptocurrency> getNormalizedRangeCrypto() {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.cryptocurrenciesNormalizedRange(cryptocurrencies);
    }

    @GetMapping("/oldest-requested")
    private Cryptocurrency getRequestedOldestCrypto(@RequestParam final String cryptocurrencySymbol) {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findOldestRequested(cryptocurrencies, cryptocurrencySymbol);
    }

    @GetMapping("/newest-requested")
    private Cryptocurrency getRequestedNewestCrypto(@RequestParam final String cryptocurrencySymbol) {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findNewestRequested(cryptocurrencies, cryptocurrencySymbol);
    }

    @GetMapping("/min-requested")
    private Cryptocurrency getRequestedMinCrypto(@RequestParam final String cryptocurrencySymbol) {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findMinRequested(cryptocurrencies, cryptocurrencySymbol);
    }

    @GetMapping("/max-requested")
    private Cryptocurrency getRequestedMaxCrypto(@RequestParam final String cryptocurrencySymbol) {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findMaxRequested(cryptocurrencies, cryptocurrencySymbol);
    }

    @GetMapping("/max-normalized-range-of-day-requested")
    private Cryptocurrency getRequestedDayMaxNormalizedRangeCrypto(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate) {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.findMaxNormalizedRangeDayRequested(cryptocurrencies, startDate);
    }

}
