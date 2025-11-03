package src;
class Nodo {
    int altura;
    int valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(int valor){
        this.valor = valor;
        this.altura = 1;
    }
}