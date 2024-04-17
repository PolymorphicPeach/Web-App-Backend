package matthewpeach.backend.qual_management.entity;

import java.time.LocalDate;
import java.util.Objects;

public class WorkerQualificationStatus implements Comparable<WorkerQualificationStatus> {
    private Long id;
    private Long qualificationId;
    private Long workerId;
    private LocalDate assignedOn;
    private LocalDate completedOn;
    private LocalDate expiration;

    public WorkerQualificationStatus(){

    }

    public WorkerQualificationStatus(Long workerId, Long qualificationId, LocalDate assignedOn){
        this.workerId = workerId;
        this.qualificationId = qualificationId;
        this.assignedOn = assignedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Long qualificationId) {
        this.qualificationId = qualificationId;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public LocalDate getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(LocalDate assignedOn) {
        this.assignedOn = assignedOn;
    }

    public LocalDate getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDate completedOn) {
        this.completedOn = completedOn;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    @Override
    public int compareTo(WorkerQualificationStatus o) {
        // Handles null expiration dates by pushing them to the end
        if (this.expiration == null) return 1;
        if (o.expiration == null) return -1;

        int comparison = this.expiration.compareTo(o.expiration);
        if (comparison != 0) {
            return comparison;
        }

        return this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof WorkerQualificationStatus)){
            return false;
        }

        return Objects.equals(id, ((WorkerQualificationStatus) o).getId());
    }

}
