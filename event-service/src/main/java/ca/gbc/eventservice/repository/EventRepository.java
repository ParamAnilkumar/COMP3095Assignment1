package ca.gbc.eventservice.repository;

import ca.gbc.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface EventRepository extends MongoRepository<Event,String> {
    List<Event> getAllByEventDate(String eventDate);


}
