package ca.gbc.roomservice.repository;

import ca.gbc.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @project microservice-parent
 * @authorparam on 28-10-2024\
 **/
public interface RoomRepository extends JpaRepository<Room,Long> {

List<Room> findByAvailability(boolean availability);
}
