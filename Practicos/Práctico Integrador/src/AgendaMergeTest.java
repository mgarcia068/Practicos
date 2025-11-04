
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaMergeTest {
    private static Turno t(String id, String dni, String mat, String fecha) {
        return new Turno(id, dni, mat, LocalDateTime.parse(fecha), 30, "test");
    }

    private static boolean containsId(List<Turno> L, String id) {
        for (Turno t : L) if (t.id.equals(id)) return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println("=== Agenda merge tests ===");

        List<Turno> A = new ArrayList<>();
        List<Turno> B = new ArrayList<>();

        // A: T-001 (M-001 09:00), T-003 (M-001 10:00), T-005 (M-002 09:00)
        A.add(t("T-001", "32045982", "M-001", "2025-11-04T09:00"));
        A.add(t("T-003", "31247856", "M-001", "2025-11-04T10:00"));
        A.add(t("T-005", "31111222", "M-002", "2025-11-04T09:00"));

        // B: duplicate id T-001, and a different id T-002 that conflicts exact time with T-001 (same medico+hora)
        B.add(t("T-001", "32045982", "M-001", "2025-11-04T09:00")); // duplicate id
        B.add(t("T-002", "32458910", "M-001", "2025-11-04T09:00")); // same medico+hora -> conflict

        List<Turno> merged = AgendaUtils.merge(A, B);

        // Expected: T-001 kept once, T-002 skipped due to conflict with T-001, T-005 kept, T-003 kept
        boolean ok = containsId(merged, "T-001") && containsId(merged, "T-003") && containsId(merged, "T-005")
                && !containsId(merged, "T-002");
        System.out.println((ok ? "PASS: " : "FAIL: ") + "merge basic dedup/conflict behavior");
        if (!ok) throw new RuntimeException("Merge test failed");

        System.out.println("Merged list (in order):");
        for (Turno t : merged) System.out.println(" - " + t);

        System.out.println("Agenda merge tests completed.");
    }
}
