package com.cs.serverlogger.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.serverlogger.dto.EventDTO;
import com.cs.serverlogger.processor.EventProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class InputFileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputFileParser.class);

    @Autowired
    private EventProcessor eventProcessor;

    public void readAndProcessStream(String fileName) {
        try {
            FileInputStream inStream = new FileInputStream(fileName);
            Scanner scanner = new Scanner(inStream, "UTF-8");

            new Thread(eventProcessor).start();

            String inputEventObject;
            Gson gson = new GsonBuilder().create();

            while (scanner.hasNextLine()) {
                inputEventObject = scanner.nextLine();
                EventDTO event = gson.fromJson(inputEventObject, EventDTO.class);
                eventProcessor.put(event);
            }
            scanner.close();
        } catch (IOException ex) {
            LOGGER.error("Error while reading file");
        } catch (InterruptedException e) {
            LOGGER.error("Error while adding event to Queue");
        }
    }
}
