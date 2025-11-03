public class Medico {
    public final String matricula;
    public final String nombre;
    public final String especialidad;

    public Medico(String matricula, String nombre, String especialidad) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return matricula + " - " + nombre + " (" + especialidad + ")";
    }
}
