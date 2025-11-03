public class ej9 {
        public static void main(String[] args) {
        MinHeap heap = new MinHeap(10);
        int[] valores = {10, 4, 15, 2, 8, 20};

        for (int v : valores) {
            System.out.println("Agregando " + v);
            heap.add(v);
            heap.printArray();
        }

        while (!heap.isEmpty()) {
            System.out.println("Eliminando " + heap.poll());
            heap.printArray();
        }
    }
}
