/*  Escribe una clase Nodo que almacene un número entero y un puntero al siguiente nodo.
    ● Implementa un programa que cree tres nodos y los enlace manualmente.
    ● Imprime la lista completa.
 */

package src;

public class ej1 {
    public static void main(String[] args) {
        // Crear nodos
        Nodo nodo1 = new Nodo(10);
        Nodo nodo2 = new Nodo(20);
        Nodo nodo3 = new Nodo(30);

        // Enlazar nodos
        nodo1.siguiente = nodo2;
        nodo2.siguiente = nodo3;

        // Imprimir la lista completa
        Nodo actual = nodo1;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }
}
