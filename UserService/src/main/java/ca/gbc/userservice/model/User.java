package ca.gbc.userservice.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean student;
}
