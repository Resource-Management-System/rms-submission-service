package callum.project.uni.rms.submission;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.project.uni.rms.submission.model.BaseLocation;
import callum.project.uni.rms.submission.model.RoleType;
import callum.project.uni.rms.submission.model.request.RequestRole;
import callum.project.uni.rms.submission.model.request.RoleCreateReq;
import callum.project.uni.rms.submission.model.target.AbstractServiceResponse;
import callum.project.uni.rms.submission.model.target.RoleSubmissions;
import callum.project.uni.rms.submission.model.target.TargetRole;
import callum.project.uni.rms.submission.model.target.TargetRoleSubmission;
import callum.project.uni.rms.submission.service.RoleSubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

import static callum.project.uni.rms.submission.helper.TargetSubmissionBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleSubmissionController.class)
class RoleSubmissionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleSubmissionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getRoleSubmission_happyPath() throws Exception {
        when(service.retrieveRoleSub(eq(SUBMISSION_ID)))
                .thenReturn(buildTargetSubmission());

        ResultActions resultActions = this.mvc.perform(get("/role/submission/" + SUBMISSION_ID))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        TargetRoleSubmission res = objectMapper.readValue(contentAsString, TargetRoleSubmission.class);
        assertEquals(SUBMISSION_ID, res.getSubmissionId());
        assertEquals(CANDIDATE_ID, res.getCandidateId());
        assertEquals(ROLE_ID, res.getSubmittedRole().getId());
    }

    @Test
    void getRoleSubmission_notFound() throws Exception {
        when(service.retrieveRoleSub(eq(SUBMISSION_ID)))
                .thenThrow(NotFoundException.class);

        this.mvc.perform(get("/role/submission/" + SUBMISSION_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getRoleSubmission_internalServiceException() throws Exception {
        when(service.retrieveRoleSub(eq(SUBMISSION_ID)))
                .thenThrow(InternalServiceException.class);

        this.mvc.perform(get("/role/submission/" + SUBMISSION_ID))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getNewRoleRequests_happyPath() throws Exception {
        RoleSubmissions submissions = RoleSubmissions.builder()
                .roleSubmissions(List.of(buildTargetSubmission()))
                .build();
        when(service.retrieveRoleSubmissionsForBu(eq(BUSINESS_UNIT_ID)))
                .thenReturn(submissions);

        ResultActions resultActions = this.mvc.perform(get("/role/submissions")
                .queryParam("buId", BUSINESS_UNIT_ID.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        RoleSubmissions res = objectMapper.readValue(contentAsString, RoleSubmissions.class);
        assertEquals(SUBMISSION_ID, res.getRoleSubmissions().get(0).getSubmissionId());
        assertEquals(CANDIDATE_ID, res.getRoleSubmissions().get(0).getCandidateId());
        assertEquals(ROLE_ID, res.getRoleSubmissions().get(0).getSubmittedRole().getId());
    }

    @Test
    void getNewRoleRequests_internalServiceException() throws Exception {
        when(service.retrieveRoleSubmissionsForBu(eq(BUSINESS_UNIT_ID)))
                .thenThrow(InternalServiceException.class);

        this.mvc.perform(get("/role/submissions")
                .queryParam("buId", BUSINESS_UNIT_ID.toString()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }


    @Test
    void putRoleAsApproved_happyPath() throws Exception {

        this.mvc.perform(put("/role/submission/approve/" + SUBMISSION_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(service, times(1)).removeRoleSubmission(eq(SUBMISSION_ID));
    }

    @Test
    void putRoleAsApproved_internalServiceException() throws Exception {
        doThrow(new InternalServiceException("ex", new HibernateException("ex"))).when(service)
                .removeRoleSubmission(eq(SUBMISSION_ID));

        this.mvc.perform(put("/role/submission/approve/" + SUBMISSION_ID))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service, times(1)).removeRoleSubmission(eq(SUBMISSION_ID));
    }

    @Test
    void putRoleAsRejected_happyPath() throws Exception {

        this.mvc.perform(delete("/role/submission/reject/" + SUBMISSION_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(service, times(1)).removeRoleSubmission(eq(SUBMISSION_ID));
    }

    @Test
    void putRoleAsRejected_internalServiceException() throws Exception {
        doThrow(new InternalServiceException("ex", new HibernateException("ex"))).when(service)
                .removeRoleSubmission(eq(SUBMISSION_ID));

        this.mvc.perform(delete("/role/submission/reject/" + SUBMISSION_ID))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service, times(1)).removeRoleSubmission(eq(SUBMISSION_ID));
    }

    @Test
    void postNewRoleSub_internalServiceException() throws Exception {

        RoleCreateReq roleCreateReq = buildSubCreateReq();
        when(service.submitNewApplication(eq(roleCreateReq)))
                .thenReturn(buildTargetSubmission());


        this.mvc.perform(post("/role/submission/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                            "\"userId\":\"2\", " +
                            "\"role\": { " +
                                "\"roleName\":\"ROLE_NAME\"," +
                                "\"projectName\": \"PROJECT_NAME\", " +
                                "\"roleType\": \"BUSINESS_ANALYST\", " +
                                "\"startDate\": \""+TARGET_START_DATE+"\", " +
                                "\"endDate\": \""+ TARGET_END_DATE+"\", " +
                                "\"projectCode\": \"2\", " +
                                "\"accountNumber\": \"1\", " +
                                "\"accountName\": \"ACCOUNT_NAME\", " +
                                "\"description\":\"DESC\"," +
                                "\"baseLocation\": \"ASTON\", " +
                                "\"businessUnit\": " +BUSINESS_UNIT_ID +
                            "}, " +
                            "\"certainty\": \"100.00\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(service, times(1)).submitNewApplication(eq(roleCreateReq));
    }


//    @PostMapping(value = "/role/submission/submit")
//    @ResponseStatus(HttpStatus.CREATED)
//    public AbstractServiceResponse postNewRoleSub(@RequestBody RoleCreateReq roleCreateReq) {
//        return roleSubmissionService.submitNewApplication(roleCreateReq);
//    }
}