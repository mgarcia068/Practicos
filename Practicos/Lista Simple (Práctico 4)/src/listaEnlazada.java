package src;

public class listaEnlazada {
    private Nodo cabeza;

    public listaEnlazada() {
        this.cabeza = null;
    }

    public void insertarInicio(int dato) {
        Nodo nuevoNodo = new Nodo(dato);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;
    }

    public void imprimirLista() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }

    public void insertarFinal(int dato) {
        Nodo nuevoNodo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    public void eliminar(int valor) {
        if (cabeza == null) {
            return; // Lista vacía
        }

        if (cabeza.dato == valor) {
            cabeza = cabeza.siguiente; // Eliminar la cabeza
            return;
        }

        Nodo actual = cabeza;
        while (actual.siguiente != null && actual.siguiente.dato != valor) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente; // Eliminar el nodo
        }
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo cabeza) {
        this.cabeza = cabeza;
    }

    public void invertir() {
        Nodo anterior = null;
        Nodo actual = cabeza;
        Nodo siguiente = null;

        while (actual != null) {
            siguiente = actual.siguiente; // Guardar el siguiente nodo
            actual.siguiente = anterior;  // Invertir el enlace
            anterior = actual;            // Mover anterior y actual un paso adelante
            actual = siguiente;
        }
        cabeza = anterior; // Actualizar la cabeza de la lista
    }
}
