import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
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

        // Punto 6: Índice rápido de pacientes (MapaPacientes)
        System.out.println("\n=== EJ6: MapaPacientes (put/get/remove/contains/keys) ===");
        MapaPacientesImpl mapa = new MapaPacientesImpl(4);
        // poblar desde loader.pacientes
        for (Paciente p : loader.pacientes.values()) {
            mapa.put(p.getDni(), p);
        }
        System.out.println("size mapa = " + mapa.size());
        String sampleDni = loader.pacientes.keySet().stream().findFirst().orElse(null);
        if (sampleDni != null) {
            System.out.println("containsKey(" + sampleDni + ") = " + mapa.containsKey(sampleDni));
            System.out.println("get(" + sampleDni + ") = " + mapa.get(sampleDni));
        }
        System.out.println("Iterando keys():");
        for (String k : mapa.keys()) System.out.println(" - " + k);
        // prueba remove (elimino primero encontrado)
        if (sampleDni != null) {
            boolean removed = mapa.remove(sampleDni);
            System.out.println("remove(" + sampleDni + ") = " + removed + ", nuevo size = " + mapa.size());
        }

        // Punto 7: Consolidación de agendas (AgendaUtils.merge)
        System.out.println("\n=== EJ7: AgendaUtils.merge (consolidación y deduplicación) ===");
        List<Turno> A = new ArrayList<>();
        List<Turno> B = new ArrayList<>();
        // repartir turnosValidos en A y B alternando
        for (int i = 0; i < loader.turnosValidos.size(); i++) {
            if (i % 2 == 0) A.add(loader.turnosValidos.get(i));
            else B.add(loader.turnosValidos.get(i));
        }
        // forzar duplicado por id: si existe al menos 1 en A, clonarlo en B con mismo id
        if (!A.isEmpty()) {
            Turno t0 = A.get(0);
            B.add(new Turno(t0.id, t0.dniPaciente, t0.matriculaMedico, t0.fechaHora, t0.duracionMin, t0.motivo));
        }
        // forzar conflicto mismo medico + misma fecha: crear en B un turno con misma matricula y fecha que A[1] if exists
        if (A.size() > 1) {
            Turno t1 = A.get(1);
            B.add(new Turno("X-COLL-"+t1.id, "00000000", t1.matriculaMedico, t1.fechaHora, 30, "conflicto"));
        }
        List<Turno> merged = AgendaUtils.merge(A, B);
        System.out.println("A size=" + A.size() + "  B size=" + B.size() + "  merged size=" + merged.size());
        System.out.println("Merged ids:");
        for (Turno t : merged) System.out.println(" - " + t.id + " | " + t.matriculaMedico + " | " + t.fechaHora);

        // Punto 8: Reportes operativos con múltiples ordenamientos
        System.out.println("\n=== EJ8: Reportes/sorts (inserción estable / shell / quicksort Lomuto) ===");
        List<Turno> copia = new ArrayList<>(loader.turnosValidos);
        // Orden por hora: insertion sort estable (asumo SortUtils.insertionSort existe)
        try {
            List<Turno> byHora = new ArrayList<>(copia);
            SortUtils.insertionSort(byHora, Comparator.comparing(t -> t.fechaHora));
            System.out.println("Orden por hora (insertion, estable):");
            byHora.forEach(t -> System.out.println(" - " + t.fechaHora + " | " + t.id));
        } catch (Throwable ex) {
            System.out.println("SortUtils.insertionSort no disponible o falló: " + ex.getMessage());
        }
        // Orden por duración: shellSort (gap sequence estándar)
        try {
            List<Turno> byDur = new ArrayList<>(copia);
            SortUtils.shellSort(byDur, Comparator.comparingInt(t -> t.duracionMin));
            System.out.println("Orden por duracion (shell):");
            byDur.forEach(t -> System.out.println(" - " + t.duracionMin + " | " + t.id));
        } catch (Throwable ex) {
            System.out.println("SortUtils.shellSort no disponible o falló: " + ex.getMessage());
        }
        // Orden por apellido de paciente: quicksort Lomuto (pivote final)
        try {
            // extraer apellido simple (split último token del nombre)
            List<Turno> byApellido = new ArrayList<>(copia);
            SortUtils.quicksort(byApellido, (a, b) -> {
                String an = loader.pacientes.containsKey(a.dniPaciente) ? loader.pacientes.get(a.dniPaciente).getNombre() : a.dniPaciente;
                String bn = loader.pacientes.containsKey(b.dniPaciente) ? loader.pacientes.get(b.dniPaciente).getNombre() : b.dniPaciente;
                String aa = an.contains(" ") ? an.substring(an.lastIndexOf(' ') + 1) : an;
                String ba = bn.contains(" ") ? bn.substring(bn.lastIndexOf(' ') + 1) : bn;
                return aa.compareToIgnoreCase(ba);
            });
            System.out.println("Orden por apellido (quicksort Lomuto):");
            byApellido.forEach(t -> {
                String nombre = loader.pacientes.containsKey(t.dniPaciente) ? loader.pacientes.get(t.dniPaciente).getNombre() : "??";
                String ap = nombre.contains(" ") ? nombre.substring(nombre.lastIndexOf(' ') + 1) : nombre;
                System.out.println(" - " + ap + " | " + nombre + " | " + t.id);
            });
        } catch (Throwable ex) {
            System.out.println("SortUtils.quicksort no disponible o falló: " + ex.getMessage());
        }

        // Punto 9: Auditoría y Undo/Redo de cambios en agenda
        System.out.println("\n=== EJ9: AgendaConHistorial (agendar/reprogramar/undo/redo) ===");
        try {
            AgendaConHistorialImpl hist = new AgendaConHistorialImpl();
            // tomar un turno de loader y aplicar historial
            if (!loader.turnosValidos.isEmpty()) {
                Turno t = loader.turnosValidos.get(0);
                System.out.println("Agendar turno " + t.id + " -> " + hist.agendar(t));
                System.out.println("Reprogramar turno " + t.id + " a +1h -> " + hist.reprogramar(t.id, t.fechaHora.plusHours(1)));
                System.out.println("undo() -> " + hist.undo());
                System.out.println("redo() -> " + hist.redo());
            } else System.out.println("No hay turnos para probar AgendaConHistorial");
        } catch (Throwable ex) {
            System.out.println("AgendaConHistorialImpl no disponible o falló: " + ex.getMessage());
        }

        // Punto 10: Planificador de quirófano (asignación y top-K)
        System.out.println("\n=== EJ10: Planificador de quirófano (procesar y topK) ===");
        try {
            PlanificadorQuirofanoImpl plan = new PlanificadorQuirofanoImpl(3); // 3 quirófanos ejemplo
            // crear solicitudes a partir de algunos turnos existentes (como demo)
            for (int i = 0; i < Math.min(4, loader.turnosValidos.size()); i++) {
                Turno t = loader.turnosValidos.get(i);
                SolicitudCirugia s = new SolicitudCirugia("S-"+t.id, t.matriculaMedico, 60, t.fechaHora.plusDays(1));
                plan.procesar(s);
            }
            System.out.println("Top K medicos (K=2): " + plan.topKMedicosBloqueados(2));
        } catch (Throwable ex) {
            System.out.println("PlanificadorQuirofanoImpl no disponible o falló: " + ex.getMessage());
        }

        System.out.println("\n=== FIN de pruebas integradas en Main ===");
    }
}
