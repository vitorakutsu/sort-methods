package Structure.LinkedList;

public class Node {
    private int info;
    private Node next;
    private Node prev;

    public Node(int info) {
        this.next = null;
        this.prev = null;
        this.info = info;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
