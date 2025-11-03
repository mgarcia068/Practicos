package src;

public class ej7{
    public static void main(String[] args){
        ColaArreglo<String> cola = new ColaArreglo<>(10);

        for (int i = 1; i <= 5; i++) {
            cola.enqueue("Doc" + i);
        }

        System.out.println("Cola de impresión inicial:");
        cola.mostrar();

        for (int i = 0; i < 3; i++) {
            System.out.println("Imprimiendo " + cola.dequeue());
        }

        System.out.println("Cola después de imprimir:");
        cola.mostrar();
    }
}
