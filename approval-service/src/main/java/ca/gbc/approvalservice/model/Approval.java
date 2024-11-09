package ca.gbc.approvalservice.model;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_approvals")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "event_id")
    public String eventId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "approved")
    private boolean approved;
}
