/* Secuencia ordenada y “efecto peinar”
    Inserte 5, 10, 15, 20, 25, 30, 35 en un ABB y luego balancee hasta AVL (o
    inserte directamente en AVL, mostrando reequilibrios).
    a) Explique por qué un ABB puro se desbalancea con datos crecientes.
    b) Detalle las rotaciones que hacen que el AVL mantenga altura O(log n).


    a) Porque construye una lista enlazada a la derecha
    En un ABB sin balanceo, si insertamos datos crecientes (como 5, 10, 15...), cada nuevo valor será mayor que el anterior, por lo que siempre se insertará en la rama derecha del nodo anterior.

    b)En un árbol AVL, cada vez que la diferencia de altura entre subárboles supera 1, se realiza una rotación (simple o doble) que:
    Reduce la altura de los subárboles desbalanceados.
    Mantiene el árbol lo más compacto posible.

    Esto garantiza que la altura del árbol AVL es O(logn) para n nodos.
 */
package src;

public class ej3 {
    public static void main(String[] args) {
        AVLTree arbol = new AVLTree();

        int[] valores = {5, 10, 15, 20, 25, 30, 35};

        for(int val: valores){
            arbol.insertar(val);
            arbol.mostrar();
        }
    }
}