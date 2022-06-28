public class AVLNode<T extends Comparable<T>, K extends Comparable<K>> {
    public T msg; // 길이가 6인 String
    public MyLinkedList<K> indexList; // 같은 msg를 갖는 것들에 대해 관리하기 위함
    public AVLNode left, right;
    public int height;


    // Constructor
    public AVLNode(T msg, AVLNode<T,K> lChild, AVLNode<T,K> rChild) {
        this.msg = msg;
        this.indexList = new MyLinkedList<>();
        this.left = lChild;
        this.right = rChild;
        this.height = 1;
    }

    public AVLNode(T msg, AVLNode<T,K> lChild, AVLNode<T,K> rChild, int h) {
        this.msg = msg;
        this.indexList = new MyLinkedList<>();
        this.left = lChild;
        this.right = rChild;
        this.height = h;
    }
}
