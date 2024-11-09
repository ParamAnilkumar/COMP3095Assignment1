package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;

    String userServiceUrl="http://user-service:8087/api/users/student/";
    String bookingServiceUrl = "http://booking-service:8088/api/bookings/exists/";
    @Override
    public String createEvent(EventRequest request) {
        String bookingUrl = bookingServiceUrl+request.bookingId();
        Boolean exists = restTemplate.getForObject(bookingUrl, Boolean.class);
        if(Boolean.TRUE.equals(exists)){

            Event event = mapRequestToEvent(request);
            boolean student = getUserType(event.getUserId());
            if(student){
                if(event.getExpectedAttendees()<=100){
                    Event savedEvent = eventRepository.save(event);
                    log.debug("Event organized for student with event id "+ savedEvent.getId());
                    return "Event organized for student with event id "+ savedEvent.getId();
                }
                else{
                    log.warn("Student can't book this big event limit is 100");
                    return "Student can't book this big event limit is 100";
                }
            }
            else{
            Event savedEvent = eventRepository.save(event);
            log.debug("Event organized."+ savedEvent.getId());
            return "Event organized with id "+ savedEvent.getId();
            }
        }
        else{
            log.warn("Booking not exists.");
            throw new IllegalStateException("Booking not exists.");
        }
    }

    @Override
    public List<EventResponse> getAllEvents() {
        log.debug("Returning a list of Events.");
        List<Event> events = eventRepository.findAll();
        if(events.isEmpty()){
            return null;
        }
        return events.stream().map(this::mapToEventResponse).toList();
    }

    @Override
    public void deleteEvent(String id) {
        log.debug("Delete the event with id{}",id);
        eventRepository.deleteById(id);
    }

    @Override
    public void updateEventDate(String id, String date) {
    Event event = eventRepository.findById(id).orElse(null);
        assert event != null;
        event.setEventDate(date);
    eventRepository.save(event);
    }
    @Override
    public EventResponse getEventById(String id) {
        log.debug("Getting event by id"+ id);
        Event event = eventRepository.findById(id).orElse(null);
        assert event != null;
        EventResponse response;
        response = mapToEventResponse(event);
        return response;
    }

    public void changeApproval(String id,boolean approve){
        Event event = eventRepository.findById(id).orElse(null);
        assert event != null;
        event.setApproved(approve);
        eventRepository.save(event);
    }

    public boolean getUserType(Long id){
        String eventUrl = userServiceUrl+id;
        String isStudent = restTemplate.getForObject(eventUrl,String.class);
        return Objects.equals(isStudent, "true");
    }

    private EventResponse mapToEventResponse(Event event) {
        return new EventResponse(event.getId(),event.getBookingId(),event.getUserId(),event.getEventName(), event.getExpectedAttendees(), event.getEventDate());
    }
    private Event mapRequestToEvent(EventRequest request){
        return new Event(request.id(),request.bookingId(),request.userId(),request.eventName(),request.expectedAttendees(),request.eventDate(), false);
    }
}
