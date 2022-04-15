package com.xm.cryptoinvestmentapi.implementation;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoCalculatorServiceImplTest {

    @Mock
    private CsvFileRecordsReaderService csvFileRecordsReaderServiceMock;

    @InjectMocks
    private CryptoCalculatorServiceImpl cryptoCalculatorService;

    @Test
    void shouldReturnTheOldestCrypto() {
        Cryptocurrency cryptocurrencyA = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyB = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "EFG", 67891.0);
        Cryptocurrency cryptocurrencyC = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "HFL", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA, cryptocurrencyB, cryptocurrencyC));

        assertEquals(cryptocurrencyC, cryptoCalculatorService.findOldest(csvFileRecordsReaderServiceMock.getFileRecords()));

    }

    @Test
    void shouldReturnTheNewestCrypto() {
        Cryptocurrency cryptocurrencyA = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyB = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "EFG", 67891.0);
        Cryptocurrency cryptocurrencyC = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "HFL", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA, cryptocurrencyB, cryptocurrencyC));

        assertEquals(cryptocurrencyA, cryptoCalculatorService.findNewest(csvFileRecordsReaderServiceMock.getFileRecords()));
    }

    @Test
    void shouldReturnCryptoWithMinPrice() {
        Cryptocurrency cryptocurrencyA = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyB = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "EFG", 67891.0);
        Cryptocurrency cryptocurrencyC = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "HFL", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA, cryptocurrencyB, cryptocurrencyC));

        assertEquals(cryptocurrencyA, cryptoCalculatorService.findMinPrice(csvFileRecordsReaderServiceMock.getFileRecords()));
    }

    @Test
    void shouldReturnCryptoWithMaxPrice() {
        Cryptocurrency cryptocurrencyA = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyB = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "EFG", 67891.0);
        Cryptocurrency cryptocurrencyC = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "HFL", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA, cryptocurrencyB, cryptocurrencyC));

        assertEquals(cryptocurrencyC, cryptoCalculatorService.findMaxPrice(csvFileRecordsReaderServiceMock.getFileRecords()));
    }

    @Test
    void shouldReturnCryptosWithNormalizedRangeDescendingOrder() {
        Cryptocurrency cryptocurrencyA1 = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "BTC", 12345.0);
        Cryptocurrency cryptocurrencyA2 = new Cryptocurrency(LocalDateTime.of(2022, Month.JANUARY, 15, 11, 50, 25), "BTC", 23456.0);
        Cryptocurrency cryptocurrencyA3 = new Cryptocurrency(LocalDateTime.of(2022, Month.FEBRUARY, 18, 13, 33, 14), "BTC", 3456.0);

        Cryptocurrency cryptocurrencyB1 = new Cryptocurrency(LocalDateTime.of(2022, Month.SEPTEMBER, 22, 16, 40, 55), "ETH", 2345.0);
        Cryptocurrency cryptocurrencyB2 = new Cryptocurrency(LocalDateTime.of(2022, Month.MARCH, 15, 11, 50, 25), "ETH", 4567.0);
        Cryptocurrency cryptocurrencyB3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "ETH", 678.0);

        Cryptocurrency cryptocurrencyC1 = new Cryptocurrency(LocalDateTime.of(2022, Month.FEBRUARY, 22, 16, 40, 55), "LTC", 6789.0);
        Cryptocurrency cryptocurrencyC2 = new Cryptocurrency(LocalDateTime.of(2022, Month.MAY, 15, 11, 50, 25), "LTC", 1356.0);
        Cryptocurrency cryptocurrencyC3 = new Cryptocurrency(LocalDateTime.of(2022, Month.OCTOBER, 18, 13, 33, 14), "LTC", 2468.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA1, cryptocurrencyA2, cryptocurrencyA3,
                cryptocurrencyB1, cryptocurrencyB2,cryptocurrencyB3, cryptocurrencyC1, cryptocurrencyC2, cryptocurrencyC3));

        double cryptoANormalizedRange = (cryptocurrencyA2.getPrice() - cryptocurrencyA3.getPrice()) / cryptocurrencyA3.getPrice();
        double cryptoBNormalizedRange = (cryptocurrencyB2.getPrice() - cryptocurrencyB3.getPrice()) / cryptocurrencyB3.getPrice();
        double cryptoCNormalizedRange = (cryptocurrencyC1.getPrice() - cryptocurrencyC2.getPrice()) / cryptocurrencyC2.getPrice();

        Cryptocurrency cryptocurrencyA = new Cryptocurrency(cryptocurrencyA1.getSymbol(), cryptoANormalizedRange);
        Cryptocurrency cryptocurrencyB = new Cryptocurrency(cryptocurrencyB1.getSymbol(), cryptoBNormalizedRange);
        Cryptocurrency cryptocurrencyC = new Cryptocurrency(cryptocurrencyC1.getSymbol(), cryptoCNormalizedRange);

        assertEquals(Arrays.asList(cryptocurrencyA, cryptocurrencyB, cryptocurrencyC), cryptoCalculatorService.cryptocurrenciesNormalizedRange(csvFileRecordsReaderServiceMock.getFileRecords()));

    }

    @Test
    void shouldReturnTheOldestCryptoRequested() {
        Cryptocurrency cryptocurrencyA1 = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyA2 = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "ABC", 67891.0);
        Cryptocurrency cryptocurrencyA3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "ABC", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA1, cryptocurrencyA2, cryptocurrencyA3));

        assertEquals(cryptocurrencyA3, cryptoCalculatorService.findOldestRequested(csvFileRecordsReaderServiceMock.getFileRecords(), cryptocurrencyA1.getSymbol()));
    }

    @Test
    void shouldReturnTheNewestCryptoRequested() {
        Cryptocurrency cryptocurrencyA1 = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyA2 = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "ABC", 67891.0);
        Cryptocurrency cryptocurrencyA3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "ABC", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA1, cryptocurrencyA2, cryptocurrencyA3));

        assertEquals(cryptocurrencyA1, cryptoCalculatorService.findNewestRequested(csvFileRecordsReaderServiceMock.getFileRecords(), cryptocurrencyA1.getSymbol()));
    }

    @Test
    void shouldReturnMinCryptoRequested() {
        Cryptocurrency cryptocurrencyA1 = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyA2 = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "ABC", 67891.0);
        Cryptocurrency cryptocurrencyA3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "ABC", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA1, cryptocurrencyA2, cryptocurrencyA3));

        assertEquals(cryptocurrencyA1, cryptoCalculatorService.findMinRequested(csvFileRecordsReaderServiceMock.getFileRecords(), cryptocurrencyA1.getSymbol()));
    }

    @Test
    void shouldReturnMaxCryptoRequested() {
        Cryptocurrency cryptocurrencyA1 = new Cryptocurrency(LocalDateTime.of(2022, Month.JULY, 22, 16, 40, 55), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyA2 = new Cryptocurrency(LocalDateTime.of(2022, Month.JUNE, 12, 16, 30, 45), "ABC", 67891.0);
        Cryptocurrency cryptocurrencyA3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 5, 14, 20, 28), "ABC", 89132.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA1, cryptocurrencyA2, cryptocurrencyA3));

        assertEquals(cryptocurrencyA3, cryptoCalculatorService.findMaxRequested(csvFileRecordsReaderServiceMock.getFileRecords(), cryptocurrencyA1.getSymbol()));
    }

    @Test
    void shouldReturnCryptoWithMaxNormalizedRangeOnDayRequested() {

        Cryptocurrency cryptocurrencyA1 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "ABC", 12345.0);
        Cryptocurrency cryptocurrencyA2 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "ABC", 23456.0);
        Cryptocurrency cryptocurrencyA3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "ABC", 3456.0);

        Cryptocurrency cryptocurrencyB1 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "EFG", 2345.0);
        Cryptocurrency cryptocurrencyB2 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "EFG", 4567.0);
        Cryptocurrency cryptocurrencyB3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "EFG", 678.0);

        Cryptocurrency cryptocurrencyC1 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "HFL", 6789.0);
        Cryptocurrency cryptocurrencyC2 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "HFL", 1356.0);
        Cryptocurrency cryptocurrencyC3 = new Cryptocurrency(LocalDateTime.of(2022, Month.APRIL, 18, 13, 33, 14), "HFL", 2468.0);

        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA1, cryptocurrencyA2, cryptocurrencyA3,
                cryptocurrencyB1, cryptocurrencyB2,cryptocurrencyB3, cryptocurrencyC1, cryptocurrencyC2, cryptocurrencyC3));

        double cryptoANormalizedRange = (cryptocurrencyA2.getPrice() - cryptocurrencyA3.getPrice()) / cryptocurrencyA3.getPrice();
        Cryptocurrency cryptocurrencyA4 = new Cryptocurrency("ABC", cryptoANormalizedRange);

        assertEquals(cryptocurrencyA4, cryptoCalculatorService.findMaxNormalizedRangeDayRequested(csvFileRecordsReaderServiceMock.getFileRecords(), cryptocurrencyA1.getLocalDateTime().toLocalDate()));
    }
}