import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around AvlAgenda that records actions for undo/redo.
 * Keeps a map id->Turno to be able to restore state for undo.
 */
public class AgendaConHistorialImpl implements AgendaConHistorial {
    private final AvlAgenda delegate = new AvlAgenda();
    private final SimpleHashMapString<Turno> byId = new SimpleHashMapString<>();

    private static class Action {
        enum Type { AGENDAR, CANCELAR, REPROGRAMAR }
        final Type type;
        final Turno before; // for CANCELAR or REPROGRAMAR
        final Turno after;  // for AGENDAR or REPROGRAMAR
        Action(Type type, Turno before, Turno after) { this.type = type; this.before = before; this.after = after; }
    }

    private final List<Action> undoStack = new ArrayList<>();
    private final List<Action> redoStack = new ArrayList<>();

    private void pushUndo(Action a) {
        undoStack.add(a);
        redoStack.clear();
    }

    @Override
    public boolean agendar(Turno t) {
        boolean ok = delegate.agendar(t);
        if (ok) {
            byId.put(t.id, t);
            pushUndo(new Action(Action.Type.AGENDAR, null, t));
        }
        return ok;
    }

    @Override
    public boolean cancelar(String id) {
        Turno t = byId.get(id);
        if (t == null) return false;
        boolean ok = delegate.cancelar(id);
        if (ok) {
            byId.remove(id);
            pushUndo(new Action(Action.Type.CANCELAR, t, null));
        }
        return ok;
    }

    @Override
    public boolean reprogramar(String idTurno, LocalDateTime nuevaFecha) {
        Turno old = byId.get(idTurno);
        if (old == null) return false;
        // create new turno copy with new fecha
        Turno nuevo = new Turno(old.id, old.dniPaciente, old.matriculaMedico, nuevaFecha, old.duracionMin, old.motivo);
        // remove old
        boolean removed = delegate.cancelar(idTurno);
        if (!removed) return false;
        // try to insert new
        boolean ok = delegate.agendar(nuevo);
        if (!ok) {
            // restore old
            delegate.agendar(old);
            return false;
        }
        // update map
        byId.put(nuevo.id, nuevo);
        pushUndo(new Action(Action.Type.REPROGRAMAR, old, nuevo));
        return true;
    }

    @Override
    public boolean undo() {
        if (undoStack.isEmpty()) return false;
        Action a = undoStack.remove(undoStack.size() - 1);
        switch (a.type) {
            case AGENDAR:
                // undo agendar -> cancelar after
                delegate.cancelar(a.after.id);
                byId.remove(a.after.id);
                redoStack.add(a);
                return true;
            case CANCELAR:
                // undo cancelar -> reinsert before
                delegate.agendar(a.before);
                byId.put(a.before.id, a.before);
                redoStack.add(a);
                return true;
            case REPROGRAMAR:
                // undo reprogramar -> remove 'after' and re-add 'before'
                delegate.cancelar(a.after.id);
                delegate.agendar(a.before);
                byId.put(a.before.id, a.before);
                redoStack.add(a);
                return true;
        }
        return false;
    }

    @Override
    public boolean redo() {
        if (redoStack.isEmpty()) return false;
        Action a = redoStack.remove(redoStack.size() - 1);
        switch (a.type) {
            case AGENDAR:
                delegate.agendar(a.after);
                byId.put(a.after.id, a.after);
                undoStack.add(a);
                return true;
            case CANCELAR:
                delegate.cancelar(a.before.id);
                byId.remove(a.before.id);
                undoStack.add(a);
                return true;
            case REPROGRAMAR:
                // apply reprogram: remove before, add after
                delegate.cancelar(a.before.id);
                delegate.agendar(a.after);
                byId.put(a.after.id, a.after);
                undoStack.add(a);
                return true;
        }
        return false;
    }

    @Override
    public java.util.Optional<Turno> siguiente(LocalDateTime t) { return delegate.siguiente(t); }

    @Override
    public java.util.Optional<LocalDateTime> primerHueco(LocalDateTime t0, int durMin, LocalDateTime inicioDia, LocalDateTime finDia) {
        return delegate.primerHueco(t0, durMin, inicioDia, finDia);
    }
}
