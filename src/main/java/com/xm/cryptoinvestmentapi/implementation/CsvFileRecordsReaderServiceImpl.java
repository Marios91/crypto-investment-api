package com.xm.cryptoinvestmentapi.implementation;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("classpath:csv/prices/*")
    private Resource[] resources;

    @Override
    public List<Cryptocurrency> getRecords() {

        List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
        for (Resource resource : resources) {
            try {
                List<String> allCsvFileLines = Files.readAllLines(resource.getFile().toPath());
                cryptocurrencies.addAll(allCsvFileLines.stream()
                        .skip(1)
                        .filter(line -> !line.isEmpty())
                        .map(line -> line.split(","))
                        .map(splitLine -> new Cryptocurrency(
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(splitLine[0])), ZoneId.systemDefault()), splitLine[1], Double.parseDouble(splitLine[2]))
                        )
                        .collect(Collectors.toList()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cryptocurrencies;
    }

}

