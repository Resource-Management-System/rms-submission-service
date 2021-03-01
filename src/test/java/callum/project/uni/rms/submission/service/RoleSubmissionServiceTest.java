package callum.project.uni.rms.submission.service;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.project.uni.rms.submission.model.source.RoleSubmission;
import callum.project.uni.rms.submission.model.target.RoleSubmissions;
import callum.project.uni.rms.submission.model.target.TargetRoleSubmission;
import callum.project.uni.rms.submission.service.repository.RoleSubmissionRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static callum.project.uni.rms.submission.helper.SourceSubmissionBuilder.buildSourceRoleSubmission;
import static callum.project.uni.rms.submission.helper.TargetSubmissionBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleSubmissionServiceTest {

    RoleSubmissionRepository roleSubmissionRepository;
    RoleSubmissionService roleSubmissionService;

    @BeforeEach
    public void setup() {
        roleSubmissionRepository = mock(RoleSubmissionRepository.class);
        roleSubmissionService = new RoleSubmissionService(roleSubmissionRepository);
    }

    @Test
    void retrieveRoleSubmissionsForRM_happyPath() {
        when(roleSubmissionRepository.findAllSubmissionsForRm(eq(RM_ID)))
                .thenReturn(List.of(buildSourceRoleSubmission()));
        RoleSubmissions subs = roleSubmissionService.retrieveRoleSubmissionsForRM(RM_ID);
        assertEquals(SUBMISSION_ID, subs.getRoleSubmissions().get(0).getSubmissionId());
        assertEquals(CANDIDATE_ID, subs.getRoleSubmissions().get(0).getCandidateId());
    }

    @Test
    void retrieveRoleSubmissionsForRM_internalServerError() {
        when(roleSubmissionRepository.findAllSubmissionsForRm(eq(RM_ID)))
                .thenThrow(HibernateException.class);
        assertThrows(InternalServiceException.class,
                () -> roleSubmissionService.retrieveRoleSubmissionsForRM(RM_ID));
    }

    @Test
    void retrieveRoleSub_happyPath() {
        when(roleSubmissionRepository.findById(eq(SUBMISSION_ID)))
                .thenReturn(Optional.of(buildSourceRoleSubmission()));

        TargetRoleSubmission roleSubmission = roleSubmissionService.retrieveRoleSub(SUBMISSION_ID);
        assertEquals(SUBMISSION_ID, roleSubmission.getSubmissionId());
        assertEquals(CANDIDATE_ID, roleSubmission.getCandidateId());
    }

    @Test
    void retrieveRoleSub_notFound() {
        when(roleSubmissionRepository.findById(eq(SUBMISSION_ID)))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleSubmissionService.retrieveRoleSub(SUBMISSION_ID));
    }

    @Test
    void retrieveRoleSub_internalSeverException() {
        when(roleSubmissionRepository.findById(eq(SUBMISSION_ID)))
                .thenThrow(HibernateException.class);

        assertThrows(InternalServiceException.class, () -> roleSubmissionService.retrieveRoleSub(SUBMISSION_ID));
    }

    @Test
    void removeRoleSubmission_happyPath() {

        roleSubmissionService.removeRoleSubmission(SUBMISSION_ID);
        verify(roleSubmissionRepository, times(1)).deleteById(eq(SUBMISSION_ID));
    }

    @Test
    void removeRoleSubmission_internalServerException() {
        doThrow(new InternalServiceException("ex", new HibernateException("ex")))
                .when(roleSubmissionRepository).deleteById(eq((SUBMISSION_ID)));
        assertThrows(InternalServiceException.class, () -> roleSubmissionService.removeRoleSubmission(SUBMISSION_ID));
        verify(roleSubmissionRepository, times(1)).deleteById(eq(SUBMISSION_ID));
    }

    @Test
    void submitNewApplication_happyPath(){
        when(roleSubmissionRepository.save(any(RoleSubmission.class)))
                .thenReturn(buildSourceRoleSubmission());

        TargetRoleSubmission roleSubmission = roleSubmissionService.submitNewApplication(buildSubCreateReq());
        assertEquals(SUBMISSION_ID, roleSubmission.getSubmissionId());
        assertEquals(CANDIDATE_ID, roleSubmission.getCandidateId());
    }

    @Test
    void submitNewApplication_internalServerException() {
        doThrow(new InternalServiceException("ex", new HibernateException("ex")))
                .when(roleSubmissionRepository).save(any(RoleSubmission.class));
        assertThrows(InternalServiceException.class, () -> roleSubmissionService.submitNewApplication(buildSubCreateReq()));
    }
}