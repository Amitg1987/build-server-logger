package com.cs.serverlogger.processor;

import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.serverlogger.dto.EventDTO;
import com.cs.serverlogger.entity.Event;
import com.cs.serverlogger.repository.EventRepository;

@Component
public class EventProcessor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProcessor.class);

    @Resource(name = "queue")
    private BlockingQueue<EventDTO> queue;

    @Autowired
    private EventRepository eventRepository;

    public void put(EventDTO event) throws InterruptedException {
        this.queue.put(event);
    }

    @Override
    public void run() {
        EventDTO e;
        while (true) {
            try {
                e = queue.take();
                if (e != null) {
                    processEvent(e);
                }
            } catch (InterruptedException e1) {
                LOGGER.error("Error while processsing event from Queue");
            }

        }
    }

    private void processEvent(EventDTO e) {
        Event event = eventRepository.findOne(e.getId());

        if (event == null) {
            processNewEvent(e);
        } else {
            processExistingEvent(e, event);
        }
    }

    private void processNewEvent(EventDTO e) {
        Event event;
        if ("STARTED".equals(e.getState())) {
            event = new Event(e.getId(), e.getType(), e.getHost(), e.getTimestamp(), null, -1, false);
            eventRepository.save(event);
        }

        if ("FINISHED".equals(e.getState())) {
            event = new Event(e.getId(), e.getType(), e.getHost(), null, e.getTimestamp(), -1, false);
            eventRepository.save(event);
        }
    }

    private void processExistingEvent(EventDTO e, Event event) {
        boolean alert = false;
        int duration = -1;
        Event updatedEvent;

        if (event.getStartTimeStamp() != null && "FINISHED".equals(e.getState())) {
            duration = (int)(Long.parseLong(e.getTimestamp()) - Long.parseLong(event.getStartTimeStamp()));
            alert = (duration > 4) ? true : false;
            updatedEvent = new Event(event.getId(), event.getEventType(), event.getHost(), event.getStartTimeStamp(),
                    e.getTimestamp(), duration, alert);
            eventRepository.save(updatedEvent);
        }

        if (event.getEndTimeStamp() != null && "STARTED".equals(e.getState())) {
            duration = (int)(Long.parseLong(event.getEndTimeStamp()) - Long.parseLong(e.getTimestamp()));
            alert = (duration > 4) ? true : false;
            updatedEvent = new Event(event.getId(), event.getEventType(), event.getHost(), e.getTimestamp(),
                    event.getEndTimeStamp(), duration, alert);
            eventRepository.save(updatedEvent);
        }
    }

}
