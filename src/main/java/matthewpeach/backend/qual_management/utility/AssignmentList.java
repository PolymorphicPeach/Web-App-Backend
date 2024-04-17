package matthewpeach.backend.qual_management.utility;

import java.util.List;

public class AssignmentList {
    private List<Long> workerIds;
    private List<Long> qualificationIds;

    public AssignmentList(){

    }

    public List<Long> getWorkerIds() {
        return workerIds;
    }

    public void setWorkerIds(List<Long> workerIds) {
        this.workerIds = workerIds;
    }

    public List<Long> getQualificationIds() {
        return qualificationIds;
    }

    public void setQualificationIds(List<Long> qualificationIds) {
        this.qualificationIds = qualificationIds;
    }
}
