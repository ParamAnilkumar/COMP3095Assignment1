package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.model.Approval;

import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public interface ApprovalService {

    String getEventInfo(String id);
    boolean approveOrRejectEvent(String id, ApprovalRequest request);

    List<Approval> getAllApprovals();

}
