package callum.project.uni.rms.submission.helper;

import callum.project.uni.rms.submission.model.BaseLocation;
import callum.project.uni.rms.submission.model.RoleType;
import callum.project.uni.rms.submission.model.source.RoleSubmission;

import java.sql.Date;
import java.time.LocalDate;

import static callum.project.uni.rms.submission.helper.TargetSubmissionBuilder.CANDIDATE_ID;
import static callum.project.uni.rms.submission.helper.TargetSubmissionBuilder.SUBMISSION_ID;

public class SourceSubmissionBuilder {

    public static RoleSubmission buildSourceRoleSubmission(){
        return RoleSubmission.builder()
                .accountName("ACCOUNT_NAME")
                .accountNumber("1")
                .baseLocation(BaseLocation.ASTON)
                .businessUnitId(1L)
                .candidateId(CANDIDATE_ID)
                .description("DESC")
                .endDate(Date.valueOf(LocalDate.of(2012, 1, 1)))
                .startDate(Date.valueOf(LocalDate.of(2012, 1, 1)))
                .projectCode("2")
                .projectName("PROJECT_NAME")
                .roleName("ROLE_NAME")
                .roleType(RoleType.BUSINESS_ANALYST)
                .submissionId(SUBMISSION_ID)
                .build();
    }
}
