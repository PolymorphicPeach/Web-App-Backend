package matthewpeach.backend.qual_management.entity;

public class Qualification {
    private Long id;
    private String name;
    private Long daysUntilExpiration;
    private Long craftId;

    public Qualification(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDaysUntilExpiration() {
        return daysUntilExpiration;
    }

    public void setDaysUntilExpiration(Long daysUntilExpiration) {
        this.daysUntilExpiration = daysUntilExpiration;
    }

    public Long getCraftId() {
        return craftId;
    }

    public void setCraftId(Long craftId) {
        this.craftId = craftId;
    }
}
