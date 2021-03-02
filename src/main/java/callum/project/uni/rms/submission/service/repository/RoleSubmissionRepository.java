package callum.project.uni.rms.submission.service.repository;

import callum.project.uni.rms.submission.model.source.RoleSubmission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleSubmissionRepository extends CrudRepository<RoleSubmission, Long> {


    @Query
    public List<RoleSubmission> findAllByBusinessUnitId(Long buId);
}
