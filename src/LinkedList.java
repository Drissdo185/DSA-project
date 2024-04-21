public class LinkedList {
    private Node head;

    public LinkedList() {
        head = null;
    }

    public void addAccount(Account data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }
    
}
