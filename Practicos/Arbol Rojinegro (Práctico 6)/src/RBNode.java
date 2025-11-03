public class RBNode<K extends Comparable<K>, V> {
    public enum Color { RED, BLACK }
    public K key;
    public V val;
    public Color color;
    public RBNode<K,V> left, right, parent;

    public RBNode(K key, V val) {
        this.key = key;
        this.val = val;
        this.color = Color.RED;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public boolean isNil(RBNode<K,V> nil) {
        return this == nil;
    }

    @Override
    public String toString() {
        return String.format("(%s:%s,%s)", key, val, color == Color.RED ? "R" : "B");
    }
}
