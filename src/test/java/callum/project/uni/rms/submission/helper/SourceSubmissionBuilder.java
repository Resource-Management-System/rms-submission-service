package callum.project.uni.rms.submission.helper;

import callum.project.uni.rms.submission.model.BaseLocation;
import callum.project.uni.rms.submission.model.RoleType;
import callum.project.uni.rms.submission.model.source.RoleSubmission;

import java.sql.Date;
import java.time.LocalDate;

import static callum.project.uni.rms.submission.helper.TargetSubmissionBuilder.*;

public class SourceSubmissionBuilder {

    public static Date SOURCE_START_DATE = Date.valueOf(TARGET_START_DATE);
    public static Date SOURCE_END_DATE = Date.valueOf(TARGET_END_DATE);

    public static RoleSubmission buildSourceRoleSubmission(){
        return RoleSubmission.builder()
                .accountName("ACCOUNT_NAME")
                .accountNumber("1")
                .baseLocation(BaseLocation.ASTON)
                .businessUnitId(BUSINESS_UNIT_ID)
                .candidateId(CANDIDATE_ID)
                .description("DESC")
                .endDate(SOURCE_END_DATE)
                .startDate(SOURCE_START_DATE)
                .projectCode("2")
                .projectName("PROJECT_NAME")
                .roleName("ROLE_NAME")
                .roleType(RoleType.BUSINESS_ANALYST)
                .submissionId(SUBMISSION_ID)
                .build();
    }
}
