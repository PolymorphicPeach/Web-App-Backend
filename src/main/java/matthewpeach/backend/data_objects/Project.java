package matthewpeach.backend.data_objects;

public class Project {
    private String name;
    private String skills;
    private String link;

    public Project(){
        // Blank for jackson library
    }

    public Project(String name, String skills, String link) {
        this.name = name;
        this.skills = skills;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
