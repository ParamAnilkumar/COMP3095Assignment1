package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;

import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface BookingService {
    String makeABooking(BookingRequest request);
    List<BookingResponse> getAllBookings();
    String updateBooking(String id, BookingRequest request);
    void deleteBooking(String id);
    BookingResponse getBookingById(String id);
    boolean bookingExists(String id);

}
