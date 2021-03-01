package callum.project.uni.rms.submission;

import callum.project.uni.rms.submission.model.target.AbstractServiceResponse;
import callum.project.uni.rms.submission.model.request.RoleCreateReq;
import callum.project.uni.rms.submission.model.target.TargetRoleSubmission;
import callum.project.uni.rms.submission.service.RoleSubmissionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
class RoleSubmissionController {

    private final RoleSubmissionService roleSubmissionService;
    
    @GetMapping(value = "/role/submissions", params = "rmId")
    @ResponseStatus(HttpStatus.OK)
    public AbstractServiceResponse getNewRoleRequests(@RequestParam @NonNull Long rmId) {
        return roleSubmissionService.retrieveRoleSubmissionsForRM(rmId);
    }

    @GetMapping(value = "/role/submission/{subId}")
    @ResponseStatus(HttpStatus.OK)
    public AbstractServiceResponse getRoleSubmission(@PathVariable @NonNull Long subId){
        return roleSubmissionService.retrieveRoleSub(subId);
    }

    @PutMapping(value = "/role/submission/approve/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putRoleAsApproved(@PathVariable("subId") @NonNull Long subId) {
        log.debug("Remove the submission for this role");
        roleSubmissionService.removeRoleSubmission(subId);
    }

    @DeleteMapping(value = "/role/submission/reject/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoleSub(@PathVariable("subId") @NonNull Long subId) {
        roleSubmissionService.removeRoleSubmission(subId);
    }

    @PostMapping(value = "/role/submission/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public AbstractServiceResponse postNewRoleSub(@RequestBody @NonNull RoleCreateReq roleCreateReq) {
        return roleSubmissionService.submitNewApplication(roleCreateReq);
    }
}
