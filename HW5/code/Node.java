// Linked List의 Node로 사용되는 부분
public class Node<T extends Comparable<T>> {
    private T item;
    private Node<T> next;

    // Constructor 코드
    public Node() {
        this.item = null;
        this.next = null;
    }

    public Node(T s) {
        this.item = s;
        this.next = null;
    }

    public Node(T s, Node<T> N) {
        this.item = s;
        this.next = N;
    }

    public final T getItem() {
        return item;
    }

    public final void setItem(T s) {
        this.item = s;
    }

    public final void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public final void insertNext(T s) {
        Node<T> insert_target = new Node<>(s, this.next);
        this.setNext(insert_target);
    }

    public final void removeNext() {
        Node<T> remove_target = this.next;
        this.setNext(remove_target.next);
    }
}
