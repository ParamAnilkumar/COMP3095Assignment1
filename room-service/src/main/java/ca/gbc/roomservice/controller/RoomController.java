package ca.gbc.roomservice.controller;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;
import ca.gbc.roomservice.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project microservice-parent
 * @authorparam on 29-10-2024
 **/

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getAllRooms(){ return roomService.getAllRooms();}


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request){
        RoomResponse createRoom = roomService.addRoom(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location","/api/room/"+request.id());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createRoom);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<?> updateProduct(@PathVariable("roomId") Long roomId,
                                           @RequestBody RoomRequest roomRequest){

        Long updateRoomId = roomService.updateRoom(roomId,roomRequest);
        //set the location header attribute
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location" ," /api/room/" + updateRoomId);

        return new ResponseEntity<>(headers,HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("roomId") Long roomId){
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room deleted");
    }

    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getAvailableRooms(){
        return roomService.getAvailableRooms();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long roomId) {
        RoomResponse roomResponse = roomService.getRoomById(roomId);
        return roomResponse != null
                ? ResponseEntity.ok(roomResponse)
                : ResponseEntity.notFound().build();
    }


    @GetMapping("/availability/{roomId}")
    public ResponseEntity<Boolean> checkRoomAvailability(@PathVariable Long roomId) {
        boolean isAvailable = roomService.checkRoomAvailability(roomId);
        return ResponseEntity.ok(isAvailable);
    }

    @PutMapping("{roomId}/availability")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateRoomAvailability(@PathVariable Long roomId,@RequestParam boolean isAvail) {
        try {
            roomService.updateRoomAvailability(roomId, isAvail);
            return ResponseEntity.ok("Room availability updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update room availability.");
        }
    }
}
