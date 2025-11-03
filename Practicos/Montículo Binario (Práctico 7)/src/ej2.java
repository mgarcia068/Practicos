public class ej2 {
    public static void main(String[] args) {
        MinHeap heap = new MinHeap(10);
        int[] valores = {20, 5, 15, 3, 11};
        for (int v : valores) heap.add(v);
        heap.printArray();
    }
}
