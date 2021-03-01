package callum.project.uni.rms.submission.model.source;

import callum.project.uni.rms.submission.model.BaseLocation;
import callum.project.uni.rms.submission.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "role_submission")
public class RoleSubmission {

    @Id
    @GeneratedValue
    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "role_type")
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_location")
    private BaseLocation baseLocation;

    @Column(name = "business_unit_id")
    private Long businessUnitId;
}
