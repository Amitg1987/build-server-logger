package com.cs.serverlogger.parser;

import static org.mockito.Matchers.any;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cs.serverlogger.processor.EventProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
public class InputFileParserTest {

    @Autowired
    private InputFileParser inputFileParser;

    @MockBean
    private EventProcessor eventProcessor;

    @Before
    public void setUp() throws Exception {
        Thread thread = BDDMockito.mock(Thread.class);
        PowerMockito.whenNew(Thread.class).withAnyArguments().thenReturn(thread);
        Mockito.doNothing().when(thread).start();
        Mockito.doNothing().when(eventProcessor).put(any());
    }

    @Test
    public void testReadAndProcessStream() throws Exception {
        inputFileParser.readAndProcessStream("input.txt");
    }

    @Test(expected = IOException.class)
    public void testReadAndProcessStreamForException() throws IOException, InterruptedException {
        inputFileParser.readAndProcessStream("input33.txt");
    }

    @TestConfiguration
    static class InputFileParserTestContextConfiguration {

        @Bean
        public InputFileParser inputFileParser() {
            return new InputFileParser();
        }
    }

}
