import junit.framework.TestCase;

public class SinglyLinkedListTest extends TestCase {

    public void testAdd() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.add(1);
        assertEquals("1", list.get(1).toString());
    }

    public void testGetSize() {
        SinglyLinkedList list = new SinglyLinkedList();
        assertEquals(0, list.getSize());
        list.add(1);
        assertEquals(1, list.getSize());
    }

    public void testIsEmpty() {
        SinglyLinkedList list = new SinglyLinkedList();
        assertTrue(list.isEmpty());
        list.add(1);
        assertFalse(list.isEmpty());
    }


    public void testGet() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.add("1");
        assertTrue(list.get(1) instanceof String);
        assertEquals("1", list.get(1));
        list.add(2);
        assertTrue(list.get(2) instanceof Integer);
        assertEquals(2, list.get(2));
    }

    public void testClear() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.add(1);
        assertEquals(1, list.get(1));
        assertEquals(1, list.getSize());
        list.add(2);
        assertEquals(2, list.get(2));
        assertEquals(2, list.getSize());
        list.clear();
        assertTrue(list.get(1) instanceof NullObject);
        assertEquals(0,list.getSize());

    }

    public void testDelete() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(2, list.get(2));
        list.delete(2);
        assertEquals(3, list.get(2));
        assertEquals(1, list.get(1));
        list.delete(1);
        assertEquals(3, list.get(1));
    }
}