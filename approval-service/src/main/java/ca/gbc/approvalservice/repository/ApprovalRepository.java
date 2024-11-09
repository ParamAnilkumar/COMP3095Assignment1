package ca.gbc.approvalservice.repository;

import ca.gbc.approvalservice.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface ApprovalRepository extends JpaRepository<Approval,Long> {

}
