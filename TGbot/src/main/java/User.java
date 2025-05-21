import java.util.Objects;

public class User {
    private Integer id;
    private Long chatID;
    private Integer status;
    // 0 - id редактируемой записи в бд notes

    private Integer age;
    private String Sex;
    private Float height;
    private Float weight;
    private Integer activity_factor;




    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        this.Sex = sex;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getActivity_factor() {
        return activity_factor;
    }

    public void setActivity_factor(Integer activity_factor) {
        this.activity_factor = activity_factor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(chatID, user.chatID) && Objects.equals(Sex, user.Sex) && Objects.equals(height, user.height) && Objects.equals(weight, user.weight) && Objects.equals(activity_factor, user.activity_factor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatID, Sex, height, weight, activity_factor);
    }


}
