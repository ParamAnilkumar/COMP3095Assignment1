package ca.gbc.userservice.dto;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public record UserResponse(
        Long id,
        String name,
        String email,
        String role,
        boolean student
) {
}
