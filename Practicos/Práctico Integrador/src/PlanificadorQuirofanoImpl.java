import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple planificador de quirófanos.
 * - Q quirófanos numerados 0..Q-1
 * - Mantiene min-heap (simple binary heap) de quirófanos ordenado por nextFreeTime
 * - topKMedicosBloqueados(K) construye on-demand un min-heap de tamaño K a partir del mapa de minutos bloqueados
 */
public class PlanificadorQuirofanoImpl implements PlanificadorQuirofano {
    private static class OR {
        int id; LocalDateTime nextFree;
        OR(int id) { this.id = id; this.nextFree = LocalDateTime.now(); }
    }

    private final List<OR> heap = new ArrayList<>(); // min-heap by nextFree
    private final Map<String,Integer> medBlocked = new HashMap<>(); // minutos bloqueados en proxima semana
    private final int Q;

    public PlanificadorQuirofanoImpl(int Q) {
        this.Q = Q;
        for (int i = 0; i < Q; i++) heap.add(new OR(i));
        // build heap
        for (int i = Q/2 - 1; i >= 0; i--) siftDown(i);
    }

    private int cmp(int i, int j) {
        return heap.get(i).nextFree.compareTo(heap.get(j).nextFree);
    }

    private void siftDown(int i) {
        int n = heap.size();
        while (true) {
            int l = 2*i + 1, r = 2*i + 2, smallest = i;
            if (l < n && heap.get(l).nextFree.isBefore(heap.get(smallest).nextFree)) smallest = l;
            if (r < n && heap.get(r).nextFree.isBefore(heap.get(smallest).nextFree)) smallest = r;
            if (smallest != i) { swap(i, smallest); i = smallest; } else break;
        }
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (heap.get(i).nextFree.isBefore(heap.get(p).nextFree)) { swap(i, p); i = p; } else break;
        }
    }

    private void swap(int a, int b) { OR ta = heap.get(a); OR tb = heap.get(b); heap.set(a, tb); heap.set(b, ta); }

    @Override
    public void procesar(SolicitudCirugia s) {
        // get earliest OR (root)
        OR or = heap.get(0);
        LocalDateTime start = or.nextFree.isAfter(LocalDateTime.now()) ? or.nextFree : LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(s.durMin);
        if (end.isAfter(s.deadline)) {
            // cannot schedule; ignore or log
            System.out.println("CANNOT SCHEDULE " + s.id + " before deadline");
            return;
        }
        // assign
        or.nextFree = end;
        siftDown(0);

        // update medBlocked for next week window
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime week = now.plusDays(7);
        if (!start.isAfter(week) && !end.isBefore(now)) {
            medBlocked.put(s.matricula, medBlocked.getOrDefault(s.matricula, 0) + s.durMin);
        }
    }

    @Override
    public List<String> topKMedicosBloqueados(int K) {
        // on-demand compute top-K using simple min-heap of size K (ArrayList)
        List<Map.Entry<String,Integer>> entries = new ArrayList<>(medBlocked.entrySet());
        // min-heap by value of size K
        List<Map.Entry<String,Integer>> h = new ArrayList<>();
        for (Map.Entry<String,Integer> e : entries) {
            if (h.size() < K) { h.add(e); if (h.size() == K) buildMinHeapEntries(h); }
            else {
                if (e.getValue() > h.get(0).getValue()) { h.set(0, e); siftDownEntries(h, 0); }
            }
        }
        // extract heap into result list sorted desc
        List<String> res = new ArrayList<>();
        h.sort((a,b)-> Integer.compare(b.getValue(), a.getValue()));
        for (Map.Entry<String,Integer> e : h) res.add(e.getKey());
        return res;
    }

    private void buildMinHeapEntries(List<Map.Entry<String,Integer>> h) {
        for (int i = h.size()/2 -1; i >=0; i--) siftDownEntries(h, i);
    }

    private void siftDownEntries(List<Map.Entry<String,Integer>> h, int i) {
        int n = h.size();
        while (true) {
            int l = 2*i + 1, r = 2*i + 2, smallest = i;
            if (l < n && h.get(l).getValue() < h.get(smallest).getValue()) smallest = l;
            if (r < n && h.get(r).getValue() < h.get(smallest).getValue()) smallest = r;
            if (smallest != i) { Map.Entry<String,Integer> tmp = h.get(i); h.set(i, h.get(smallest)); h.set(smallest, tmp); i = smallest; } else break;
        }
    }
}
