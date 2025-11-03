import java.util.List;
import java.util.ArrayList;

public class RBTree<K extends Comparable<K>, V> {
    public final RBNode<K,V> NIL;
    private RBNode<K,V> root;
    public enum Caso { 
        TIO_ROJO,
        LL,
        RR,
        LR,
        RL,
        NO_APLICA
    }

    public RBTree() {
        NIL = new RBNode<>(null, null);
        NIL.color = RBNode.Color.BLACK;
        NIL.left = NIL;
        NIL.right = NIL;
        NIL.parent = NIL;
        root = NIL;
    }

    public void insert(K key, V val) {
        RBNode<K,V> z = insertBST(key, val);
        fixInsert(z);
    }

    private void fixInsert(RBNode<K,V> z) {
        while (z.parent.color == RBNode.Color.RED) {
            Caso caso = clasificarCaso(z);
            switch (caso) {
                case TIO_ROJO:
                    RBNode<K,V> p = z.parent;
                    RBNode<K,V> g = p.parent;
                    RBNode<K,V> t = (p == g.left) ? g.right : g.left;
                    p.color = RBNode.Color.BLACK;
                    t.color = RBNode.Color.BLACK;
                    g.color = RBNode.Color.RED;
                    z = g;
                    break;
                case LL:
                    z.parent.color = RBNode.Color.BLACK;
                    z.parent.parent.color = RBNode.Color.RED;
                    rotateRight(z.parent.parent);
                    break;
                case RR:
                    z.parent.color = RBNode.Color.BLACK;
                    z.parent.parent.color = RBNode.Color.RED;
                    rotateLeft(z.parent.parent);
                    break;
                case LR:
                    z = z.parent;
                    rotateLeft(z);
                    z.parent.color = RBNode.Color.BLACK;
                    z.parent.parent.color = RBNode.Color.RED;
                    rotateRight(z.parent.parent);
                    break;
                case RL:
                    z = z.parent;
                    rotateRight(z);
                    z.parent.color = RBNode.Color.BLACK;
                    z.parent.parent.color = RBNode.Color.RED;
                    rotateLeft(z.parent.parent);
                    break;
                default:
                    break;
            }
            if (z == root) break;
        }
        root.color = RBNode.Color.BLACK;
    }

    public RBNode<K,V> getRoot() { return root; }

    public void rotateLeft(RBNode<K,V> x) {
        if (x == null || x.isNil(NIL)) return;
        RBNode<K,V> y = x.right;
        if (y == null || y.isNil(NIL)) return;

        x.right = y.left;
        if (!y.left.isNil(NIL)) y.left.parent = x;

        y.parent = x.parent;
        if (x.parent.isNil(NIL)) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    public void rotateRight(RBNode<K,V> y) {
        if (y == null || y.isNil(NIL)) return;
        RBNode<K,V> x = y.left;
        if (x == null || x.isNil(NIL)) return;

        y.left = x.right;
        if (!x.right.isNil(NIL)) x.right.parent = y;

        x.parent = y.parent;
        if (y.parent.isNil(NIL)) root = x;
        else if (y == y.parent.right) y.parent.right = x;
        else y.parent.left = x;

        x.right = y;
        y.parent = x;
    }

    public RBNode<K,V> insertBST(K key, V val) {
        RBNode<K,V> z = new RBNode<>(key, val);
        z.left = NIL;
        z.right = NIL;
        z.parent = NIL;
        z.color = RBNode.Color.RED;

        RBNode<K,V> y = NIL;
        RBNode<K,V> x = root;
        while (!x.isNil(NIL)) {
            y = x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else x = x.right;
        }
        z.parent = y;
        if (y.isNil(NIL)) root = z;
        else if (key.compareTo(y.key) < 0) y.left = z;
        else y.right = z;
        return z;
    }

    public RBNode<K,V> searchNode(K key) {
        RBNode<K,V> x = root;
        while (!x.isNil(NIL)) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x;
            x = (cmp < 0) ? x.left : x.right;
        }
        return null;
    }

