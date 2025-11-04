import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Comparator;

/**
 * Small benchmark to measure sorting times for synthetic Turno lists.
 * Generates random turnos and sorts by: fechaHora, duracion, apellido paciente.
 */
public class SortBench {
    private static final Random R = new Random(42);

    private static List<Turno> gen(int n) {
        List<Turno> L = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            String id = "S-" + i;
            String dni = String.valueOf(30000000 + i);
            String mat = "M-" + (1 + R.nextInt(20));
            LocalDateTime fecha = LocalDateTime.now().plusMinutes(R.nextInt(60*24*30));
            int dur = 15 + R.nextInt(120);
            L.add(new Turno(id, dni, mat, fecha, dur, "bench"));
        }
        return L;
    }

    private static Comparator<Turno> byHour = Comparator.comparing(t -> t.fechaHora);
    private static Comparator<Turno> byDuration = Comparator.comparingInt(t -> t.duracionMin);
    // For patient last name comparator: we'll use dni as stand-in for bench (no patient map here)
    private static Comparator<Turno> byPatient = Comparator.comparing((Turno t) -> t.dniPaciente);

    private static void runOnce(List<Turno> data, String name) {
        List<Turno> copy = new ArrayList<>(data);
        long t0 = System.nanoTime();
        SortUtils.insertionSort(copy, byHour);
        long t1 = System.nanoTime();
        System.out.println(name + " insertionSort by hour: " + ((t1 - t0)/1_000_000) + " ms");

        copy = new ArrayList<>(data);
        t0 = System.nanoTime();
        SortUtils.shellSort(copy, byDuration);
        t1 = System.nanoTime();
        System.out.println(name + " shellSort by duration: " + ((t1 - t0)/1_000_000) + " ms");

        copy = new ArrayList<>(data);
        t0 = System.nanoTime();
        SortUtils.quicksort(copy, byPatient);
        t1 = System.nanoTime();
        System.out.println(name + " quicksort by patient(dni): " + ((t1 - t0)/1_000_000) + " ms");
    }

    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 50000};
        for (int s : sizes) {
            System.out.println("\n--- bench size=" + s + " ---");
            List<Turno> data = gen(s);
            runOnce(data, "n=" + s);
        }
    }
}
