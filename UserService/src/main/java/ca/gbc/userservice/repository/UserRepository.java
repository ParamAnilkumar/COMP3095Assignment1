package ca.gbc.userservice.repository;

import ca.gbc.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
