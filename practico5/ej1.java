/* Inserciones y FE paso a paso (caso LL y RR)
    Inserte en un AVL la secuencia: 30, 20, 10, 40, 50, 60.
    a) Dibuje el árbol tras cada inserción.
    b) Calcule alturas y factor de equilibrio (FE) de cada nodo en cada paso.
    c) Indique qué rotación se aplica en cada desbalance (LL o RR) y por qué.
*/

public class Main(){
    public static void main(String[] args) {
        AVLTree arbol = new AVLTree();

        int[] valores = {30, 20, 10, 40, 50, 60};

        for(int val: valores){
            arbol.insertar();
            arbol.mostrar();
        }
    }
}