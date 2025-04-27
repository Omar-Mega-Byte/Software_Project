package com.example.event_service.repository;
import com.example.event_service.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizerId(Long organizerId);

    @Query("SELECT e FROM Event e WHERE e.isCanceled = false AND e.availableSeats > 0 AND e.dateTime > :now")
    List<Event> findAvailableEvents(LocalDateTime now);
}