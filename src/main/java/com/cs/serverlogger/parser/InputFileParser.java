package com.cs.serverlogger.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.serverlogger.dto.EventDTO;
import com.cs.serverlogger.processor.EventProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class: InputFileParser, implements function to read and process input stream.
 * 
 * @author Amit Kumar Gupta
 *
 */
@Component
public class InputFileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputFileParser.class);

    @Autowired
    private EventProcessor eventProcessor;

    /**
     * Function takes filename to process as input, responsible for scanning/reading
     * serialized event objects(JSON) from file, covert them to Java Object and
     * finally, pushing event to EventProcessor.
     * 
     * @param fileName
     * @throws IOException 
     * @throws InterruptedException 
     */
    public void readAndProcessStream(String fileName) throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {

            // Starting consumer thread, responsible for event processing
            new Thread(eventProcessor).start();

            String inputEventObject;
            Gson gson = new GsonBuilder().create();

            while ((inputEventObject = br.readLine()) != null) {
                EventDTO event = gson.fromJson(inputEventObject, EventDTO.class);

                // Putting event into Queue for processing
                eventProcessor.put(event);
                LOGGER.debug("Event having id:{}, state:{}, timeStamp:{} pushed to Queue", event.getId(),
                        event.getState(), event.getTimestamp());
            }
        } catch (IOException ex) {
            LOGGER.error("Error while reading file", ex);
            throw ex;
        } catch (InterruptedException ex) {
            LOGGER.error("Error while adding event to Queue", ex);
            throw ex;
        }
    }
}
