package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @project Assignment1-parent
 * @author
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${service.user-url}")
    private String userServiceUrl;

    @Value("${service.booking-url}")
    private String bookingServiceUrl;

    @Override
    public String createEvent(EventRequest request) {
        String bookingUrl = bookingServiceUrl + request.bookingId();
        Boolean exists = checkBookingExists(request.bookingId());

        if (Boolean.TRUE.equals(exists)) {
            Event event = mapRequestToEvent(request);
            boolean isStudent = getUserType(event.getUserId());

            if (isStudent) {
                if (event.getExpectedAttendees() <= 100) {
                    Event savedEvent = eventRepository.save(event);
                    log.debug("Event organized for student with event id: {}", savedEvent.getId());
                    String eventMessage = String.format(" Student Event created: "+savedEvent.getId()+" & userId: "+savedEvent.getUserId()+" & bookingId: "+savedEvent.getBookingId());
                    kafkaTemplate.send("event-events",eventMessage);
                    return "Event organized for student with event id: " + savedEvent.getId();

                } else {
                    log.warn("Students can't book large events. Limit is 100 attendees.");
                    return "Students can't book large events. Limit is 100 attendees.";
                }
            } else {
                Event savedEvent = eventRepository.save(event);
                log.debug("Event organized with id: {}", savedEvent.getId());
                String eventMessage = String.format("Event created: "+savedEvent.getId()+" & userId: "+savedEvent.getUserId()+" & bookingId: "+savedEvent.getBookingId());
                kafkaTemplate.send("event-events",eventMessage);
                return "Event organized with id: " + savedEvent.getId();
            }
        } else {
            log.warn("Booking does not exist.");
            throw new IllegalStateException("Booking does not exist.");
        }
    }

    @Override
    public List<EventResponse> getAllEvents() {
        log.debug("Returning a list of events.");
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return null;
        }
        return events.stream().map(this::mapToEventResponse).toList();
    }

    @Override
    public void deleteEvent(String id) {
        log.debug("Deleting the event with id: {}", id);
        eventRepository.deleteById(id);
    }

    @Override
    public void updateEventDate(String id, String date) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            event.setEventDate(date);
            eventRepository.save(event);
        }
    }

    @Override
    public EventResponse getEventById(String id) {
        log.debug("Getting event by id: {}", id);
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return null;
        }
        return mapToEventResponse(event);
    }

    public void changeApproval(String id, boolean approve) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            event.setApproved(approve);
            eventRepository.save(event);
        }
    }

    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallbackForBookingCheck")
    public boolean checkBookingExists(String id) {
        Boolean exists = restTemplate.getForObject(bookingServiceUrl+id, Boolean.class);
        return Boolean.TRUE.equals(exists);
    }

    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallbackForUserType")
    public boolean getUserType(Long id) {
        String userUrl = userServiceUrl + id;
        String isStudent = restTemplate.getForObject(userUrl, String.class);
        return Objects.equals(isStudent, "true");
    }

    private EventResponse mapToEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getBookingId(),
                event.getUserId(),
                event.getEventName(),
                event.getExpectedAttendees(),
                event.getEventDate()
        );
    }

    private Event mapRequestToEvent(EventRequest request) {
        return new Event(
                request.id(),
                request.bookingId(),
                request.userId(),
                request.eventName(),
                request.expectedAttendees(),
                request.eventDate(),
                false
        );
    }

    // Fallback methods
    public boolean fallbackForBookingCheck(String bookingUrl, Throwable throwable) {
        log.error("Booking service is unavailable. Error: {}", throwable.getMessage());
        return false; // Default fallback behavior: assume booking does not exist.
    }

    public boolean fallbackForUserType(Long id, Throwable throwable) {
        log.error("User service is unavailable. Error: {}", throwable.getMessage());
        return false; // Default fallback behavior: assume user is not a student.
    }
}
