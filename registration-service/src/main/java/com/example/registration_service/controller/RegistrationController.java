package com.example.registration_service.controller;

import com.example.registration_service.dto.EventWithParticipantsDTO;
import com.example.registration_service.dto.ParticipantWithEventsDTO;
import com.example.registration_service.model.Event;
import com.example.registration_service.model.Participant;
import com.example.registration_service.model.Registration;
import com.example.registration_service.repository.RegistrationRepository;
import com.example.registration_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationController(RegistrationService registrationService, RegistrationRepository registrationRepository) {
        this.registrationService = registrationService;
        this.registrationRepository = registrationRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Integer> payload) {
        try {
            Long eventId = Long.valueOf(payload.get("eventId"));
            Long userId = Long.valueOf(payload.get("userId"));

            if (registrationRepository.findByEventIdAndParticipantId(eventId, userId).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Already registered for this event"));
            }

            Registration registration = new Registration();
            registration.setEventId(eventId);
            registration.setParticipantId(userId);
            registration.setRegistrationTime(LocalDateTime.now());
            registrationRepository.save(registration);

            return ResponseEntity.ok(Map.of("success", true, "message", "Successfully registered for event " + eventId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Registration failed: " + e.getMessage()));
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventWithParticipantsDTO>> getAllEventsWithParticipants() {
        List<EventWithParticipantsDTO> events = registrationService.getAllEventsWithParticipants();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/participants")
    public ResponseEntity<List<ParticipantWithEventsDTO>> getAllParticipantsWithEvents() {
        List<ParticipantWithEventsDTO> participants = registrationService.getAllParticipantsWithEvents();
        return ResponseEntity.ok(participants);
    }

    private Event getEventFromService(Long eventId) {
        return registrationService.getEventFromService(eventId);
    }


    private void updateEventInService(Event event) {
        registrationService.updateEventInService(event); // Placeholder
    }

}