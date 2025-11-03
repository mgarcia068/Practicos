public class SalaEspera {
    private final String[] datos;
    private int front;
    private int rear;
    private int cantidad;

    public SalaEspera(int capacidad) {
        datos = new String[capacidad];
        front = 0; rear = -1; cantidad = 0;
    }

    public void llega(String dni) {
        if (cantidad < datos.length) {
            rear = (rear + 1) % datos.length;
            datos[rear] = dni;
            cantidad++;
        } else {
            front = (front + 1) % datos.length;
            rear = (rear + 1) % datos.length;
            datos[rear] = dni;
        }
    }

    public String atiende() {
        if (isEmpty()) return null;
        String r = datos[front];
        front = (front + 1) % datos.length;
        cantidad--;
        return r;
    }

    public String peek() {
        return isEmpty() ? null : datos[front];
    }

    public int size() { return cantidad; }
    public boolean isEmpty() { return cantidad == 0; }
    public boolean isFull() { return cantidad == datos.length; }

    public void printEstado() {
        System.out.print("FRONT -> [");
        for (int i = 0; i < cantidad; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(datos[(front + i) % datos.length]);
        }
        System.out.println("] <- REAR");
    }
}
