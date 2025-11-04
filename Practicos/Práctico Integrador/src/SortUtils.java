import java.util.Comparator;
import java.util.List;

/**
 * Sorting utilities: stable insertion sort, shellsort (Knuth gaps), quicksort (Lomuto).
 */
public class SortUtils {

    // Stable insertion sort
    public static <T> void insertionSort(List<T> a, Comparator<T> cmp) {
        for (int i = 1; i < a.size(); i++) {
            T key = a.get(i);
            int j = i - 1;
            while (j >= 0 && cmp.compare(a.get(j), key) > 0) {
                a.set(j + 1, a.get(j));
                j--;
            }
            a.set(j + 1, key);
        }
    }

    // Shellsort with Knuth sequence: h = 1; while (h < n/3) h = 3*h + 1;
    public static <T> void shellSort(List<T> a, Comparator<T> cmp) {
        int n = a.size();
        int h = 1;
        while (h < n / 3) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                T temp = a.get(i);
                int j = i;
                while (j >= h && cmp.compare(a.get(j - h), temp) > 0) {
                    a.set(j, a.get(j - h));
                    j -= h;
                }
                a.set(j, temp);
            }
            h = h / 3;
        }
    }

    // Quicksort (Lomuto partition), not stable
    public static <T> void quicksort(List<T> a, Comparator<T> cmp) {
        quicksort(a, 0, a.size() - 1, cmp);
    }

    private static <T> void quicksort(List<T> a, int lo, int hi, Comparator<T> cmp) {
        if (lo < hi) {
            int p = lomutoPartition(a, lo, hi, cmp);
            quicksort(a, lo, p - 1, cmp);
            quicksort(a, p + 1, hi, cmp);
        }
    }

    private static <T> int lomutoPartition(List<T> a, int lo, int hi, Comparator<T> cmp) {
        // Use middle element as pivot to avoid worst case with sorted arrays
        int mid = lo + (hi - lo) / 2;
        swap(a, mid, hi);
        T pivot = a.get(hi);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (cmp.compare(a.get(j), pivot) < 0) {  // Changed <= to < to handle equal elements better
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static <T> void swap(List<T> a, int i, int j) {
        T tmp = a.get(i); a.set(i, a.get(j)); a.set(j, tmp);
    }
}
