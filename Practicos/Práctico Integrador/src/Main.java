import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nCargando datos iniciales...");
        DataLoader loader = new DataLoader();
        loader.cargarTodo("Práctico Integrador/data");


        System.out.println("> Pacientes cargados: " + loader.pacientes.size());
        System.out.println("> Medicos cargados: " + loader.medicos.size());
        System.out.println("> Turnos leidos validos: " + loader.turnosValidos.size());
        if (!loader.errores.isEmpty()) {
            System.out.println("> Errores detectados:");
            loader.errores.forEach(e -> System.out.println("  - " + e));
        }

        // Construir agendas por médico (punto 2)
        Map<String, AvlAgenda> agendas = new HashMap<>();
        for (String mat : loader.medicos.keySet()) agendas.put(mat, new AvlAgenda());

        // Insertar turnos validados en cada agenda
        for (Turno t : loader.turnosValidos) {
            AvlAgenda agenda = agendas.get(t.matriculaMedico);
            boolean ok = agenda.agendar(t);
            if (!ok) System.out.println("Conflicto al agendar turno " + t.id + " para medico " + t.matriculaMedico);
        }

        // Demo: siguiente turno >= now for a medico
        Optional<String> anyMed = loader.medicos.keySet().stream().findFirst();
        if (anyMed.isPresent()) {
            AvlAgenda ag = agendas.get(anyMed.get());
            System.out.println("\n[AGENDA DEMO] Medico: " + loader.medicos.get(anyMed.get()));
            System.out.println("Turnos actuales (in-order):");
            ag.printInOrder();

            LocalDateTime q = LocalDateTime.now().plusDays(1);
            System.out.println("Siguiente turno >= " + q + ": " + ag.siguiente(q));
        }

        // Punto 3: primerHueco demo
        if (anyMed.isPresent()) {
            AvlAgenda ag = agendas.get(anyMed.get());
            LocalDateTime desde = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
            Optional<LocalDateTime> hueco = ag.primerHueco(desde, 30);
            System.out.println("\nPrimer hueco >= " + desde + " (30 min): " + hueco.orElse(null));
        }

        // Punto 4: Sala de espera demo
        System.out.println("\n[SALA DE ESPERA DEMO]");
        SalaEspera sala = new SalaEspera(5);
        sala.llega("32045982");
        sala.llega("32458910");
        sala.llega("31890432");
        sala.llega("31247856");
        sala.llega("32500890");
        sala.printEstado();
        System.out.println("Llega paciente 31111222 -> overflow");
        sala.llega("31111222");
        sala.printEstado();
        System.out.println("Atienden: " + sala.atiende());
        sala.printEstado();

        // Punto 5: Planner demo
        System.out.println("\n[PLANNER DEMO]");
        Planner planner = new PlannerImpl();
        planner.programar(new Recordatorio("R1", LocalDateTime.now().plusMinutes(60), "32045982", "Recordatorio 1"));
        planner.programar(new Recordatorio("R2", LocalDateTime.now().plusMinutes(30), "32458910", "Recordatorio 2"));
        planner.programar(new Recordatorio("R3", LocalDateTime.now().plusMinutes(90), "31890432", "Recordatorio 3"));
        System.out.println("Proximo: " + planner.proximo());
        System.out.println("Reprogramar R3 a +15min");
        planner.reprogramar("R3", LocalDateTime.now().plusMinutes(15));
        System.out.println("Proximo: " + planner.proximo());
    }
}
