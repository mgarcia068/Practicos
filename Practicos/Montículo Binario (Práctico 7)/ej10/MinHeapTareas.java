public class MinHeapTareas {
    Tarea[] heap;
    int size;

    public MinHeapTareas(int capacidad) {
        heap = new Tarea[capacidad];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void agregar(Tarea t) {
        heap[size] = t;
        percolateUp(size);
        size++;
    }

    public Tarea peek() {
        if (isEmpty()) return null;
        return heap[0];
    }

    public Tarea completar() {
        if (isEmpty()) return null;
        Tarea min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        percolateDown(0);
        return min;
    }

    public void mostrar() {
        for (int i = 0; i < size; i++) System.out.println(heap[i]);
    }

    private void percolateUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].prioridad < heap[parent].prioridad) {
                Tarea temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;
                index = parent;
            } else break;
        }
    }

    private void percolateDown(int index) {
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left].prioridad < heap[smallest].prioridad) smallest = left;
            if (right < size && heap[right].prioridad < heap[smallest].prioridad) smallest = right;

            if (smallest != index) {
                Tarea temp = heap[index];
                heap[index] = heap[smallest];
                heap[smallest] = temp;
                index = smallest;
            } else break;
        }
    }
}
