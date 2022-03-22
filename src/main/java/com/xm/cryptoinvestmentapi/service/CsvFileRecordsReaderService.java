package com.xm.cryptoinvestmentapi.service;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;

import java.util.List;

public interface CsvFileRecordsReaderService {

    List<Cryptocurrency> getRecords();
}
