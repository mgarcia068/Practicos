import java.util.Arrays;

public class ej6 {
    public static void main(String[] args) {
        int[] arr = {9, 4, 7, 1, 6, 2};
        System.out.println("Original: " + Arrays.toString(arr));
        MinHeap.heapsort(arr);
        System.out.println("Ordenado: " + Arrays.toString(arr));
    }
}
