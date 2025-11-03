package src;

public class ej8 {
    public static void main(String[] args) {
        int capacidad = 5;
        String[] cola = new String[capacidad];
        int inicio = 0;
        int fin = 0;
        int cantidad = 0;

        for (int i = 1; i <= 8; i++) {
            String llamada = "Llamada" + i;
            if (cantidad < capacidad) {
                cola[fin] = llamada;
                fin = (fin + 1) % capacidad;
                cantidad++;
            } else {
                cola[fin] = llamada;
                fin = (fin + 1) % capacidad;
                inicio = (inicio + 1) % capacidad;
            }
        }

        System.out.println("\nEstado final de la cola circular:");
        for (int i = 0; i < cantidad; i++) {
            System.out.print(cola[(inicio + i) % capacidad] + " ");
        }
        System.out.println();
    }
}
