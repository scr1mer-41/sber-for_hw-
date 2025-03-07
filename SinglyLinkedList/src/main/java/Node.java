import java.util.Objects;

public class Node {
    Object value;
    Node next;

    public Node(Object value) {
        this.value = value;
        this.next = null;
    }
}