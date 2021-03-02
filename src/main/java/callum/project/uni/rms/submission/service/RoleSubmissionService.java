package callum.project.uni.rms.submission.service;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.project.uni.rms.submission.mapper.RoleSubmissionMapper;
import callum.project.uni.rms.submission.model.request.RoleCreateReq;
import callum.project.uni.rms.submission.model.source.RoleSubmission;
import callum.project.uni.rms.submission.model.target.RoleSubmissions;
import callum.project.uni.rms.submission.model.target.TargetRoleSubmission;
import callum.project.uni.rms.submission.service.repository.RoleSubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static callum.project.uni.rms.submission.mapper.RoleSubmissionMapper.mapCreateRoleReqToDbRole;
import static callum.project.uni.rms.submission.mapper.RoleSubmissionMapper.mapRoleSubmissionToTarget;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleSubmissionService {

    private final RoleSubmissionRepository repository;

    public RoleSubmissions retrieveRoleSubmissionsForBu(long buId) {

        try {
            List<RoleSubmission> roleSubmissionList = repository.findAllByBusinessUnitId(buId);

            List<TargetRoleSubmission> targetRoleSubmissions = roleSubmissionList.parallelStream()
                    .map(RoleSubmissionMapper::mapRoleSubmissionToTarget)
                    .collect(Collectors.toList());

            return RoleSubmissions.builder()
                    .roleSubmissions(targetRoleSubmissions)
                    .build();
        } catch (HibernateException e) {
            throw new InternalServiceException("Issue retrieving data for role sub", e);
        }
    }

    public TargetRoleSubmission retrieveRoleSub(long subId) {
        try {

            Optional<RoleSubmission> roleSubOpt = repository.findById(subId);

            return mapRoleSubmissionToTarget(roleSubOpt.orElseThrow(() ->
                    new NotFoundException("Role submission not found")));

        } catch (HibernateException e) {
            throw new InternalServiceException("Error finding role submission", e);
        }
    }

    public void removeRoleSubmission(Long subId) {
        try {
            repository.deleteById(subId);

        } catch (HibernateException e) {
            throw new InternalServiceException("Error removing role submission", e);
        }
    }

    public TargetRoleSubmission submitNewApplication(RoleCreateReq req) {
        try {
            RoleSubmission newRoleSub = mapCreateRoleReqToDbRole(req);
            return mapRoleSubmissionToTarget(repository.save(newRoleSub));
        } catch (HibernateException e) {
            throw new InternalServiceException("Error creating role submission", e);
        }
    }
}
