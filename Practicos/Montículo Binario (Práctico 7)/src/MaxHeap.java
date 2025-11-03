public class MaxHeap {
    int[] heap;
    int size;
    int capacidad;

    public MaxHeap(int capacidad) {
        this.capacidad = capacidad;
        heap = new int[capacidad];
        size = 0;
    }

    public void add(int valor) {
        heap[size] = valor;
        percolateUp(size);
        size++;
    }

    public int poll() {
        int max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        percolateDown(0);
        return max;
    }

    private void percolateUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index] > heap[parent]) {
                int temp = heap[index];
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
            int largest = index;

            if (left < size && heap[left] > heap[largest]) largest = left;
            if (right < size && heap[right] > heap[largest]) largest = right;

            if (largest != index) {
                int temp = heap[index];
                heap[index] = heap[largest];
                heap[largest] = temp;
                index = largest;
            } else break;
        }
    }
}