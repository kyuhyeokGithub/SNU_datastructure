public class MyLinkedList<T extends Comparable<T>> {
    // dummy head
    Node<T> head;
    int numItems;

    public MyLinkedList() {
        head = new Node<>();
        numItems = 0;
    }

    public void add(int index, T item) {
        if (index >= 0 && index <= numItems) {
            Node<T> prevNode = getNode(index - 1);
            Node<T> newNode = new Node(item, prevNode.getNext());
            prevNode.setNext(newNode);
            numItems++;
        }
    }

    public void append(T item) {
        Node<T> prevNode = head;
        while (prevNode.getNext() != null) {
            prevNode = prevNode.getNext();
        }
        Node<T> newNode = new Node(item);
        prevNode.setNext(newNode);
        numItems++;
    }

    public T remove(int index) {
        if (index >= 0 && index <= numItems - 1) {
            Node<T> prevNode = getNode(index - 1);
            Node<T> currNode = prevNode.getNext();
            prevNode.setNext(currNode.getNext());
            numItems--;
            return currNode.getItem();
        } else return null; // 에러
    }

    public boolean removeItem(T item) {

        Node<T> currNode = head;
        for (int j = 0; j <= numItems - 1; j++) {
            Node<T> prevNode = currNode;
            currNode = prevNode.getNext();
            if (currNode.getItem().compareTo(item) == 0) {
                prevNode.setNext(currNode.getNext());
                numItems--;
                return true;
            }
        }
        return false;
    }

    public T get(int index) {
        if (index >= 0 && index <= numItems - 1) {
            return getNode(index).getItem();
        } else return null; // 에러
    }

    public void set(int index, T item) {
        if (index >= 0 && index <= numItems - 1) {
            getNode(index).setItem(item);
        }
    }

    private Node<T> getNode(int index) {
        if (index >= -1 && index <= numItems - 1) {
            Node currNode = head;
            for (int i = 0; i <= index; i++) {
                currNode = currNode.getNext();
            }
            return currNode;
        } else return null;
    }

    private final int NOT_FOUND = -1;

    public int indexOf(T item) {

        Node<T> currNode = head;
        for (int j = 0; j <= numItems - 1; j++) {
            currNode = currNode.getNext();
            if (currNode.getItem().compareTo(item) == 0) {
                return j;
            }
        }
        return NOT_FOUND;
    }

    public boolean isEmpty() {
        return numItems == 0;
    }

    public int len() {
        return numItems;
    }

    public void clear() {
        numItems = 0;
        head = new Node<>();
    }
}
