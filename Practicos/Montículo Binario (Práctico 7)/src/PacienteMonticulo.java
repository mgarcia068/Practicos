public class PacienteMonticulo {
    String nombre;
    int prioridad; 

    public PacienteMonticulo(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return nombre + " (prioridad " + prioridad + ")";
    }
}
