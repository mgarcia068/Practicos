public class Paciente {
    String nombre;
    int prioridad; 

    public Paciente(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return nombre + " (prioridad " + prioridad + ")";
    }
}
