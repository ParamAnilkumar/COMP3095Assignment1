package ca.gbc.userservice.service;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;

import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long userId);
    UserResponse createUser(UserRequest request);
    void deleteUser(Long userId);
    Long updateUser(Long userId,UserRequest userDetails);

}
