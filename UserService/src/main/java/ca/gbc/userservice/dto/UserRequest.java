package ca.gbc.userservice.dto;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public record UserRequest(
        Long id,
        String name,
        String email,
        String role,
        boolean student) {
}
