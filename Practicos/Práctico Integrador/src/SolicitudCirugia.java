import java.time.LocalDateTime;

public class SolicitudCirugia {
    public final String id;
    public final String matricula;
    public final int durMin;
    public final LocalDateTime deadline;

    public SolicitudCirugia(String id, String matricula, int durMin, LocalDateTime deadline) {
        this.id = id; this.matricula = matricula; this.durMin = durMin; this.deadline = deadline;
    }
}
