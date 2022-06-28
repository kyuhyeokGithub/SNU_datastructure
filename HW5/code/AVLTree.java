public class AVLTree<T extends Comparable<T>, K extends Comparable<K>>{
    public AVLNode<T,K> root;

    private final int LL = 1, LR = 2, RR = 3, RL = 4, NO_NEED = 0, ILLEGAL = -1;

    public AVLNode<T,K> NIL = new AVLNode(null,null,null,0);

    // Constructor
    public AVLTree() {
        root = NIL;
    }

    // Search
    public AVLNode<T,K> search(T msg) {
        return searchMSG(root, msg);
    }

    public AVLNode<T,K> searchMSG(AVLNode<T,K> tNode, T msg) {
        if (tNode==NIL) {
            return null;
        } else if (msg.compareTo(tNode.msg) == 0) {
            return tNode;
        } else if (msg.compareTo(tNode.msg) < 0) {
            return searchMSG(tNode.left, msg);
        } else if (msg.compareTo(tNode.msg) > 0) {
            return searchMSG(tNode.right, msg);
        }
        return NIL;
    }

    // Insert
    public void insert(T msg, K item) {
        root = insertItem(root, msg, item);
    }

    public AVLNode<T,K> insertItem(AVLNode<T,K> tNode, T msg, K item) {
        int type;
        if (tNode == NIL) {
            tNode = new AVLNode<>(msg,NIL,NIL);
            tNode.indexList.append(item);
        } else if (msg.compareTo(tNode.msg) < 0) {
            tNode.left = insertItem(tNode.left, msg, item);
            tNode.height = 1 + Math.max(tNode.right.height, tNode.left.height);
            type = needBalance(tNode);
            if (type != NO_NEED) {
                tNode = balanceAVL(tNode, type);
            }
        } else if (msg.compareTo(tNode.msg) > 0) {
            tNode.right = insertItem(tNode.right, msg, item);
            tNode.height = 1 + Math.max(tNode.right.height, tNode.left.height);
            type = needBalance(tNode);
            if (type != NO_NEED) {
                tNode = balanceAVL(tNode, type);
            }
        } else if (msg.compareTo(tNode.msg) == 0) {
            tNode.indexList.append(item);
        }

        return tNode;
    }

    // Balancing 작업
    private int needBalance(AVLNode<T,K> t) {
        int type = ILLEGAL;
        if (t.left.height + 2 <= t.right.height) {
            if (t.right.left.height <= t.right.right.height) {
                type = RR;
            } else {
                type = RL;
            }
        } else if (t.right.height + 2 <= t.left.height) {
            if (t.left.right.height <= t.left.left.height) {
                type = LL;
            } else {
                type = LR;
            }
        } else {
            type = NO_NEED;
        }
        return type;
    }

    private AVLNode<T,K> balanceAVL(AVLNode<T,K> tNode, int type) {
        AVLNode<T,K> returnNode = NIL;
        switch (type) {
            case LL:
                returnNode = rightRotate(tNode);
                break;
            case LR:
                tNode.left = leftRotate(tNode.left);
                returnNode = rightRotate(tNode);
                break;
            case RR:
                returnNode = leftRotate(tNode);
                break;
            case RL:
                tNode.right = rightRotate(tNode.right);
                returnNode = leftRotate(tNode);
                break;
            default:
                break;
        }
        return returnNode;
    }

    private AVLNode<T,K> leftRotate(AVLNode<T,K> t) {
        AVLNode<T,K> Rchild = t.right;
        AVLNode<T,K> RLchild = Rchild.left;
        Rchild.left = t;
        t.right = RLchild;
        t.height = 1 + Math.max(t.left.height, t.right.height);
        Rchild.height = 1 + Math.max(Rchild.left.height, Rchild.right.height);
        return Rchild;
    }

    private AVLNode<T,K> rightRotate(AVLNode<T,K> t) {
        AVLNode<T,K> Lchild = t.left;
        AVLNode<T,K> LRchild = Lchild.right;
        Lchild.right = t;
        t.left = LRchild;
        t.height = 1 + Math.max(t.left.height, t.right.height);
        Lchild.height = 1 + Math.max(Lchild.left.height, Lchild.right.height);
        return Lchild;
    }

    public StringBuilder printall(AVLNode<T,K> tNode, StringBuilder strbuilder) {
        if (tNode != NIL) {
            strbuilder.append(" ").append(tNode.msg);
            printall(tNode.left, strbuilder);
            printall(tNode.right, strbuilder);
        }
        return strbuilder;
    }
}
