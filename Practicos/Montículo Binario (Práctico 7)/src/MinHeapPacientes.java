public class MinHeapPacientes {
    Paciente[] heap;
    int size;

    public MinHeapPacientes(int capacidad) {
        heap = new Paciente[capacidad];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void ingresar(Paciente p) {
        heap[size] = p;
        percolateUp(size);
        size++;
    }

    public Paciente atender() {
        Paciente min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        percolateDown(0);
        return min;
    }

    private void percolateUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].prioridad < heap[parent].prioridad) {
                Paciente temp = heap[index];
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
                Paciente temp = heap[index];
                heap[index] = heap[smallest];
                heap[smallest] = temp;
                index = smallest;
            } else break;
        }
    }
}