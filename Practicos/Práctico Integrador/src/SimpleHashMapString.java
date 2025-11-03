import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Hash map simple de String -> V con chaining. Rehash automático al loadFactor > 0.75
 */
public class SimpleHashMapString<V> {
    private static class Entry<V> {
        final String key;
        V value;
        Entry(String k, V v) { key = k; value = v; }
    }

    private List<Entry<V>>[] buckets;
    private int size;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public SimpleHashMapString(int initialCapacity) {
        buckets = (List<Entry<V>>[]) new List[initialCapacity];
        size = 0;
    }

    public SimpleHashMapString() { this(16); }

    private int idx(String key) {
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }

    public V get(String key) {
        int i = idx(key);
        List<Entry<V>> bucket = buckets[i];
        if (bucket == null) return null;
        for (Entry<V> e : bucket) if (e.key.equals(key)) return e.value;
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public void put(String key, V value) {
        if ((double)(size + 1) / buckets.length > LOAD_FACTOR) rehash();
        int i = idx(key);
        if (buckets[i] == null) buckets[i] = new ArrayList<>();
        for (Entry<V> e : buckets[i]) {
            if (e.key.equals(key)) { e.value = value; return; }
        }
        buckets[i].add(new Entry<>(key, value));
        size++;
    }

    public V remove(String key) {
        int i = idx(key);
        List<Entry<V>> bucket = buckets[i];
        if (bucket == null) return null;
        Iterator<Entry<V>> it = bucket.iterator();
        while (it.hasNext()) {
            Entry<V> e = it.next();
            if (e.key.equals(key)) { it.remove(); size--; return e.value; }
        }
        return null;
    }

    public int size() { return size; }

    @SuppressWarnings("unchecked")
    private void rehash() {
        List<Entry<V>>[] old = buckets;
        buckets = (List<Entry<V>>[]) new List[old.length * 2];
        size = 0;
        for (List<Entry<V>> bucket : old) {
            if (bucket == null) continue;
            for (Entry<V> e : bucket) put(e.key, e.value);
        }
    }
}
