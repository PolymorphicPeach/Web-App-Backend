package matthewpeach.backend.data_objects;

public class SkillCount {
    private String skill;
    private int count;

    public SkillCount(){

    }

    public SkillCount(String skill, int count) {
        this.skill = skill;
        this.count = count;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
