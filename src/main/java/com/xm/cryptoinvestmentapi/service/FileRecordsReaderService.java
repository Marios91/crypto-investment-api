package com.xm.cryptoinvestmentapi.service;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;

import java.util.List;

public interface FileRecordsReaderService {

    List<Cryptocurrency> getFileRecords();
}
