package matthewpeach.backend.qual_management.repository;

import matthewpeach.backend.qual_management.entity.WorkerQualificationStatus;

import java.time.LocalDate;
import java.util.List;

public interface WorkerQualificationStatusRepository {
    public void deleteAll();
    public void insert(WorkerQualificationStatus workerQualificationStatus);
    public void update(Long id, LocalDate completedOn, LocalDate expiration);
    public void update(Long id, LocalDate assignedOn);
    public void assign(Long workerId, Long qualificationId, LocalDate assignedOn);
    public List<WorkerQualificationStatus> findByWorkerId(Long workerId);
    public WorkerQualificationStatus findByWorkerIdAndQualificationId(Long workerId, Long qualificationId);
    public Integer getSize();
}
