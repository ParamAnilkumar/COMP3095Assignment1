package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.repository.ApprovalRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @project Assignment1-parent
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    @Value("${service.event-url}")
    public String eventServiceUrl;

    private final RestTemplate restTemplate;
    private final ApprovalRepository approvalRepository;

    @Override
    @CircuitBreaker(name = "eventServiceCircuitBreaker", fallbackMethod = "fallbackForGetEventInfo")
    public String getEventInfo(String id) {
        String eventUrl = eventServiceUrl + id;
        log.debug("Fetching event information from URL: {}", eventUrl);
        return restTemplate.getForObject(eventUrl, String.class);
    }

    @Override
    @CircuitBreaker(name = "eventServiceCircuitBreaker", fallbackMethod = "fallbackForApproveOrRejectEvent")
    public boolean approveOrRejectEvent(String eventId, ApprovalRequest request) {
        String eventUrl = eventServiceUrl + eventId + "/approval";
        log.debug("Processing event approval/rejection for event ID: {}", eventId);

        Approval approval = new Approval(request.id(), eventId, request.userId(), request.approved());
        approvalRepository.save(approval);

        // Notify event-service of the approval/rejection
        restTemplate.put(eventUrl, approval.isApproved());
        return approval.isApproved();
    }

    @Override
    public List<Approval> getAllApprovals() {
        log.debug("Fetching all approvals from the database.");
        return approvalRepository.findAll();
    }

    // Fallback methods
    public String fallbackForGetEventInfo(String id, Throwable throwable) {
        log.error("Event-service is unavailable. Failed to fetch event information for ID: {}. Error: {}", id, throwable.getMessage());
        return "Event information not available at the moment.";
    }

    public boolean fallbackForApproveOrRejectEvent(String eventId, ApprovalRequest request, Throwable throwable) {
        log.error("Event-service is unavailable. Failed to process approval/rejection for event ID: {}. Error: {}", eventId, throwable.getMessage());

        // Log approval as pending due to the service failure
        Approval approval = new Approval(request.id(), eventId, request.userId(), false);
        approvalRepository.save(approval);
        return false; // Default to rejection until resolved
    }
}
