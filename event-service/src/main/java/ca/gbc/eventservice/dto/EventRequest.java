package ca.gbc.eventservice.dto;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public record EventRequest(
        String id,
        String bookingId,
        Long userId,
        String eventName,
        int expectedAttendees,
        String eventDate
) {
}
