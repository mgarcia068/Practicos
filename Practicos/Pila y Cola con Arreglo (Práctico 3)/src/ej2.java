package src;
public class ej2 {
    public static void main(String[] args) {
        ColaArreglo<Integer> cola = new ColaArreglo<>(5);

        cola.enqueue(1);
        cola.enqueue(2);
        cola.enqueue(3);
        cola.enqueue(4);
        cola.mostrar();

        System.out.println("Dequeue: " + cola.dequeue());
        cola.mostrar();
    }
}
