package com.example.registration_service.dto;

import com.example.registration_service.model.Event;
import com.example.registration_service.model.Participant;

import java.util.List;

public class EventWithParticipantsDTO {
    private Event event;
    private List<Participant> participants;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}