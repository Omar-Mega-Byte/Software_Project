package com.example.event_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {

    @GetMapping("/events")
    public String showEventList() {
        return "events/list";
    }

    @GetMapping("/events/{id}")
    public String showEventDetails() {
        return "events/details";
    }

    @GetMapping({"/events/create", "/events/{id}/edit"})
    public String showEventForm() {
        return "events/form";
    }
}