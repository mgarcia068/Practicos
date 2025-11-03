public class ej3 {
    public static void main(String[] args) {
        MinHeap heap = new MinHeap(10);
        int[] valores = {20, 5, 15, 3, 11};
        for (int v : valores) heap.add(v);

        System.out.println("Antes de eliminar:");
        heap.printArray();

        while (!heap.isEmpty()) {
            System.out.println("Eliminando " + heap.poll());
            heap.printArray();
        }
    }
}
