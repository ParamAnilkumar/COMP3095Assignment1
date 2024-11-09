package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {


    private final BookingRepository bookingRepository;
    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;
    String roomServiceUrl = "http://room-service:8086/api/rooms/";

    private BookingResponse mapToBookingResponse(Booking booking) {
        return new BookingResponse(booking.getId(),booking.getUserId(),booking.getRoomId(),booking.getStartTime(),booking.getEndTime(),booking.getPurpose());
    }
    private Booking mapRequestToBooking(BookingRequest request){
        return new Booking(request.id(), request.userId(), request.roomId(), request.startTime(),request.endTime(), request.purpose());
    }


    @Override
    public String makeABooking(BookingRequest request) {

        String roomUrl = roomServiceUrl+"availability/"+request.roomId();
        Boolean isAvailable = restTemplate.getForObject(roomUrl,Boolean.class);
        if(Boolean.TRUE.equals(isAvailable)){

            Booking booking = mapRequestToBooking(request);
            Booking savedBooking = bookingRepository.save(booking);
            log.debug("Booking made with id: "+savedBooking.getId());
            updateRoomAvailability(request.roomId(),false);
            return "Booking made. with id + " + savedBooking.getId();
        }
        else {
            log.warn("Room with ID {} is not available for booking.", request.roomId());
            throw new IllegalStateException("Room is not available for booking.");
        }
    }

    private void updateRoomAvailability(String roomId, boolean available) {
        String url = String.format("%s/%s/availability?available=%b", roomServiceUrl, roomId, available);
        restTemplate.put(url, null);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        log.debug("Returning a list of Bookings.");
        List<Booking> bookings = bookingRepository.findAll();
        if(bookings.isEmpty()){
            return null;
        }
        return bookings.stream().map(this::mapToBookingResponse).toList();
    }

    @Override
    public String updateBooking(String id, BookingRequest request) {
        log.debug("Updating a booking with id{}",id);
        //finding product
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Booking booking = mongoTemplate.findOne(query, Booking.class);
        if(booking!=null){
           booking = mapRequestToBooking(request);
            return bookingRepository.save(booking).getId();
        }
        return id;
    }

    @Override
    public void deleteBooking(String id) {
        log.debug("Delete the booking with id{}",id);
        bookingRepository.deleteById(id);
        Booking booking = bookingRepository.findById(id).orElse(null);

        assert booking != null;
        updateRoomAvailability(booking.getRoomId(),true);
    }

    @Override
    public BookingResponse getBookingById(String id) {
        log.debug("Getting the booking with id{}",id);
        Booking booking = bookingRepository.findById(id).orElse(null);
        BookingResponse response;
        assert booking != null;
        response = mapToBookingResponse(booking);
        return response;

    }

    @Override
    public boolean bookingExists(String id) {
        log.debug("Getting the booking with id{}",id);
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking!=null) return true;
        else return false;

    }




}
