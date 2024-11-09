package ca.gbc.bookingservice.dto;

import java.time.LocalDateTime;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public record BookingRequest(
        String id,
        String userId,
        String roomId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose) {
}