    public Caso clasificarCaso(RBNode<K,V> z) {
        if (z == null || z.isNil(NIL)) return Caso.NO_APLICA;
        RBNode<K,V> p = z.parent;
        if (p == null || p.isNil(NIL)) return Caso.NO_APLICA;
        RBNode<K,V> g = p.parent;
        if (g == null || g.isNil(NIL)) return Caso.NO_APLICA;

        RBNode<K,V> tio = (p == g.left) ? g.right : g.left;
        boolean tioRojo = !tio.isNil(NIL) && tio.color == RBNode.Color.RED;
        if (tioRojo) return Caso.TIO_ROJO;

        if (p == g.left) {
            if (z == p.left) return Caso.LL;
            else return Caso.LR;
        } else {
            if (z == p.right) return Caso.RR;
            else return Caso.RL;
        }
    }

    public boolean raizNegra() {
        return root.color == RBNode.Color.BLACK;
    }

    public boolean sinRojoRojo() {
        return !tieneRojoRojo(root);
    }

    private boolean tieneRojoRojo(RBNode<K,V> node) {
        if (node.isNil(NIL)) return false;
        if (node.color == RBNode.Color.RED) {
            if (!node.left.isNil(NIL) && node.left.color == RBNode.Color.RED) return true;
            if (!node.right.isNil(NIL) && node.right.color == RBNode.Color.RED) return true;
        }
        return tieneRojoRojo(node.left) || tieneRojoRojo(node.right);
    }

    public int alturaNegra() {
        return calcularAlturaNegra(root);
    }

    private int calcularAlturaNegra(RBNode<K,V> node) {
        if (node.isNil(NIL)) return 0;
        int altura = node.color == RBNode.Color.BLACK ? 1 : 0;
        int alturaIzquierda = calcularAlturaNegra(node.left);
        int alturaDerecha = calcularAlturaNegra(node.right);
        return altura + Math.max(alturaIzquierda, alturaDerecha);
    }

    public RBNode<K,V> successor(RBNode<K,V> x) {
        if (x == null || x.isNil(NIL)) return null;
        if (!x.right.isNil(NIL)) {
            RBNode<K,V> y = x.right;
            while (!y.left.isNil(NIL)) y = y.left;
            return y;
        }
        RBNode<K,V> y = x.parent;
        while (!y.isNil(NIL) && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y.isNil(NIL) ? null : y;
    }

    public RBNode<K,V> predecessor(RBNode<K,V> x) {
        if (x == null || x.isNil(NIL)) return null;
        if (!x.left.isNil(NIL)) {
            RBNode<K,V> y = x.left;
            while (!y.right.isNil(NIL)) y = y.right;
            return y;
        }
        RBNode<K,V> y = x.parent;
        while (!y.isNil(NIL) && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y.isNil(NIL) ? null : y;
    }

    public List<K> rangeQuery(K lo, K hi) {
        List<K> result = new ArrayList<>();
        rangeQuery(root, lo, hi, result);
        return result;
    }

    private void rangeQuery(RBNode<K,V> node, K lo, K hi, List<K> result) {
        if (node.isNil(NIL)) return;
        
        int cmpLo = lo.compareTo(node.key);
        int cmpHi = hi.compareTo(node.key);
        
        if (cmpLo < 0) rangeQuery(node.left, lo, hi, result);
        if (cmpLo <= 0 && cmpHi >= 0) result.add(node.key);
        if (cmpHi > 0) rangeQuery(node.right, lo, hi, result);
    }

    public void printInOrderWithColors() {
        printInOrderWithColors(root, "");
        System.out.println();
    }

    private void printInOrderWithColors(RBNode<K,V> node, String prefix) {
        if (node.isNil(NIL)) return;
        printInOrderWithColors(node.left, prefix);
        System.out.print(prefix + node + " ");
        printInOrderWithColors(node.right, prefix);
    }
}