import java.time.LocalDateTime;

public class AgendaConHistorialTest {
    public static void main(String[] args) {
        System.out.println("=== AgendaConHistorial tests ===");
        AgendaConHistorialImpl ag = new AgendaConHistorialImpl();

        Turno t1 = new Turno("R1","32045982","M-001", LocalDateTime.now().plusDays(1).withHour(9).withMinute(0), 30, "c1");
        boolean a1 = ag.agendar(t1);
        System.out.println("agendar R1: " + a1);

        // reprogramar
        boolean r = ag.reprogramar("R1", t1.fechaHora.plusHours(1));
        System.out.println("reprogramar R1 +1h: " + r);

        // undo -> back to original
        boolean u = ag.undo();
        System.out.println("undo: " + u);

        // redo -> reapply reprogram
        boolean re = ag.redo();
        System.out.println("redo: " + re);

        System.out.println("AgendaConHistorial tests completed.");
    }
}
