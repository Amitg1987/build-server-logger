package com.cs.serverlogger.service;

import static org.mockito.Matchers.any;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cs.serverlogger.parser.InputFileParser;

@RunWith(SpringJUnit4ClassRunner.class)
public class LogProcessingServiceTest {

    @Autowired
    private LogProcessingService logProcessingService;

    @MockBean
    private InputFileParser inputFileParser;

    @Before
    public void setUp() throws IOException, InterruptedException {
        Mockito.doNothing().when(inputFileParser).readAndProcessStream(any());
    }

    @Test
    public void testReadAndProcessStream() throws IOException, InterruptedException {
        logProcessingService.process("input.txt");
    }

    @TestConfiguration
    static class LogProcessingServiceTestContextConfiguration {

        @Bean
        public LogProcessingService logProcessingService() {
            return new LogProcessingService();
        }
    }

}
