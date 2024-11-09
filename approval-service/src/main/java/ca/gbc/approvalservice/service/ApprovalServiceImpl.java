package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService{

    public final String eventServiceUrl = "http://event-service:8089/api/events/";
    private final RestTemplate restTemplate;
    private final ApprovalRepository approvalRepository;
    @Override
    public String getEventInfo(String id) {
        String eventUrl = eventServiceUrl+id;
        return restTemplate.getForObject(eventUrl, String.class);
    }

    @Override
    public boolean approveOrRejectEvent(String eventId, ApprovalRequest request) {
        String eventUrl = eventServiceUrl+eventId+"approval";
        Approval approval = new Approval(request.id(), eventId,request.userId(),request.approved());
        approvalRepository.save(approval);
        return approval.isApproved();
    }

    @Override
    public List<Approval> getAllApprovals() {
        return approvalRepository.findAll();
    }
}
