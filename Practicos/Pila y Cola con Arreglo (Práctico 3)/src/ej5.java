package src;
import java.util.Scanner;

public class ej5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese una palabra: ");
        String palabra = sc.nextLine().toLowerCase();

        PilaArreglo<String> pila = new PilaArreglo<>(palabra.length());
        ColaArreglo<String> cola = new ColaArreglo<>(palabra.length());

        for (char c : palabra.toCharArray()) {
            pila.push(String.valueOf(c));
            cola.enqueue(String.valueOf(c));
        }

        boolean palindromo = true;
        while (!pila.isEmpty()) {
            if (!pila.pop().equals(cola.dequeue())) {
                palindromo = false;
                break;
            }
        }

        System.out.println(palindromo ? "Es palíndromo" : "No es palíndromo");
        sc.close();
    }
}
