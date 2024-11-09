package ca.gbc.roomservice.service;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;
import ca.gbc.roomservice.model.Room;
import ca.gbc.roomservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @project microservice-parent
 * @authorparam on 28-10-2024
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAllRooms(){
        log.debug("Returning a list of Rooms.");
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    private RoomResponse mapToRoomResponse(Room room) {
        return new RoomResponse(room.getId(),room.getRoomName(),room.getCapacity(),room.isAcRoom(),room.isAvailability());
    }
    private Room mapRequestToRoom(RoomRequest request){
        return new Room(request.id(),request.roomName(),request.capacity(),request.ac(),request.availability());
    }


    @Override
    public RoomResponse getRoomById(Long roomId) {
        log.debug("Getting room by + " + roomId);
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room != null) {
            return mapToRoomResponse(room);
        }
        else return null;
    }

    @Override
    public RoomResponse addRoom(RoomRequest request) {
        log.debug("Added the room with name + " + request.roomName());
        Room room = mapRequestToRoom(request);
        roomRepository.save(room);
        return mapToRoomResponse(room);
    }

    @Override
    public Long updateRoom(Long roomId, RoomRequest roomDetails) {
        log.debug("Updating a product with id{} + %d".formatted(roomId));
        Room room = roomRepository.findById(roomId).orElse(null);
        if(room!=null){
            room.setRoomName(roomDetails.roomName());
            room.setCapacity(roomDetails.capacity());
            room.setAcRoom(roomDetails.ac());
            room.setAvailability(roomDetails.availability());

            return roomRepository.save(room).getId();
        }

        return roomId;
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public List<RoomResponse> getAvailableRooms() {
        log.debug("Returning a list of Rooms.");
        List<Room> rooms = roomRepository.findByAvailability(true);
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    @Override
    public Boolean checkRoomAvailability(Long roomId) {

        Room room = roomRepository.findById(roomId).orElse(null);
        if(room!=null){
            return room.isAvailability();
        }
        else{
            return null;
        }
    }

    @Override
    public void updateRoomAvailability(Long roomId, boolean available) {
        Room room = roomRepository.findById(roomId).orElse(null);
        assert room != null;
        room.setAvailability(available);
        roomRepository.save(room);
    }

}
