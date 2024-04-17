package matthewpeach.backend.qual_management;

import matthewpeach.backend.qual_management.entity.Qualification;
import matthewpeach.backend.qual_management.entity.Worker;
import matthewpeach.backend.qual_management.entity.WorkerQualificationStatus;
import matthewpeach.backend.qual_management.repository.IDRepository;
import matthewpeach.backend.qual_management.repository.QualificationRepository;
import matthewpeach.backend.qual_management.repository.WorkerQualificationStatusRepository;
import matthewpeach.backend.qual_management.repository.WorkerRepository;
import matthewpeach.backend.qual_management.utility.AssignmentList;
import matthewpeach.backend.qual_management.utility.WorkerInfoSheet;
import matthewpeach.backend.qual_management.utility.WorkerSearchCriteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EntityManager {
    final private WorkerRepository workerRepository;
    final private WorkerQualificationStatusRepository qualificationStatusRepository;
    final private QualificationRepository qualificationRepository;
    final private IDRepository idRepository;
    final private Random random;
    final private String[] firstNames = {"John", "Jane", "Michael", "Emily", "Christopher", "Amanda", "Robert", "Jessica", "David", "Sarah"};
    final private String[] lastNames = {"Doe", "Smith", "Johnson", "Brown", "Jones", "Davis", "Wilson", "Taylor", "Martinez", "Anderson"};
    final private String[] domains = {"example.com", "mail.com", "test.org", "sample.net"};

    public EntityManager(WorkerRepository workerRepository,
                         WorkerQualificationStatusRepository qualificationStatusRepository,
                         QualificationRepository qualificationRepository,
                         IDRepository idRepository)
    {
        this.workerRepository = workerRepository;
        this.qualificationRepository = qualificationRepository;
        this.qualificationStatusRepository = qualificationStatusRepository;
        this.idRepository = idRepository;
        this.random = new Random();
    }

    public List<Worker> getAllWorkers(){
        return workerRepository.getAll();
    }

    public Worker getWorkerById(Long id){
        return workerRepository.findById(id);
    }

    public List<Qualification> getAllQualifications(){
        return qualificationRepository.getAll();
    }

    public Integer getWorkerCount(){
        return workerRepository.getSize();
    }

    public Integer getQualificationStatusCount(){
        return qualificationStatusRepository.getSize();
    }

    public void assignTraining(Long workerId, List<Long> qualificationIds){
        Worker worker = workerRepository.findById(workerId);
        List<WorkerQualificationStatus> currentQuals = qualificationStatusRepository.findByWorkerId(workerId);
        List<Long> currentQualIds = currentQuals.stream().map(qual -> qual.getId()).collect(Collectors.toList());

        for(Long qualId : qualificationIds){
            if(currentQualIds.contains(qualId)){
                // Update existing record
                qualificationStatusRepository.assign(workerId, qualId, LocalDate.now());
            }
            else{
                // Create new record
                qualificationStatusRepository.insert(new WorkerQualificationStatus(workerId, qualId, LocalDate.now()));
            }
        }
    }

    public void assignAll(AssignmentList assignmentList){
        for(Long workerId : assignmentList.getWorkerIds()){
            assignTraining(workerId, assignmentList.getQualificationIds());
        }
    }

    public List<Worker> advancedWorkerSearch(WorkerSearchCriteria searchCriteria){
        return workerRepository.advancedSearch(
                searchCriteria.getId(),
                searchCriteria.getFirstName(),
                searchCriteria.getLastName(),
                searchCriteria.getPhone(),
                searchCriteria.getEmail(),
                searchCriteria.getGradeId(),
                searchCriteria.getCraftId());
    }

    public void populateWithRandomWorkers(int numWorkers){
        for(int i = 0; i < numWorkers; ++i){
            Worker worker = createRandomWorker();
            Long workerId = workerRepository.insert(worker);
            assignRandomQualifications(workerId);
        }
    }

    private void assignRandomQualifications(Long workerId){
        Worker worker = workerRepository.findById(workerId);
        Long craftId = worker.getCraftId();
        List<Qualification> qualifications = qualificationRepository.findByCraftId(null);

        if(craftId != null){
            qualifications.addAll(qualificationRepository.findByCraftId(craftId));
        }

        for(Qualification qualification : qualifications){
            if(random.nextBoolean()){
                LocalDate assignedOn = getRandomPastDate(1L);
                // Randomly completed
                LocalDate completedOn = random.nextBoolean() ? generateRandomCompletionDate(assignedOn) : null;

                WorkerQualificationStatus status = new WorkerQualificationStatus();
                status.setWorkerId(workerId);
                status.setQualificationId(qualification.getId());
                status.setAssignedOn(assignedOn);
                status.setCompletedOn(completedOn);

                if(completedOn != null && qualification.getDaysUntilExpiration() != null){
                    status.setExpiration(completedOn.plusDays(qualification.getDaysUntilExpiration()));
                }
                qualificationStatusRepository.insert(status);
            }
        }

    }

    private LocalDate getRandomPastDate(Long years){
        if(years < 1){
            years = 1L;
        }
        return LocalDate.now().minusDays(365L * years);
    }

    private LocalDate generateRandomCompletionDate(LocalDate assignedOn){
        return assignedOn.plusDays(1 + random.nextInt(250));
    }

    public void addDemoWorker(Worker worker){
        if(worker.getFirstName() == null || worker.getFirstName().isEmpty()){
            worker.setFirstName(firstNames[random.nextInt(firstNames.length)]);
        }

        if(worker.getLastName() == null || worker.getLastName().isEmpty()){
            worker.setLastName(lastNames[random.nextInt(lastNames.length)]);
        }

        if(worker.getCraftId() == null){
            worker.setCraftId( (long) random.nextInt(4) + 1);
        }

        if(worker.getGradeId() == null){
            worker.setGradeId( (long) random.nextInt(4) + 1);
        }

        String email = worker.getFirstName().toLowerCase() + "." + worker.getLastName().toLowerCase() + "@" + domains[random.nextInt(domains.length)];
        String phone = String.format("%03d-%03d-%04d", random.nextInt(1000), random.nextInt(1000), random.nextInt(10000));
        worker.setEmail(email);
        worker.setPhone(phone);
        workerRepository.insert(worker);
    }

    private Worker createRandomWorker(){
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String phone = String.format("%03d-%03d-%04d", random.nextInt(1000), random.nextInt(1000), random.nextInt(10000));
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + domains[random.nextInt(domains.length)];
        int craftId = random.nextInt(idRepository.getCrafts().size()) + 1;
        int gradeId = random.nextInt(4) + 1;

        return new Worker(firstName, lastName, phone, email, (long) craftId, (long) gradeId);
    }

    public void deleteAllWorkers(){
        this.qualificationStatusRepository.deleteAll();
        this.workerRepository.deleteAll();
    }

    public WorkerInfoSheet getWorkerInfoSheet(Worker worker){
        return new WorkerInfoSheet(
                worker,
                qualificationStatusRepository.findByWorkerId(worker.getId())
        );
    }

    public Map<String, Long> getCraftMappings(){
        return idRepository.getCrafts();
    }

    public Map<String, Long> getGradeMappings(){
        return idRepository.getGrades();
    }

    public Map<String, Long> getQualificationMappings(){
        return idRepository.getQualifications();
    }
}
