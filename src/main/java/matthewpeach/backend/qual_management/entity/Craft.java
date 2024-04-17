package matthewpeach.backend.qual_management.entity;

public class Craft {
    private Long id;
    private String name;

    public Craft(){

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
