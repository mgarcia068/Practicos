import java.time.LocalDateTime;

public interface Planner {
    void programar(Recordatorio r);
    Recordatorio proximo();
    void reprogramar(String id, LocalDateTime nuevaFecha);
    int size();
}

class PlannerImpl implements Planner {
    private final MinHeapRecordatorios heap = new MinHeapRecordatorios();

    @Override
    public void programar(Recordatorio r) { heap.programar(r); }

    @Override
    public Recordatorio proximo() { return heap.proximo(); }

    @Override
    public void reprogramar(String id, LocalDateTime nuevaFecha) { heap.reprogramar(id, nuevaFecha); }

    @Override
    public int size() { return heap.sizeHeap(); }
}
