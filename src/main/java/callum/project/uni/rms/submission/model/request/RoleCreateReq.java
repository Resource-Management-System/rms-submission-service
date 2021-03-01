package callum.project.uni.rms.submission.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RoleCreateReq {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("role")
    private RequestRole role;

    @JsonProperty("certainty")
    private Double certainty;
}
