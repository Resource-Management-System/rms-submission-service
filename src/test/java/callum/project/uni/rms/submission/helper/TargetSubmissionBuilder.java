package callum.project.uni.rms.submission.helper;

import callum.project.uni.rms.submission.model.BaseLocation;
import callum.project.uni.rms.submission.model.RoleType;
import callum.project.uni.rms.submission.model.request.RequestRole;
import callum.project.uni.rms.submission.model.request.RoleCreateReq;
import callum.project.uni.rms.submission.model.target.TargetRole;
import callum.project.uni.rms.submission.model.target.TargetRoleSubmission;

import java.time.LocalDate;

public class TargetSubmissionBuilder {

    public static final Long ROLE_ID = 1L;
    public static final Long CANDIDATE_ID = 2L;
    public static final Long SUBMISSION_ID = 3L;
    public static final Long RM_ID = 1L;

    public static TargetRoleSubmission buildTargetSubmission(){
        return TargetRoleSubmission.builder()
                .candidateId(CANDIDATE_ID)
                .submissionId(SUBMISSION_ID)
                .submittedRole(TargetRole.builder().id(ROLE_ID).build())
                .build();
    }

    public static RoleCreateReq buildSubCreateReq(){
        return RoleCreateReq.builder()
                .certainty(100.00)
                .role(RequestRole.builder()
                        .roleName("ROLE_NAME")
                        .roleType(RoleType.BUSINESS_ANALYST)
                        .accountName("ACCOUNT_NAME")
                        .accountNumber("1")
                        .baseLocation(BaseLocation.ASTON)
                        .description("DESC")
                        .endDate(LocalDate.of(2012, 1, 1))
                        .startDate(LocalDate.of(2010, 1, 1))
                        .projectCode("2")
                        .projectName("PROJECT_NAME")
                        .businessUnit(1L)
                        .build())
                .userId(CANDIDATE_ID)
                .build();
    }
}
