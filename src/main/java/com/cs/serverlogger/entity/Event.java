package com.cs.serverlogger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "host")
    private String host;

    @Column(name = "start_ts")
    private String startTimeStamp;

    @Column(name = "end_ts")
    private String endTimeStamp;

    @Column(name = "duration")
    private int duration;

    @Column(name = "alert")
    private boolean alert;

    public Event() {

    }

    public Event(String id, String eventType, String host, String startTimeStamp, String endTimeStamp, int duration,
            boolean alert) {
        super();
        this.id = id;
        this.duration = duration;
        this.eventType = eventType;
        this.host = host;
        this.alert = alert;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
    }

    public Event(String id, String eventType, String host) {
        super();
        this.id = id;
        this.eventType = eventType;
        this.host = host;
        this.duration = -1;
        this.alert = false;
        this.startTimeStamp = null;
        this.endTimeStamp = null;
    }

    public Event(String id) {
        super();
        this.id = id;
        this.eventType = null;
        this.host = null;
        this.duration = -1;
        this.alert = false;
        this.startTimeStamp = null;
        this.endTimeStamp = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }
}
