package src;

class AVLTree {
    NodoAVL raiz;

    // Obtener altura
    int altura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    // Calcular FE = altura izquierda - derecha
    int obtenerFE(NodoAVL nodo) {
        return nodo == null ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Actualizar altura
    void actualizarAltura(NodoAVL nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    // Rotación simple a la derecha (LL)
    NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        actualizarAltura(y);
        actualizarAltura(x);

        System.out.println("Rotación LL (Derecha) en nodo " + y.valor);
        return x;
    }

    // Rotación simple a la izquierda (RR)
    NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        actualizarAltura(x);
        actualizarAltura(y);

        System.out.println("Rotación RR (Izquierda) en nodo " + x.valor);
        return y;
    }

    // Insertar nodo
    NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null) {
            System.out.println("Insertando " + valor);
            return new NodoAVL(valor);
        }

        if (valor < nodo.valor)
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        else if (valor > nodo.valor)
            nodo.derecho = insertar(nodo.derecho, valor);
        else
            return nodo; // No duplicados

        actualizarAltura(nodo);

        int fe = obtenerFE(nodo);

        // Detectar desbalances y aplicar rotaciones

        // Caso LL
        if (fe > 1 && valor < nodo.izquierdo.valor)
            return rotarDerecha(nodo);

        // Caso RR
        if (fe < -1 && valor > nodo.derecho.valor)
            return rotarIzquierda(nodo);

        // Otros casos (LR, RL) no se presentan en esta secuencia

        return nodo;
    }

    public void insertar(int valor) {
        raiz = insertar(raiz, valor);
    }

    // Mostrar el árbol (inorden con alturas y FE)
    void mostrar(NodoAVL nodo) {
        if (nodo != null) {
            mostrar(nodo.izquierdo);
            System.out.println("Nodo: " + nodo.valor +
                               ", Altura: " + nodo.altura +
                               ", FE: " + obtenerFE(nodo));
            mostrar(nodo.derecho);
        }
    }

    public void mostrar() {
        System.out.println("Árbol actual (inorden):");
        mostrar(raiz);
        System.out.println("-----------------------");
    }
}
