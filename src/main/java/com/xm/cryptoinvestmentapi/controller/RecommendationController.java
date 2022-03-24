package com.xm.cryptoinvestmentapi.controller;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CryptoCalculatorService;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private List<Cryptocurrency> getBtcCryptos() {
        List<Cryptocurrency> cryptocurrencies = csvFileRecordsReaderService.getRecords();
        return cryptoCalculatorService.cryptocurrenciesNormalizedRange(cryptocurrencies);
    }

}
