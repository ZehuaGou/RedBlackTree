import java.util.ArrayList;

/**
 * @author Li Ersan
 */
public class BST<K extends Comparable<K>, V> {

    private class Node {

        K key;

        V value;

        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;

    private int size;

    public BST() {
        root = null;
        size = 0;
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
     * 删除以node为根的二分搜索树中的最小结点
     *
     * @param node
     * @return 返回删除结点后新的二分搜索树的根
     */
    private Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
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

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {//key.compareTo(node.key) == 0

            //待删除结点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            //待删除结点右子树为空的情况
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }


              /*待删除结点左右子树为空的情况
                找到比待删除结点大的最小结点，即待删除结点右子树的最小结点
                用这个结点顶替待删除结点的位置
                */

            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node.left = node.right = null;

            return successor;

        }
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

    public static void main(String[] args) {

        System.out.println("傲慢与偏见");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("共有单词数：" + words.size());

            BST<String, Integer> bst = new BST<>();
            for (String word : words) {
                if (bst.contains(word)) {
                    bst.set(word, bst.get(word) + 1);
                } else {
                    bst.add(word, 1);
                }
            }

            System.out.println("共有不同单词数：" + bst.getSize());
            System.out.println("出现pride的次数: " + bst.get("pride"));
            System.out.println("出现prejudice的次数: " + bst.get("prejudice"));
        }
    }
}
