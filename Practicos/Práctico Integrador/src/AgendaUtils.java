import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidades para agendas: merge y deduplicación.
 * Requisitos:
 * - merge(A,B) en O(|A|+|B|) asumiendo A y B ordenadas por fechaHora
 * - si hay mismo id o (mismo médico y choque exacto de horario), mantener uno y loggear conflicto
 */
public class AgendaUtils {

    /**
     * Merge ordenado de dos listas de Turno (ambas ya ordenadas por fechaHora).
     * Imprime conflictos en stdout y devuelve la lista unificada ordenada.
     */
    public static List<Turno> merge(List<Turno> A, List<Turno> B) {
        List<Turno> out = new ArrayList<>();
        // track seen ids -> evitar duplicados por id
        SimpleHashMapString<Boolean> seenIds = new SimpleHashMapString<>();
        // track seen appointments: key = matriculaMedico + "|" + fechaHora.toString() -> id
        SimpleHashMapString<String> seenAppts = new SimpleHashMapString<>();

        int i = 0, j = 0;
        while (i < A.size() && j < B.size()) {
            Turno ta = A.get(i);
            Turno tb = B.get(j);
            int cmp = ta.fechaHora.compareTo(tb.fechaHora);
            if (cmp < 0) {
                addIfNotDuplicate(ta, out, seenIds, seenAppts);
                i++;
            } else if (cmp > 0) {
                addIfNotDuplicate(tb, out, seenIds, seenAppts);
                j++;
            } else {
                // mismas fechaHora
                if (ta.matriculaMedico.equals(tb.matriculaMedico)) {
                    // conflicto exacto para mismo medico y misma hora
                    System.out.println("CONFLICT: mismo médico " + ta.matriculaMedico +
                            " y choque exacto de horario entre turnos " + ta.id + " y " + tb.id +
                            " a las " + ta.fechaHora);
                    // elegir uno (preferimos ta) y marcarlo
                    addIfNotDuplicate(ta, out, seenIds, seenAppts);
                    // avanzar ambos
                    i++; j++;
                } else {
                    // diferentes médicos, podemos añadir ambos (manteniendo orden estable A then B)
                    addIfNotDuplicate(ta, out, seenIds, seenAppts);
                    addIfNotDuplicate(tb, out, seenIds, seenAppts);
                    i++; j++;
                }
            }
        }

        while (i < A.size()) { addIfNotDuplicate(A.get(i++), out, seenIds, seenAppts); }
        while (j < B.size()) { addIfNotDuplicate(B.get(j++), out, seenIds, seenAppts); }

        return out;
    }

    private static void addIfNotDuplicate(Turno t, List<Turno> out,
                                          SimpleHashMapString<Boolean> seenIds,
                                          SimpleHashMapString<String> seenAppts) {
        if (seenIds.containsKey(t.id)) {
            System.out.println("CONFLICT: turno con id duplicado " + t.id + " (se omite)");
            return;
        }
        String apptKey = t.matriculaMedico + "|" + t.fechaHora.toString();
        String prevId = seenAppts.get(apptKey);
        if (prevId != null) {
            // conflicto: mismo médico y hora ya registrada con otro id
            System.out.println("CONFLICT: choque exacto horario para medico " + t.matriculaMedico +
                    " entre turnos " + prevId + " y " + t.id + " a las " + t.fechaHora);
            return;
        }
        // agregar y marcar
        out.add(t);
        seenIds.put(t.id, true);
        seenAppts.put(apptKey, t.id);
    }
}
