package ca.gbc.eventservice.controller;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.service.EventService;
import ca.gbc.eventservice.service.EventServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventServiceImpl eventService;
    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody EventRequest request) {
        try {
            String response = eventService.createEvent(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        if (events == null || events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(events);
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getEventById(@PathVariable String id) {
        EventResponse eventResponse = eventService.getEventById(id);
        if (eventResponse != null) {
            return ResponseEntity.ok(eventResponse.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with ID: " + id);
        }
    }

    @PutMapping("/{id}/date")
    public ResponseEntity<String> updateEventDate(@PathVariable String id, @RequestBody String newDate) {
        try {
            eventService.updateEventDate(id, newDate);
            return ResponseEntity.ok("Event date updated for ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with ID: " + id);
        }
    }

    @PutMapping("{id}/approval")
    public ResponseEntity<String> changeApprovalStatus(@PathVariable String id,@RequestParam boolean status){
        try{
        eventService.changeApproval(id,status);
        return ResponseEntity.ok("Event approved.");}
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }

    }

}
