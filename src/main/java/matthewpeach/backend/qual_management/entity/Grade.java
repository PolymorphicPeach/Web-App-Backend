package matthewpeach.backend.qual_management.entity;

public class Grade {
    private Long id;
    private String name;

    public Grade(){

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
}
