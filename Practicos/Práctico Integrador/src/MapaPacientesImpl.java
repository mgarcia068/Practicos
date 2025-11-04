import java.util.ArrayList;

/**
 * Implementación de MapaPacientes usando hash encadenado (listas simples) con rehash
 * cuando loadFactor > 0.75. No utiliza colecciones que resuelvan el problema por sí solas.
 */
public class MapaPacientesImpl implements MapaPacientes {
    private static class Node {
        final String key;
        Paciente value;
        Node next;
        Node(String k, Paciente v, Node n) { key = k; value = v; next = n; }
    }

    private Node[] buckets;
    private int size;
    private static final double LOAD_FACTOR = 0.75;

    public MapaPacientesImpl() { this(16); }

    public MapaPacientesImpl(int initialCapacity) {
        if (initialCapacity <= 0) initialCapacity = 16;
        buckets = (Node[]) new MapaPacientesImpl.Node[initialCapacity];
        size = 0;
    }

    private int indexFor(String key, int len) {
        int h = key.hashCode();
        // mezcla simple para mejorar distribución de hashCode pobre
        int mixed = h ^ (h >>> 16);
        return (mixed & 0x7fffffff) % len;
    }

    @Override
    public void put(String dni, Paciente p) {
        if (dni == null) throw new IllegalArgumentException("key null");
        if ((double)(size + 1) / buckets.length > LOAD_FACTOR) rehash();
        int idx = indexFor(dni, buckets.length);
        Node cur = buckets[idx];
        for (Node n = cur; n != null; n = n.next) {
            if (n.key.equals(dni)) { n.value = p; return; }
        }
        // insertar al frente para O(1)
        buckets[idx] = new Node(dni, p, cur);
        size++;
    }

    @Override
    public Paciente get(String dni) {
        if (dni == null) return null;
        int idx = indexFor(dni, buckets.length);
        for (Node n = buckets[idx]; n != null; n = n.next) if (n.key.equals(dni)) return n.value;
        return null;
    }

    @Override
    public boolean remove(String dni) {
        if (dni == null) return false;
        int idx = indexFor(dni, buckets.length);
        Node prev = null; Node cur = buckets[idx];
        while (cur != null) {
            if (cur.key.equals(dni)) {
                if (prev == null) buckets[idx] = cur.next; else prev.next = cur.next;
                size--;
                return true;
            }
            prev = cur; cur = cur.next;
        }
        return false;
    }

    @Override
    public boolean containsKey(String dni) { return get(dni) != null; }

    @Override
    public int size() { return size; }

    @Override
    public Iterable<String> keys() {
        ArrayList<String> out = new ArrayList<>(size);
        for (int i = 0; i < buckets.length; i++) {
            for (Node n = buckets[i]; n != null; n = n.next) out.add(n.key);
        }
        return out;
    }

    private void rehash() {
        Node[] old = buckets;
        int newCap = old.length * 2;
        buckets = (Node[]) new Node[newCap];
        size = 0;
        for (Node head : old) {
            for (Node n = head; n != null; n = n.next) {
                // reinsert
                int idx = indexFor(n.key, buckets.length);
                buckets[idx] = new Node(n.key, n.value, buckets[idx]);
                size++;
            }
        }
    }
}

