package callum.project.uni.rms.submission.model.target;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleSubmissions extends AbstractServiceResponse {

    private List<TargetRoleSubmission> roleSubmissions;
}
