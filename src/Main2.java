import java.util.ArrayList;
import java.util.Random;

/**
 * @author Li Ersan
 */
public class Main2 {
    public static void main(String[] args) {

        int n = 20000000;

        Random random = new Random();
        ArrayList<Integer> testData = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            testData.add(random.nextInt(Integer.MAX_VALUE));
        }

        // Test BST
        long startTime = System.nanoTime();

        BST<Integer, Integer> bst = new BST<>();

        for (Integer x : testData) {
            bst.add(x, null);
        }

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("BST: " + time + " s");

        // Test AVLTree
        startTime = System.nanoTime();

        AVLTree<Integer, Integer> avlTree = new AVLTree<>();

        for (Integer x : testData) {
            avlTree.add(x, null);
        }

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVLTree: " + time + " s");

        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbTree = new RBTree<>();

        for (Integer x : testData) {
            rbTree.add(x, null);
        }

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");
    }
}
