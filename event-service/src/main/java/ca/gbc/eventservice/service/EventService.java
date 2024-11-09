package ca.gbc.eventservice.service;


import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface EventService {
    String createEvent(EventRequest request);
    List<EventResponse> getAllEvents();
    void deleteEvent(String id);
    void updateEventDate(String id, String date);
    EventResponse getEventById(String id);
}
