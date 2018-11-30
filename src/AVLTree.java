import java.util.ArrayList;

/**
 * @author Li Ersan
 */
public class AVLTree<K extends Comparable<K>, V> {

    private class Node {

        K key;

        V value;

        Node left, right;

        /**
         * 结点高度值
         */
        int height;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root;

    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    /**
     * 获得结点的高度
     *
     * @param node
     * @return
     */
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    /**
     * 获得结点node的平衡因子
     *
     * @param node
     * @return
     */
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * 对结点y进行向右旋转操作，返回旋转后新的根结点x
     * //                   y                                        x
     * //                 /   \                                    /   \
     * //                x     T4       向右旋转（y）             z      y
     * //              /   \            - - - - - - >           /  \   /  \
     * //             z     T3                                 T1  T2 T3  T4
     * //           /   \
     * //          T1    T2
     *
     * @param y
     * @return
     */
    private Node rightRotate(Node y) {
        Node x = y.left;

        Node T3 = x.right;

        //向右旋转过程
        x.right = y;
        y.left = T3;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    /**
     * 对结点y进行向左旋转操作，返回旋转后新的根结点x
     * //               y                                        x
     * //             /   \                                    /   \
     * //            T1    x       向左旋转（y）              y      z
     * //                /   \     - - - - - - >            /  \   /  \
     * //               T2    z                            T1  T2 T3  T4
     * //                    /  \
     * //                   T3   T4
     *
     * @param y
     * @return
     */
    private Node leftRotate(Node y) {
        Node x = y.right;

        Node T2 = x.left;

        //向左旋转过程
        x.left = y;
        y.right = T2;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    /**
     * 向二分搜索树中添加新的元素e
     *
     * @param key
     * @param value
     */
    public void add(K key, V value) {
        root = add(root, key, value);
    }


    /**
     * 向以node为根的二分搜索树中插入元素(key, value)，递归算法
     * 返回插入新结点后二分搜索树的根
     *
     * @param node
     * @param key
     * @param value
     * @return
     */
    private Node add(Node node, K key, V value) {

        if (node == null) {
            size++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);

        } else {
            // key.compareTo(node.key) == 0
            node.value = value;
        }

        //更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
//        if (Math.abs(balanceFactor) > 1) {
//            System.out.println("不是一个平衡二叉树：" + balanceFactor);
//        }

        //平衡维护
        //LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        //RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        //LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * 返回以node为根结点的二分搜索树中，key所在的结点
     *
     * @param node
     * @param key
     * @return
     */
    private Node getNode(Node node, K key) {

        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else { // if key.compareTo(node.key) > 0
            return getNode(node.right, key);
        }
    }

    /**
     * 返回以node为根的二分搜索树的最小键值所在的结点
     *
     * @param node
     */
    private Node minimum(Node node) {

        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    /**
     * 从二分搜索树中删除键值为key的结点
     *
     * @param key
     * @return
     */
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }

        return null;
    }

    /**
     * 删除掉以node为根的二分搜索树中键为key的结点，递归算法
     *
     * @param node
     * @param key
     * @return 返回删除结点后新的二分搜索树的根
     */
    private Node remove(Node node, K key) {

        if (node == null) {
            return null;
        }

        Node retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {//key.compareTo(node.key) == 0

            //待删除结点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            } else if (node.right == null) { //待删除结点右子树为空的情况
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            } else {
                /*待删除结点左右子树为空的情况
                找到比待删除结点大的最小结点，即待删除结点右子树的最小结点
                用这个结点顶替待删除结点的位置
                */
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = node.right = null;

                retNode = successor;
            }
        }

        if (retNode == null) {
            return null;
        }

        //更新height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        //计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        //平衡维护
        //LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {
            return rightRotate(retNode);
        }

        //RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {
            return leftRotate(retNode);
        }

        //LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
    }


    public boolean contains(K key) {
        return getNode(root, key) != null;
    }


    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }


    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + " 不存在！");
        }

        node.value = newValue;
    }


    public int getSize() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断该二叉树是否是一棵二分搜索树
     *
     * @return
     */
    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);

        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }

        return true;
    }

    private void inOrder(Node node, ArrayList<K> keys) {
        if (node == null) {
            return;
        }

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    /**
     * 判断该二叉树是否是一棵平衡二叉树
     *
     * @return
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    /**
     * 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
     *
     * @param node
     * @return
     */
    private boolean isBalanced(Node node) {

        if (node == null) {
            return true;
        }

        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }

        return isBalanced(node.left) && isBalanced(node.right);
    }

    public static void main(String[] args) {

        System.out.println("傲慢与偏见");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("共有单词数：" + words.size());

            AVLTree<String, Integer> avlTree = new AVLTree<>();
            for (String word : words) {
                if (avlTree.contains(word)) {
                    avlTree.set(word, avlTree.get(word) + 1);
                } else {
                    avlTree.add(word, 1);
                }
            }

            System.out.println("共有不同单词数：" + avlTree.getSize());
            System.out.println("出现pride的次数: " + avlTree.get("pride"));
            System.out.println("出现prejudice的次数: " + avlTree.get("prejudice"));

            System.out.println("is BST: " + avlTree.isBST());
            System.out.println("is Balance: " + avlTree.isBalanced());

            for (String word : words) {
                avlTree.remove(word);
                if (!avlTree.isBST() || !avlTree.isBalanced()) {
                    throw new RuntimeException("Error");
                }
            }
        }

        System.out.println();
    }
}
