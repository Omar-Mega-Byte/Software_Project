package com.example.event_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @Future(message = "Event date must be in the future")
    @NotNull(message = "Date and time are required")
    private LocalDateTime dateTime;

    @NotBlank(message = "Location is required")
    @Size(max = 200, message = "Location cannot exceed 200 characters")
    private String location;

    @Min(value = 1, message = "Maximum seats must be at least 1")
    @NotNull(message = "Maximum seats are required")
    private Integer maxSeats;

    @NotNull(message = "Available seats are required")
    private Integer availableSeats;

    @NotNull(message = "Canceled status is required")
    @Builder.Default
    private Boolean isCanceled = false;

//    @NotNull(message = "Organizer ID is required")
    private Long organizerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }


    public boolean isEventAvailable() {
        return !isCanceled &&
                availableSeats > 0 &&
                dateTime.isAfter(LocalDateTime.now());
    }

    public void updateAvailableSeats(int change) {
        int newAvailable = this.availableSeats + change;
        if (newAvailable < 0 || newAvailable > maxSeats) {
            throw new IllegalStateException("Invalid seat count");
        }
        this.availableSeats = newAvailable;
    }
}