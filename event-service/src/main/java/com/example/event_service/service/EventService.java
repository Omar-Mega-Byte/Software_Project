package com.example.event_service.service;

import com.example.event_service.model.Event;
import com.example.event_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event createEvent(Event event) {
        event.setAvailableSeats(event.getMaxSeats());
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = getEventById(id);
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setDateTime(eventDetails.getDateTime());
        event.setLocation(eventDetails.getLocation());
        event.setMaxSeats(eventDetails.getMaxSeats());
        return eventRepository.save(event);
    }

    public void cancelEvent(Long id) {
        Event event = getEventById(id);
        event.setIsCanceled(true);
        eventRepository.save(event);
    }

    public List<Event> getEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    public List<Event> getAvailableEvents() {
        return eventRepository.findAvailableEvents(LocalDateTime.now());
    }
}