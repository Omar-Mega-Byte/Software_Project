package com.example.registration_service.model;

import java.time.LocalDateTime;

public class Event {
    private Long id;
    private String title;
    private LocalDateTime dateTime;
    private Integer availableSeats;
    private boolean isCanceled;

    public Event() {
        this.isCanceled = false;
    }

    public Event(Long id, String title, LocalDateTime dateTime, Integer availableSeats, boolean isCanceled) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.availableSeats = availableSeats;
        this.isCanceled = isCanceled;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public boolean getIsCanceled() {
        return isCanceled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setIsCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public boolean isEventAvailable() {
        return !isCanceled &&
                availableSeats > 0 &&
                dateTime.isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateTime=" + dateTime +
                ", availableSeats=" + availableSeats +
                ", isCanceled=" + isCanceled +
                '}';
    }
}