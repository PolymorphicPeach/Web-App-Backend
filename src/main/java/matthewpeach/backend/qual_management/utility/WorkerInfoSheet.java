package matthewpeach.backend.qual_management.utility;

import matthewpeach.backend.qual_management.entity.Worker;
import matthewpeach.backend.qual_management.entity.WorkerQualificationStatus;

import java.util.List;

public class WorkerInfoSheet {
    private Worker worker;
    private List<WorkerQualificationStatus> qualifications;

    public WorkerInfoSheet(Worker worker, List<WorkerQualificationStatus> qualifications){
        this.worker = worker;
        this.qualifications = qualifications;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<WorkerQualificationStatus> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<WorkerQualificationStatus> qualifications) {
        this.qualifications = qualifications;
    }
}
