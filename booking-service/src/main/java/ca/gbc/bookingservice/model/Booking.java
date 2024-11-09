package ca.gbc.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/

@Document(value="bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    private String id;
    private String userId;
    private String roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
}
