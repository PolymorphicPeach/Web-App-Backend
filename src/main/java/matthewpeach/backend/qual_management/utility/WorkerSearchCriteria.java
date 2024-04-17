package matthewpeach.backend.qual_management.utility;

public class WorkerSearchCriteria {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Long craftId;
    private Long gradeId;

    public WorkerSearchCriteria(){

    }

    public WorkerSearchCriteria(Long id, String firstName, String lastName, String phone, String email, Long craftId, Long gradeId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.craftId = craftId;
        this.gradeId = gradeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCraftId() {
        return craftId;
    }

    public void setCraftId(Long craftId) {
        this.craftId = craftId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }
}
