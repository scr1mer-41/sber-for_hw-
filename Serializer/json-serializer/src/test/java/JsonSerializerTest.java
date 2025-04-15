import com.serializer.JsonField;
import com.serializer.JsonSerializationException;
import com.serializer.JsonSerializer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {
    private final JsonSerializer serializer = new JsonSerializer();

    @Test
    void serialize_SimpleObject_ReturnsValidJson() throws JsonSerializationException {
        class TestClass {
            @JsonField
            private String name = "test";

            @JsonField
            private int value = 42;
        }

        String json = serializer.serialize(new TestClass());
        System.out.print(json);
        assertEquals("{\"name\": \"test\", \"value\": 42}", json);
    }

    @Test
    void serialize_AllPrimitiveTypes_ReturnsValidJson() throws JsonSerializationException {
        class Primitives {
            @JsonField int intValue = 10;
            @JsonField long longValue = 100L;
            @JsonField double doubleValue = 3.14;
            @JsonField boolean boolValue = true;
            @JsonField char charValue = 'A';
        }

        String json = serializer.serialize(new Primitives());
        assertTrue(json.contains("\"intValue\": 10"));
        assertTrue(json.contains("\"longValue\": 100"));
        assertTrue(json.contains("\"doubleValue\": 3.14"));
        assertTrue(json.contains("\"boolValue\": true"));
        assertTrue(json.contains("\"charValue\": A"));
    }

    @Test
    void serialize_WrapperTypes_ReturnsValidJson() throws JsonSerializationException {
        class Wrappers {
            @JsonField Integer intObj = 20;
            @JsonField Double doubleObj = 2.71;
            @JsonField Boolean boolObj = false;
        }

        String json = serializer.serialize(new Wrappers());
        assertTrue(json.contains("\"intObj\": 20"));
        assertTrue(json.contains("\"doubleObj\": 2.71"));
        assertTrue(json.contains("\"boolObj\": false"));
    }


}