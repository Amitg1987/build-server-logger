package com.cs.serverlogger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.serverlogger.parser.InputFileParser;

/**
 * Class: LogProcessingService, This service is entry point for event
 * processing, it delegates request to process file to InputFileParser class.
 * 
 * @author Amit Kumar Gupta
 *
 */
@Service
public class LogProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogProcessingService.class);

    @Autowired
    private InputFileParser inputFileParser;

    public void process(String fileName) {

        LOGGER.debug("Forwarding process request to file parser");

        inputFileParser.readAndProcessStream(fileName);
    }
}
