package demo;

import com.serializer.JsonField;
import com.serializer.JsonSerializer;
import com.serializer.JsonSerializationException;

import java.util.List;

class Person {
    @JsonField
    private String name;

    @JsonField(name = "age_years")
    private int age;

    @JsonField
    private List<String> hobbies;

    private String password;

    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
        this.password = "secret";
    }
}

public class App {
    public static void main(String[] args) {
        Person person = new Person("Alice", 30, List.of("Reading", "Hiking"));
        JsonSerializer serializer = new JsonSerializer();

        try {
            String json = serializer.serialize(person);
            System.out.println(json);
            // Вывод: {"name":"Alice", "age_years":30, "hobbies":["Reading", "Hiking"]}
        } catch (JsonSerializationException e) {
            System.err.println("Serialization failed: " + e.getMessage());
        }
    }
}