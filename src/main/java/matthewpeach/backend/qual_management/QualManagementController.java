package matthewpeach.backend.qual_management;

import matthewpeach.backend.qual_management.entity.Qualification;
import matthewpeach.backend.qual_management.entity.Worker;
import matthewpeach.backend.qual_management.repository.WorkerRepository;
import matthewpeach.backend.qual_management.utility.AssignmentList;
import matthewpeach.backend.qual_management.utility.WorkerInfoSheet;
import matthewpeach.backend.qual_management.utility.WorkerSearchCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qualmanagement")
@CrossOrigin
public class QualManagementController {
    final private EntityManager entityManager;
    final private int workerLimit = 100;
    final private int workerQualificationStatusLimit = 1000;

    public QualManagementController(EntityManager entityManager){
        this.entityManager = entityManager;
        entityManager.populateWithRandomWorkers(50);
    }

    @GetMapping("/workers")
    public List<Worker> getAllWorker(){
        return entityManager.getAllWorkers();
    }

    @GetMapping("/workers/count")
    public Integer getWorkerCount(){
        return entityManager.getWorkerCount();
    }

    @PostMapping("/workers/populateRandom")
    public void populateRandom(){
        if(entityManager.getWorkerCount() + 20 <= workerLimit){
            entityManager.populateWithRandomWorkers(20);
        }
    }

    @PostMapping("/workers/deleteAll")
    public void deleteAllWorkers(){
        this.entityManager.deleteAllWorkers();
    }

    @GetMapping("/workers/{id}")
    public Worker getAllWorker(@PathVariable Long id){
        return entityManager.getWorkerById(id);
    }

    @PostMapping("/workers/addDemo")
    public void addDemoWorker(@RequestBody Worker worker){
        if(entityManager.getWorkerCount() + 20 <= workerLimit){
            entityManager.addDemoWorker(worker);
        }
    }

    @PostMapping("workers/search")
    public List<Worker> advancedWorkerSearch(@RequestBody WorkerSearchCriteria workerSearchCriteria){
        return entityManager.advancedWorkerSearch(workerSearchCriteria);
    }

    @GetMapping("/qualifications")
    public List<Qualification> getAllQualifications(){
        return entityManager.getAllQualifications();
    }

    @PostMapping("/workers/info")
    public WorkerInfoSheet getWorkerInfoSheet(@RequestBody Worker worker){
        return entityManager.getWorkerInfoSheet(worker);
    }

    @GetMapping("/mappings")
    public Map<String, Map<String, Long>> getMappings(){
        Map<String, Map<String, Long>> mappings = new HashMap<>();
        mappings.put("craft", entityManager.getCraftMappings());
        mappings.put("grade", entityManager.getGradeMappings());
        mappings.put("qualification", entityManager.getQualificationMappings());
        return mappings;
    }

    @PostMapping("/training/assign")
    public void assignTraining(@RequestBody AssignmentList assignmentList){
        if(assignmentList.getWorkerIds() == null){
            return;
        }

        if(assignmentList.getQualificationIds() == null){
            return;
        }

        int newRecords = assignmentList.getWorkerIds().size() * assignmentList.getQualificationIds().size();
        int existingRecords = entityManager.getQualificationStatusCount();

        if(existingRecords + newRecords <= workerQualificationStatusLimit){
            entityManager.assignAll(assignmentList);
        }
    }

    @GetMapping("/quals/count")
    public Integer getQualificationStatusCount(){
        return entityManager.getQualificationStatusCount();
    }
}
