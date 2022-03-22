package com.xm.cryptoinvestmentapi.controller;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class RecommendationController {

    private final CsvFileRecordsReaderService csvFileRecordsReaderService;

    public RecommendationController(CsvFileRecordsReaderService csvFileRecordsReaderService) {
        this.csvFileRecordsReaderService = csvFileRecordsReaderService;
    }

    @GetMapping("/data")
    private List<Cryptocurrency> getCsvRecords() {
        return csvFileRecordsReaderService.getRecords();
    }


}
