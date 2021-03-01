package callum.project.uni.rms.submission.model.request;

import callum.project.uni.rms.submission.model.BaseLocation;
import callum.project.uni.rms.submission.model.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RequestRole {

    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("roleType")
    private RoleType roleType;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("projectCode")
    private String projectCode;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("baseLocation")
    private BaseLocation baseLocation;

    @JsonProperty("businessUnit")
    private Long businessUnit;
}
