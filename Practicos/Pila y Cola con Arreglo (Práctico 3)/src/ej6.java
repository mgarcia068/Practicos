package src;
import java.util.Stack;

public class ej6 {
    public static void main(String[] args) {
        Stack<String> deshacer = new Stack<>();
        Stack<String> rehacer = new Stack<>();

        deshacer.push("Escribir 'Hola'");
        deshacer.push("Agregar 'Mundo'");
        deshacer.push("Borrar 'o'");
        deshacer.push("Escribir '!'");
        deshacer.push("Copiar texto");

        System.out.println("\nPila Deshacer inicial: " + deshacer);
        System.out.println("Pila Rehacer inicial: " + rehacer);

        System.out.println("\n-- Deshacer 2 acciones --");
        for (int i = 0; i < 2; i++) {
            String accion = deshacer.pop();
            rehacer.push(accion);
        }

        System.out.println("Deshacer: " + deshacer);
        System.out.println("Rehacer: " + rehacer);

        System.out.println("\n-- Rehacer 1 acción --");
        deshacer.push(rehacer.pop());

        System.out.println("Deshacer: " + deshacer);
        System.out.println("Rehacer: " + rehacer);
    }
}
