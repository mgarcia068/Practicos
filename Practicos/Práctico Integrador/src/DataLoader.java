import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DataLoader {

    public final Map<String,Paciente> pacientes = new HashMap<>();
    public final Map<String,Medico> medicos = new HashMap<>();
    public final List<Turno> turnosValidos = new ArrayList<>();
    public final List<String> errores = new ArrayList<>();
    private final Set<String> idsTurno = new HashSet<>();

    /**
     * Lee CSVs en data/pacientes.csv, data/medicos.csv, data/turnos.csv (ruta relativa al proyecto)
     */
    public void cargarTodo(String carpetaDatos) {
        try {
            cargarPacientes(carpetaDatos + "/pacientes.csv");
            cargarMedicos(carpetaDatos + "/medicos.csv");
            cargarTurnos(carpetaDatos + "/turnos.csv");
        } catch (Exception e) {
            errores.add("Error lectura archivos: " + e.getMessage());
        }
    }

public void cargarPacientes(String path) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (i == 0 || l.trim().isEmpty()) continue; // Saltar encabezado
            String[] tok = l.split(",", -1);
            if (tok.length < 2) { errores.add("Paciente linea invalida: " + l); continue; }
            String dni = tok[0].trim(), nombre = tok[1].trim();
            pacientes.put(dni, new Paciente(dni, nombre));
        }
    }

    public void cargarMedicos(String path) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (i == 0 || l.trim().isEmpty()) continue; // Saltar encabezado
            String[] tok = l.split(",", -1);
            if (tok.length < 3) { errores.add("Medico linea invalida: " + l); continue; }
            String mat = tok[0].trim(), nombre = tok[1].trim(), esp = tok[2].trim();
            medicos.put(mat, new Medico(mat, nombre, esp));
        }
    }

    public void cargarTurnos(String path) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (i == 0 || l.trim().isEmpty()) continue; // Saltar encabezado
            String[] tok = l.split(",", -1);
            if (tok.length < 6) { errores.add("Turno linea invalida: " + l); continue; }
            String id = tok[0].trim();
            if (idsTurno.contains(id)) { errores.add("Turno duplicado id=" + id); continue; }
            String dni = tok[1].trim();
            String mat = tok[2].trim();
            String fechaS = tok[3].trim();
            String durS = tok[4].trim();
            String motivo = tok[5].trim();

            if (!pacientes.containsKey(dni)) { errores.add("Turno " + id + " paciente inexistente: " + dni); continue; }
            if (!medicos.containsKey(mat)) { errores.add("Turno " + id + " medico inexistente: " + mat); continue; }

            LocalDateTime fecha;
            try {
                fecha = LocalDateTime.parse(fechaS);
            } catch (DateTimeParseException ex) {
                errores.add("Turno " + id + " fecha invalida: " + fechaS); continue;
            }

            if (fecha.isBefore(LocalDateTime.now())) { errores.add("Turno " + id + " fecha pasada: " + fechaS); continue; }

            int dur;
            try { dur = Integer.parseInt(durS); }
            catch (NumberFormatException ex) { errores.add("Turno " + id + " duracion invalida: " + durS); continue; }
            if (dur <= 0) { errores.add("Turno " + id + " duracion <= 0"); continue; }

            Turno t = new Turno(id, dni, mat, fecha, dur, motivo);
            turnosValidos.add(t);
            idsTurno.add(id);
        }
    }
}
