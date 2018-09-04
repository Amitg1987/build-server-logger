package com.cs.serverlogger.processor;

import static org.mockito.Matchers.any;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cs.serverlogger.dto.EventDTO;
import com.cs.serverlogger.entity.Event;
import com.cs.serverlogger.repository.EventRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class EventProcessorTest {

    @Autowired
    private EventProcessor eventProcessor;

    @MockBean
    private EventRepository eventRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void testEventProcessorQueuePutMethod() throws InterruptedException {
        eventProcessor.put(new EventDTO());
        Assert.assertEquals(eventProcessor.getQueue().size(), 1);
    }

    @Test
    public void testEventProcessorProcessEventMethodStartEventAlreadyPresent() throws InterruptedException {
        Event event = new Event("abc", "PQR", "localhost", "12345", null, -1, false);
        EventDTO e = new EventDTO("abc", "FINISHED", "PQR", "localhost", "12348");
        Mockito.when(eventRepository.findOne(any(String.class))).thenReturn(event);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventProcessor.processEvent(e);
    }

    @Test
    public void testEventProcessorProcessEventMethodEndEventAlreadyPresent() throws InterruptedException {
        Event event = new Event("abc", "PQR", "localhost", null, "12348", -1, false);
        EventDTO e = new EventDTO("abc", "STARTED", "PQR", "localhost", "12345");
        Mockito.when(eventRepository.findOne(any(String.class))).thenReturn(event);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventProcessor.processEvent(e);
    }

    @Test
    public void testEventProcessorProcessEventMethodEventNotPresentStartEventArrived() throws InterruptedException {
        Event event = new Event("abc", "PQR", "localhost", null, "12348", -1, false);
        EventDTO e = new EventDTO("abc", "STARTED", "PQR", "localhost", "12345");
        Mockito.when(eventRepository.findOne(any(String.class))).thenReturn(null);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventProcessor.processEvent(e);
    }

    @Test
    public void testEventProcessorProcessEventMethodEventNotPresentEndEventArrived() throws InterruptedException {
        Event event = new Event("abc", "PQR", "localhost", null, "12348", -1, false);
        EventDTO e = new EventDTO("abc", "FINISHED", "PQR", "localhost", "12348");
        Mockito.when(eventRepository.findOne(any(String.class))).thenReturn(null);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventProcessor.processEvent(e);
    }

    @Test
    public void testEventProcessorProcessEventMethodStartEventAlreadyPresentWithNullTimeStamp()
            throws InterruptedException {
        Event event = new Event("abc", "PQR", "localhost", null, null, -1, false);
        EventDTO e = new EventDTO("abc", "FINISHED", "PQR", "localhost", "12348");
        Mockito.when(eventRepository.findOne(any(String.class))).thenReturn(event);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventProcessor.processEvent(e);
    }

    @Test
    public void testEventProcessorProcessEventMethodEndEventAlreadyPresentWithNullTimeStamp()
            throws InterruptedException {
        Event event = new Event("abc", "PQR", "localhost", null, null, -1, false);
        EventDTO e = new EventDTO("abc", "STARTED", "PQR", "localhost", "12345");
        Mockito.when(eventRepository.findOne(any(String.class))).thenReturn(event);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventProcessor.processEvent(e);
    }

    @TestConfiguration
    static class EventProcessorTestContextConfiguration {

        @Bean
        public EventProcessor eventProcessor() {
            return new EventProcessor();
        }

        @Bean(name = "queue")
        public BlockingQueue<EventDTO> eventQueue() {
            return new LinkedBlockingQueue<>();
        }
    }
}
