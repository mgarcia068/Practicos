import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendaMedico {
    boolean agendar(Turno t);

    boolean cancelar(String id);

    Optional<Turno> siguiente(LocalDateTime t);

    Optional<LocalDateTime> primerHueco(LocalDateTime t0, int durMin,
            LocalDateTime inicioDia, LocalDateTime finDia);
}
