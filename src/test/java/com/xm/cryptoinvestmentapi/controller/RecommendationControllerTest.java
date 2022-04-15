package com.xm.cryptoinvestmentapi.controller;

import com.xm.cryptoinvestmentapi.domain.Cryptocurrency;
import com.xm.cryptoinvestmentapi.service.CsvFileRecordsReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CsvFileRecordsReaderService csvFileRecordsReaderServiceMock;

    @Test
    void recommendationShouldReturnDataFromCsvFileService() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        Cryptocurrency cryptocurrencyA = new Cryptocurrency(currentTime, "ABC", 12345.0);
        Cryptocurrency cryptocurrencyB = new Cryptocurrency(currentTime, "EFG", 67891.0);
        when(csvFileRecordsReaderServiceMock.getFileRecords()).thenReturn(Arrays.asList(cryptocurrencyA, cryptocurrencyB));

        this.mockMvc.perform(get("/cryptos/data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].localDateTime").value(currentTime.toString()))
                .andExpect(jsonPath("$[0].symbol").value("ABC"))
                .andExpect(jsonPath("$[0].price").value(12345.0))
                .andExpect(jsonPath("$[1].localDateTime").value(currentTime.toString()))
                .andExpect(jsonPath("$[1].symbol").value("EFG"))
                .andExpect(jsonPath("$[1].price").value(67891.0));

        verify(csvFileRecordsReaderServiceMock, times(1)).getFileRecords();
        verifyNoMoreInteractions(csvFileRecordsReaderServiceMock);
    }

}