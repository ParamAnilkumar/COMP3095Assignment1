package ca.gbc.roomservice.dto;

/**
 * @project microservice-parent
 * @authorparam on 28-10-2024
 **/
public record RoomResponse(
        Long id,
        String roomName,
        int capacity,
        boolean ac,
        boolean availability
) {
}
