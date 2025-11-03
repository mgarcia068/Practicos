import java.util.Arrays;

public class MinHeap {
    int[] heap;
    int size;
    int capacidad;

    public MinHeap(int capacidad) {
        this.capacidad = capacidad;
        heap = new int[capacidad];
        size = 0;
    }
    public MinHeap(int[] datos, boolean heapify) {
        this.capacidad = datos.length;
        heap = Arrays.copyOf(datos, capacidad);
        size = datos.length;
        heapify();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacidad;
    }

    public int peek() {
        if (isEmpty()) throw new IllegalStateException("El heap está vacío");
        return heap[0];
    }

    public void add(int valor) {
        if (isFull()) throw new IllegalStateException("Heap lleno");
        heap[size] = valor;
        percolateUp(size);
        size++;
    }

    public int poll() {
        if (isEmpty()) throw new IllegalStateException("Heap vacío");
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        percolateDown(0);
        return min;
    }

    private void percolateUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index] < heap[parent]) {
                System.out.println("Intercambiando " + heap[index] + " con " + heap[parent]);
                int temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;
                index = parent;
            } else {
                break;
            }
        }
    }

    private void percolateDown(int index) {
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left] < heap[smallest]) smallest = left;
            if (right < size && heap[right] < heap[smallest]) smallest = right;

            if (smallest != index) {
                int temp = heap[index];
                heap[index] = heap[smallest];
                heap[smallest] = temp;
                index = smallest;
            } else break;
        }
    }

    private void heapify() {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    public void printArray() {
        System.out.println(Arrays.toString(Arrays.copyOf(heap, size)));
    }

    public void printTree() {
        int nivel = 0;
        int elementosEnNivel = 1;
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
            if ((i + 1) == elementosEnNivel) {
                System.out.println();
                nivel++;
                elementosEnNivel += Math.pow(2, nivel);
            }
        }
        System.out.println();
    }

    public static void heapsort(int[] arr) {
        MinHeap heap = new MinHeap(arr.length);
        for (int num : arr) heap.add(num);
        for (int i = 0; i < arr.length; i++) arr[i] = heap.poll();
    }
}

