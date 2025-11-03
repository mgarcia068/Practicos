import java.time.LocalDateTime;

public class Turno {
    public final String id;
    public final String dniPaciente;
    public final String matriculaMedico;
    public LocalDateTime fechaHora;
    public final int duracionMin;
    public final String motivo;

    public Turno(String id, String dniPaciente, String matriculaMedico,
                 LocalDateTime fechaHora, int duracionMin, String motivo) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.matriculaMedico = matriculaMedico;
        this.fechaHora = fechaHora;
        this.duracionMin = duracionMin;
        this.motivo = motivo;
    }

    public LocalDateTime fin() {
        return fechaHora.plusMinutes(duracionMin);
    }

    @Override
    public String toString() {
        return id + " | " + dniPaciente + " | " + fechaHora + " -> " + fin() + " | " + motivo;
    }
}
