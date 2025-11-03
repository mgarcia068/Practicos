/*  Inserciones con rotación doble (caso LR y RL)
    Inserte la secuencia: 30, 10, 20, 40, 35, 37.
    a) Muestre el estado del árbol en cada paso.
    b) Identifique los desbalances (FE = ±2).
    c) Especifique cuándo corresponde rotación doble (LR o RL) y ejecútela.
*/
package src;

public class ej2 {
    public static void main(String[] args) {
        AVLTree arbol = new AVLTree();

        int[] valores = {30, 10, 20, 40, 35, 37};

        for(int val: valores){
            arbol.insertar(val);
            arbol.mostrar();
        }
    }
}