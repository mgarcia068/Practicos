public class ej5 {
        public static void main(String[] args) {
        int[] datos = {9, 4, 7, 1, 6, 2};
        System.out.println("Arreglo original:");
        for (int d : datos) System.out.print(d + " ");
        System.out.println("\nConstruyendo heap...");
        MinHeap heap = new MinHeap(datos, true);
        heap.printArray();
    }
}
