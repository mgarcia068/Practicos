public class ej7 {
        public static void main(String[] args) {
        int[] datos = {10, 3, 15, 8, 6, 12};
        MaxHeap heap = new MaxHeap(10);
        for (int d : datos) heap.add(d);

        System.out.println("\nExtrayendo de mayor a menor:");
        while (heap.size > 0) System.out.print(heap.poll() + " ");
        System.out.println();
    }
}
