package callum.project.uni.rms.submission.mapper;

import callum.project.uni.rms.submission.model.request.RequestRole;
import callum.project.uni.rms.submission.model.request.RoleCreateReq;
import callum.project.uni.rms.submission.model.source.RoleSubmission;
import callum.project.uni.rms.submission.model.target.TargetRole;
import callum.project.uni.rms.submission.model.target.TargetRoleSubmission;

import static callum.project.uni.rms.parent.mapper.MapperUtils.convertLocalDateToSqlDate;
import static callum.project.uni.rms.parent.mapper.MapperUtils.convertSqlDateToLocalDate;

public class RoleSubmissionMapper {

    public static TargetRoleSubmission mapRoleSubmissionToTarget(RoleSubmission roleSubmission) {
        TargetRole targetRole = mapDBSubRoleToTarget(roleSubmission);
        return TargetRoleSubmission.builder()
                .submittedRole(targetRole)
                .candidateId(roleSubmission.getCandidateId())
                .submissionId(roleSubmission.getSubmissionId())
                .build();
    }

    public static TargetRole mapDBSubRoleToTarget(RoleSubmission dbRole) {
        return TargetRole.builder()
                .accountName(dbRole.getAccountName())
                .accountNumber(dbRole.getAccountNumber())
                .projectName(dbRole.getProjectName())
                .projectCode(dbRole.getProjectCode())
                .businessUnit(dbRole.getBusinessUnitId())
                .endDate(convertSqlDateToLocalDate(dbRole.getEndDate()))
                .startDate(convertSqlDateToLocalDate(dbRole.getStartDate()))
                .roleName(dbRole.getRoleName())
                .description(dbRole.getDescription())
                .baseLocation(dbRole.getBaseLocation())
                .roleType(dbRole.getRoleType())
                .build();
    }

    public static RoleSubmission mapCreateRoleReqToDbRole(RoleCreateReq roleCreateReq) {
        RequestRole role = roleCreateReq.getRole();

        return RoleSubmission.builder()
                .candidateId(roleCreateReq.getUserId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .roleType(role.getRoleType())
                .accountName(role.getAccountName())
                .accountNumber(role.getAccountNumber())
                .baseLocation(role.getBaseLocation())
                .projectCode(role.getProjectCode())
                .businessUnitId(role.getBusinessUnit())
                .projectName(role.getProjectName())
                .startDate(convertLocalDateToSqlDate(role.getStartDate()))
                .endDate(convertLocalDateToSqlDate(role.getEndDate()))
                .build();
    }
}
