import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MinHeapRecordatorios {
    private final List<Recordatorio> heap = new ArrayList<>();
    private final SimpleHashMapString<Integer> indexById = new SimpleHashMapString<>();

    public int size() { return heap.size(); }

    public void programar(Recordatorio r) {
        heap.add(r);
        int idx = heap.size() - 1;
        indexById.put(r.id, idx);
        percolateUp(idx);
    }

    public Recordatorio proximo() {
        if (heap.isEmpty()) return null;
        Recordatorio min = heap.get(0);
        Recordatorio last = heap.remove(heap.size() - 1);
        indexById.remove(min.id);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            indexById.put(last.id, 0);
            percolateDown(0);
        }
        return min;
    }

    public void reprogramar(String id, LocalDateTime nuevaFecha) {
        Integer idx = indexById.get(id);
        if (idx == null) throw new IllegalArgumentException("id no encontrado: " + id);
        Recordatorio r = heap.get(idx);
        r.fecha = nuevaFecha;
        // reheapify: podría subir o bajar
        percolateUp(idx);
        percolateDown(idx);
    }

    private void percolateUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (heap.get(i).fecha.isBefore(heap.get(p).fecha)) {
                swap(i, p); i = p;
            } else break;
        }
    }

    private void percolateDown(int i) {
        int n = heap.size();
        while (true) {
            int l = 2*i + 1, r = 2*i + 2, smallest = i;
            if (l < n && heap.get(l).fecha.isBefore(heap.get(smallest).fecha)) smallest = l;
            if (r < n && heap.get(r).fecha.isBefore(heap.get(smallest).fecha)) smallest = r;
            if (smallest != i) { swap(i, smallest); i = smallest; } else break;
        }
    }

    private void swap(int a, int b) {
        Recordatorio ra = heap.get(a), rb = heap.get(b);
        heap.set(a, rb); heap.set(b, ra);
        indexById.put(ra.id, b);
        indexById.put(rb.id, a);
    }

    public int sizeHeap() { return heap.size(); }
}
