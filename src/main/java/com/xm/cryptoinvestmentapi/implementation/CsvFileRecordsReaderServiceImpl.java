package com.xm.cryptoinvestmentapi.implementation;

import com.xm.cryptoinvestmentapi.constants.CryptocurrencyConstants;
import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvFileRecordsReaderServiceImpl implements CsvFileRecordsReaderService {

    private final List<Cryptocurrency> cryptocurrencies = new ArrayList<>();

    @Value("classpath:csv/prices/*")
    private final Resource[] resources;

    public CsvFileRecordsReaderServiceImpl(Resource[] resources) {
        this.resources = resources;
    }

    @PostConstruct
    public void init() throws IOException {
        readCsvFileRecords();
    }

    public void readCsvFileRecords() throws IOException {

        for (Resource resource : resources) {
            cryptocurrencies.addAll(Files.lines(resource.getFile().toPath())
                    .skip(1)
                    .map(CsvFileRecordsReaderServiceImpl::getCryptocurrencyValues)
                    .collect(Collectors.toList()));
        }
    }

    private static Cryptocurrency getCryptocurrencyValues(String line) {
        String[] fields = line.split(CryptocurrencyConstants.COMMA_SEPARATOR);
        if (fields.length != 3) {
            throw new RuntimeException("Invalid CSV line - " + line + CryptocurrencyConstants.DOT + " Application is automatically terminated.");
        }
        return new Cryptocurrency(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(fields[0])), ZoneId.systemDefault()), fields[1], Double.parseDouble(fields[2]));

    }

    @Override
    public List<Cryptocurrency> getFileRecords() {
        return cryptocurrencies;
    }
}

