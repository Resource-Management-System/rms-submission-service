package callum.project.uni.rms.submission.model.target;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class TargetRoleSubmission extends AbstractServiceResponse {


    private Long candidateId;

    private Long submissionId;

    private TargetRole submittedRole;
}
