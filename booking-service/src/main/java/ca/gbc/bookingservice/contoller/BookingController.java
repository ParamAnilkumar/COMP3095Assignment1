package ca.gbc.bookingservice.contoller;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.service.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingServiceImpl bookingService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getAllBookings(){
        return bookingService.getAllBookings();
    }


    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable("bookingId") String bookingId){
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> makeABooking(@RequestBody BookingRequest bookingRequest){
        bookingService.makeABooking(bookingRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location","/api/booking" + bookingRequest.id());
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Booking made.");

    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable("bookingId") String bookingId,@RequestBody BookingRequest bookingRequest){
        bookingService.updateBooking(bookingId,bookingRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location" ," /api/booking" + bookingId);

        return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable String id) {
        BookingResponse bookingResponse = bookingService.getBookingById(id);

        if (bookingResponse != null) {
            return ResponseEntity.ok(bookingResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("exists/{id}")
    public ResponseEntity<Boolean> bookingExists(@PathVariable String id){
        boolean exists = bookingService.bookingExists(id);
        return ResponseEntity.ok(exists);
    }



}
