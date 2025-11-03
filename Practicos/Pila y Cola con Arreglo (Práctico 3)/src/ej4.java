package src;
public class ej4 {
    public static void main(String[] args) {
        ColaArreglo<String> cola = new ColaArreglo<>(10);

        cola.enqueue("Ana");
        cola.enqueue("Luis");
        cola.enqueue("Marta");
        cola.enqueue("Pedro");

        System.out.println("Cola inicial:");
        cola.mostrar();

        cola.dequeue();
        cola.dequeue();

        System.out.println("Cola después de atender:");
        cola.mostrar();
    }
}
