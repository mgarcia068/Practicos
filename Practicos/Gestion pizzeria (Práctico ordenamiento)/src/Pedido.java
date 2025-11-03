package src;

public class Pedido {

    int id;
    String nombre;
    String descripcion;
    int total;
    int tiempoPreparacion; 

    

    public Pedido(String nombre, String descripcion, int id, int total, int tiempoPreparacion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
        this.total = total;
        this.tiempoPreparacion = tiempoPreparacion;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }


}
