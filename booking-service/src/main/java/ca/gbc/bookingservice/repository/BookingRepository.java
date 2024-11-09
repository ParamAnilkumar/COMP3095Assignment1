package ca.gbc.bookingservice.repository;

import ca.gbc.bookingservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface BookingRepository extends MongoRepository<Booking,String> {
    List<Booking> findByRoomIdAndStartTimeBetweenOrEndTimeBetween(String roomId, LocalDateTime start, LocalDateTime end);
}
