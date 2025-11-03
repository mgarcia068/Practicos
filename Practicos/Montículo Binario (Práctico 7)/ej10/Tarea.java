public class Tarea {
    String descripcion;
    int prioridad; 

    public Tarea(String descripcion, int prioridad) {
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return descripcion + " (prioridad " + prioridad + ")";
    }
}