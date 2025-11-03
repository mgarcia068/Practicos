package src;
class NodoAVL {
    int altura;
    int valor;
    NodoAVL izquierdo;
    NodoAVL derecho;

    public NodoAVL(int valor){
        this.valor = valor;
        this.altura = 1;
    }
}