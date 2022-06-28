public class HashTable<T extends Comparable<T>, K extends Comparable<K>> {

    public AVLTree[] slot;
    int numItems;

    // Constructor
    public HashTable() {
        slot = new AVLTree[100];
        for (int i = 0; i < 100; i++) {
            slot[i] = new AVLTree<T,K>();
        }
        this.numItems = 0;
    }

    // method
    private int hash(T msg) {
        String target_msg = msg.toString();
        char[] num = target_msg.toCharArray();
        int len = target_msg.length();
        int hash_result = 0;
        for (int j = 0; j < len; j++) {
            int ascii = num[j];
            hash_result += ascii;
        }
        return hash_result % 100;
    }

    public void insert(T msg, K item) {
        int slot_num = hash(msg);
        slot[slot_num].insert(msg, item);
        this.numItems++;
    }

    public AVLNode search(T msg){
        int slot_num = hash(msg);
        AVLTree target_tree = slot[slot_num];
        if (target_tree.root == null){
            return null;
        } else {
            return target_tree.search(msg);
        }
    }

    public AVLTree find_slot(int slot_num){
        if(0<=slot_num && slot_num<100){
            return slot[slot_num];
        } else{
            return null;
        }
    }


}
