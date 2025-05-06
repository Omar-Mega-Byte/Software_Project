package com.example.registration_service.dto;

import com.example.registration_service.model.Event;
import com.example.registration_service.model.Participant;

import java.util.List;

public class ParticipantWithEventsDTO {
    private Participant participant;
    private List<Event> events;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}