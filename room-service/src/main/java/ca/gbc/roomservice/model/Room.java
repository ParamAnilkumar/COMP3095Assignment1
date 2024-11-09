package ca.gbc.roomservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "ac_room")
    private boolean acRoom;

    @Column(name = "availability")
    private boolean availability;
}
