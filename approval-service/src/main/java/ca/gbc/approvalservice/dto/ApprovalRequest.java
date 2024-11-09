package ca.gbc.approvalservice.dto;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/
public record ApprovalRequest(

        Long id,
        String userId,
        boolean approved
) {
}
