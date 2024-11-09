package ca.gbc.eventservice.model;

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

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    private String id;
    private String bookingId;
    private Long userId;
    private String eventName;
    private int expectedAttendees;
    private String eventDate;
    private boolean approved = false;
}
