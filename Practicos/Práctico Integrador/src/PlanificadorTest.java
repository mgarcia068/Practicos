import java.time.LocalDateTime;
import java.util.List;

public class PlanificadorTest {
    public static void main(String[] args) {
        System.out.println("=== Planificador tests ===");
        PlanificadorQuirofanoImpl p = new PlanificadorQuirofanoImpl(3); // 3 ORs

        LocalDateTime dl = LocalDateTime.now().plusHours(5);
        p.procesar(new SolicitudCirugia("C1","M-001", 60, dl));
        p.procesar(new SolicitudCirugia("C2","M-002", 120, dl.plusHours(1)));
        p.procesar(new SolicitudCirugia("C3","M-001", 30, dl.plusHours(2)));

        List<String> top = p.topKMedicosBloqueados(2);
        System.out.println("Top medicos: " + top);

        System.out.println("Planificador tests completed.");
    }
}
