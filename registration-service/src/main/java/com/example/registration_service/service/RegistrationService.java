package com.example.registration_service.service;

import com.example.registration_service.dto.EventWithParticipantsDTO;
import com.example.registration_service.dto.ParticipantWithEventsDTO;
import com.example.registration_service.model.Event;
import com.example.registration_service.model.Participant;
import com.example.registration_service.model.Registration;
import com.example.registration_service.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RestTemplate restTemplate;
    private static final String USER_SERVICE_URL = "http://localhost:8082";
    private static final String EVENT_SERVICE_URL = "http://localhost:8081";

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository, RestTemplate restTemplate) {
        this.registrationRepository = registrationRepository;
        this.restTemplate = restTemplate;
    }

    public List<EventWithParticipantsDTO> getAllEventsWithParticipants() {
        // Existing logic remains the same
        List<Registration> registrations = registrationRepository.findAll();

        Event[] eventsArray = restTemplate.getForObject(
                EVENT_SERVICE_URL + "/api/events",
                Event[].class
        );
        List<Event> allEvents = Arrays.asList(eventsArray != null ? eventsArray : new Event[0]);

        Participant[] participantsArray = restTemplate.getForObject(
                USER_SERVICE_URL + "/users/email",
                Participant[].class
        );
        List<Participant> allParticipants = Arrays.asList(participantsArray != null ? participantsArray : new Participant[0]);
        Map<Long, String> participantIdToEmail = allParticipants.stream()
                .collect(Collectors.toMap(Participant::getId, Participant::getEmail));

        List<EventWithParticipantsDTO> eventDTOs = new ArrayList<>();
        for (Event event : allEvents) {
            List<Long> participantIds = registrations.stream()
                    .filter(reg -> reg.getEventId().equals(event.getId()))
                    .map(Registration::getParticipantId)
                    .collect(Collectors.toList());

            List<Participant> participants = new ArrayList<>();
            for (Long participantId : participantIds) {
                String email = participantIdToEmail.get(participantId);
                if (email != null) {
                    Participant participant = restTemplate.getForObject(
                            USER_SERVICE_URL + "/users/email/" + email,
                            Participant.class
                    );
                    if (participant != null) {
                        participants.add(participant);
                    }
                }
            }

            EventWithParticipantsDTO dto = new EventWithParticipantsDTO();
            dto.setEvent(event);
            dto.setParticipants(participants);
            eventDTOs.add(dto);
        }

        return eventDTOs;
    }

    public List<ParticipantWithEventsDTO> getAllParticipantsWithEvents() {
        // Existing logic remains the same
        List<Registration> registrations = registrationRepository.findAll();

        Participant[] participantsArray = restTemplate.getForObject(
                USER_SERVICE_URL + "/users/email",
                Participant[].class
        );
        List<Participant> allParticipants = Arrays.asList(participantsArray != null ? participantsArray : new Participant[0]);

        List<ParticipantWithEventsDTO> participantDTOs = new ArrayList<>();
        for (Participant participant : allParticipants) {
            List<Long> eventIds = registrations.stream()
                    .filter(reg -> reg.getParticipantId().equals(participant.getId()))
                    .map(Registration::getEventId)
                    .collect(Collectors.toList());

            List<Event> events = new ArrayList<>();
            for (Long eventId : eventIds) {
                Event event = restTemplate.getForObject(
                        EVENT_SERVICE_URL + "/api/events/" + eventId,
                        Event.class
                );
                if (event != null) {
                    events.add(event);
                }
            }

            ParticipantWithEventsDTO dto = new ParticipantWithEventsDTO();
            dto.setParticipant(participant);
            dto.setEvents(events);
            participantDTOs.add(dto);
        }

        return participantDTOs;
    }

    // New method to fetch event from service
    public Event getEventFromService(Long eventId) {
        return restTemplate.getForObject(EVENT_SERVICE_URL + "/api/events/" + eventId, Event.class);
    }

    // New method to update event in service
    public void updateEventInService(Event event) {
        restTemplate.put(EVENT_SERVICE_URL + "/api/events/" + event.getId(), event);
    }

    // New method to fetch participant from service
    public Participant getParticipantFromService(Long participantId) {
        // Assuming an endpoint like /users/{id} exists
        return restTemplate.getForObject(USER_SERVICE_URL + "/users/" + participantId, Participant.class);
    }
}