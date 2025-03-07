public class SinglyLinkedList {
    private Node root = null;
    private int size = 0;

    public SinglyLinkedList() {}

    public void add(Object item) {
        Node newNode = new Node(item);
        if (root == null) {
            root = newNode;
        } else {
            Node lastNode = lastNode();
            lastNode.next = newNode;
        }
        this.size += 1;
    }

    private Node lastNode() {
        Node temp = root;
        while (temp.next != null) {
            temp = temp.next;
        }
        return temp;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void print() {
        if (root != null) {
            String str = "[";
            Node temp = root;
            while (temp.next != null) {
                str += temp.value.toString() + ", ";
                temp = temp.next;
            }
            str += temp.value.toString() + "]";
            System.out.print(str + "\n");
        } else {
            System.out.print("[]" + "\n");
        }
    }

    public Object get(int index) {
        if (root == null) {
            return new NullObject();
        } else if ((index < 1) | (index > (this.size))) {
            return new NullObject();
        } else {
            int cnt = 1;
            Node temp = root;
            while (cnt != index) {
                cnt += 1;
                temp = temp.next;
            }
            return temp.value;
        }
    }

    public void clear() {
        if (root != null) {
            Node temp = root;
            while (root != null) {
                temp = root.next;
                root.next = null;
                root = temp;
            }
            this.size = 0;
        }
    }

    public void delete(int index) {
        if (root == null) {
            System.out.print("Список пуст!");
        } else if ((index < 1) | (index > size)) {
            System.out.print("Ошибочный индекс!");
        } else {
            if (index == 1) {
                Node temp = root;
                root = root.next;
                temp.next = null;
                temp = null;
                this.size -= 1;
            } else {
                int cnt = 1;
                Node temp = root;
                Node previous = temp;
                while (cnt != index) {
                    cnt += 1;
                    previous = temp;
                    temp = temp.next;
                }
                previous.next = temp.next;
                temp.next = null;
                temp = null;
                this.size -= 1;

            }
        }

    }

}
