package ca.gbc.eventservice.dto;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public record EventResponse(
        String id,
        String bookingId,
        Long userId,
        String eventName,
        int expectedAttendees,
        String eventDate
) {
}
