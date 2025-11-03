import java.time.LocalDateTime;

public class Recordatorio {
    public final String id;
    public LocalDateTime fecha;
    public final String dniPaciente;
    public final String mensaje;

    public Recordatorio(String id, LocalDateTime fecha, String dniPaciente, String mensaje) {
        this.id = id;
        this.fecha = fecha;
        this.dniPaciente = dniPaciente;
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return id + " | " + fecha + " | " + dniPaciente + " | " + mensaje;
    }
}
