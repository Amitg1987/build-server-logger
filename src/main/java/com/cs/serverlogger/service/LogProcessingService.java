package com.cs.serverlogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.serverlogger.parser.InputFileParser;

@Service
public class LogProcessingService {

    @Autowired
    private InputFileParser inputFileParser;

    public void process(String fileName) {
        inputFileParser.readAndProcessStream(fileName);
    }
}
