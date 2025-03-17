public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<String> arr = new SinglyLinkedList<String>();

        arr.add("a");
        arr.add("b");
        arr.add("c");

        arr.delete(1);

        System.out.println(arr.get(1));

        System.out.println(arr.getSize());

        arr.print();
    }
}
