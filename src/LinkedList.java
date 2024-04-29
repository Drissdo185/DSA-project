public class LinkedList {
    Node head;

    public LinkedList() {
        this.head = null;
    }

    public void addAccount(Account account) {
        Node newNode = new Node(account);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
    
}
