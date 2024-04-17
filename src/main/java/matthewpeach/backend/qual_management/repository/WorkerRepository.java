package matthewpeach.backend.qual_management.repository;

import matthewpeach.backend.qual_management.entity.Worker;

import java.util.List;
import java.util.Map;

public interface WorkerRepository {
    public List<Worker> advancedSearch(Long workerId, String firstName, String lastName, String phone, String email, Long gradeId, Long craftId);
    public List<Worker> getAll();
    public Worker findById(Long id);
    // returns id
    public Long insert(Worker worker);
    public void deleteAll();
    public Integer getSize();
}
