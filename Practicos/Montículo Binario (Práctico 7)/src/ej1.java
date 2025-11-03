public class ej1 {
    public static void main(String[] args) {
        MinHeap heap = new MinHeap(10);
        int[] valores = {20, 5, 15, 3, 11};

        for (int v : valores) heap.add(v);

        System.out.println("Extrayendo elementos en orden:");
        while (!heap.isEmpty()) {
            System.out.print(heap.poll() + " ");
        }
        System.out.println();
    }
}
