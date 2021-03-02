package callum.project.uni.rms.submission.mapper;

import callum.project.uni.rms.submission.model.BaseLocation;
import callum.project.uni.rms.submission.model.RoleType;
import callum.project.uni.rms.submission.model.source.RoleSubmission;
import callum.project.uni.rms.submission.model.target.TargetRole;
import callum.project.uni.rms.submission.model.target.TargetRoleSubmission;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static callum.project.uni.rms.submission.helper.SourceSubmissionBuilder.*;
import static callum.project.uni.rms.submission.helper.TargetSubmissionBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleSubmissionMapperTest {

    @Test
    void mapRoleSubmissionToTarget(){
        TargetRoleSubmission roleSubmission = RoleSubmissionMapper.mapRoleSubmissionToTarget(buildSourceRoleSubmission());
        TargetRole role = roleSubmission.getSubmittedRole();
        assertEquals("ACCOUNT_NAME", role.getAccountName());
        assertEquals("1", role.getAccountNumber());
        assertEquals(BaseLocation.ASTON, role.getBaseLocation());
        assertEquals(BUSINESS_UNIT_ID, role.getBusinessUnit());
        assertEquals("DESC", role.getDescription());
        assertEquals(TARGET_END_DATE, role.getEndDate());
        assertEquals(TARGET_START_DATE, role.getStartDate());
        assertEquals("2", role.getProjectCode());
        assertEquals("PROJECT_NAME", role.getProjectName());
        assertEquals("ROLE_NAME", role.getRoleName());
        assertEquals(RoleType.BUSINESS_ANALYST, role.getRoleType());
        assertEquals(SUBMISSION_ID, roleSubmission.getSubmissionId());
        assertEquals(CANDIDATE_ID, roleSubmission.getCandidateId());
    }

    @Test
    void mapCreateRoleReqToDbRole(){

        RoleSubmission roleSubmission = RoleSubmissionMapper.mapCreateRoleReqToDbRole(buildSubCreateReq());
        assertEquals(CANDIDATE_ID, roleSubmission.getCandidateId());

        assertEquals("ACCOUNT_NAME", roleSubmission.getAccountName());
        assertEquals("1", roleSubmission.getAccountNumber());
        assertEquals(BaseLocation.ASTON, roleSubmission.getBaseLocation());
        assertEquals(BUSINESS_UNIT_ID, roleSubmission.getBusinessUnitId());
        assertEquals("DESC", roleSubmission.getDescription());
        assertEquals(SOURCE_END_DATE, roleSubmission.getEndDate());
        assertEquals(SOURCE_START_DATE, roleSubmission.getStartDate());
        assertEquals("2", roleSubmission.getProjectCode());
        assertEquals("PROJECT_NAME", roleSubmission.getProjectName());
        assertEquals("ROLE_NAME", roleSubmission.getRoleName());
        assertEquals(RoleType.BUSINESS_ANALYST, roleSubmission.getRoleType());
    }
}