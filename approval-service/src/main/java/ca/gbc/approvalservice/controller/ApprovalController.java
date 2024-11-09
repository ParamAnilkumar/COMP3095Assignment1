package ca.gbc.approvalservice.controller;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.service.ApprovalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/

@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
public class ApprovalController {
    private final ApprovalServiceImpl approvalService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getEventInfo(@PathVariable String id){


        String eventInfo = approvalService.getEventInfo(id);
        if(!eventInfo.isEmpty()) {
            return ResponseEntity.ok(eventInfo);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> approveOrRejectEvent(@PathVariable String id, @RequestBody ApprovalRequest request){
        try{boolean approved = approvalService.approveOrRejectEvent(id,request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Event Approved.");
    }
    catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }
    }

    @GetMapping
    public ResponseEntity<List<Approval>> getAllApprovals(){
        List<Approval> approvals = approvalService.getAllApprovals();
        if(approvals == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else{
            return ResponseEntity.ok(approvals);
        }
    }
}
