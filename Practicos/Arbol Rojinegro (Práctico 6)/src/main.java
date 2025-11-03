import java.util.List;

public class main {
    public static void main(String[] args) {
        // Create a new Red-Black Tree
        RBTree<Integer, String> tree = new RBTree<>();

        System.out.println("Inserting numbers into tree...");
        int[] valores = {20, 15, 25, 10, 18, 17, 19, 30, 5, 12};
        
        for (int val : valores) {
            System.out.println("\nInserting " + val);
            tree.insert(val, "val" + val);
            System.out.println("Tree after insertion:");
            tree.printInOrderWithColors();
        }

        System.out.println("\nFinal tree in-order traversal:");
        tree.printInOrderWithColors();

        // Test tree properties
        System.out.println("\nTesting Red-Black Tree properties:");
        System.out.println("Root is black? " + tree.raizNegra());
        System.out.println("No red-red violations? " + tree.sinRojoRojo());
        System.out.println("Black height: " + tree.alturaNegra());

        // Test search and successor/predecessor
        System.out.println("\nTesting search and successor/predecessor:");
        RBNode<Integer, String> node15 = tree.searchNode(15);
        if (node15 != null) {
            System.out.println("Found node with key 15");
            RBNode<Integer, String> succ = tree.successor(node15);
            RBNode<Integer, String> pred = tree.predecessor(node15);
            System.out.println("Successor of 15: " + (succ != null ? succ.key : "none"));
            System.out.println("Predecessor of 15: " + (pred != null ? pred.key : "none"));
        }

        // Test range query
        System.out.println("\nTesting range query:");
        List<Integer> range = tree.rangeQuery(12, 19);
        System.out.println("Keys between 12 and 19: " + range);

        // Test with a smaller tree
        System.out.println("\nTesting with a smaller tree:");
        RBTree<Integer, String> smallTree = new RBTree<>();
        smallTree.insert(5, "a");
        smallTree.insert(10, "b");
        smallTree.insert(15, "c");
        
        System.out.println("Small tree in-order traversal:");
        smallTree.printInOrderWithColors();

        RBNode<Integer, String> node10 = smallTree.searchNode(10);
        if (node10 != null) {
            RBNode<Integer, String> succ = smallTree.successor(node10);
            RBNode<Integer, String> pred = smallTree.predecessor(node10);
            System.out.println("Successor of 10: " + (succ != null ? succ.key : "none"));
            System.out.println("Predecessor of 10: " + (pred != null ? pred.key : "none"));
        }
    }
}