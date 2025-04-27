package com.example.event_service.controller;

import com.example.event_service.model.Event;
import com.example.event_service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventViewController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public String listEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "events/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        return "events/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "events/form";
    }

    @PostMapping
    public String saveEvent(@ModelAttribute Event event) {
        if (event.getId() != null) {
            eventService.updateEvent(event.getId(), event);
        } else {
            eventService.createEvent(event);
        }
        return "redirect:/events";
    }

    @GetMapping("/{id}")
    public String showEventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "events/details";
    }

    @GetMapping("/{id}/cancel")
    public String cancelEvent(@PathVariable Long id) {
        eventService.cancelEvent(id);
        return "redirect:/events";
    }
}
