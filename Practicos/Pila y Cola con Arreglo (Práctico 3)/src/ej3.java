package src;
import java.util.Scanner;

public class ej3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese una cadena: ");
        String texto = sc.nextLine();

        PilaArreglo<String> pila = new PilaArreglo<>(texto.length());
        for (char c : texto.toCharArray()) {
            pila.push(String.valueOf(c));
        }

        System.out.print("Cadena invertida: ");
        while (!pila.isEmpty()) {
            System.out.print(pila.pop());
        }
        System.out.println();
        sc.close();
    }
}
