package src;

import java.util.ArrayList;
import java.util.Random;

public class TiempoOrdenamiento {

    public static ArrayList<Pedido> generarPedidos(int cantidad) {
        ArrayList<Pedido> lista = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < cantidad; i++) {
            Pedido p = new Pedido(
                    "Cliente" + rand.nextInt(cantidad), 
                    "Descripción" + i, 
                    i,
                    rand.nextInt(5000),
                    rand.nextInt(60) 
            );
            lista.add(p);
        }
        return lista;
    }

    public static long medirInsertionSort(ArrayList<Pedido> pedidos) {
        ArrayList<Pedido> copia = new ArrayList<>(pedidos); 
        long start = System.nanoTime();
        Ordenador.ordenarPorTiempoPreparacion(copia);
        long end = System.nanoTime();
        return end - start;
    }

    public static long medirShellsort(ArrayList<Pedido> pedidos) {
        ArrayList<Pedido> copia = new ArrayList<>(pedidos);
        long start = System.nanoTime();
        Ordenador.ordenarPorTotal(copia);
        long end = System.nanoTime();
        return end - start;
    }
    public static long medirQuicksort(ArrayList<Pedido> pedidos) {
        ArrayList<Pedido> copia = new ArrayList<>(pedidos);
        long start = System.nanoTime();
        Ordenador.ordenarPorNombre(copia);
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        int[] tamaños = { 100, 1000, 10000 };

        for (int tamaño : tamaños) {
            ArrayList<Pedido> listaPedidos = generarPedidos(tamaño);

            long tiempoInsertion = medirInsertionSort(listaPedidos);
            long tiempoShellsort = medirShellsort(listaPedidos);
            long tiempoQuicksort = medirQuicksort(listaPedidos);

            System.out.println("Tamaño lista: " + tamaño);
            System.out.println("Insertion Sort (Tiempo): " + tiempoInsertion / 1_000_000 + " ms");
            System.out.println("Shellsort (Precio Total): " + tiempoShellsort / 1_000_000 + " ms");
            System.out.println("Quicksort (Nombre): " + tiempoQuicksort / 1_000_000 + " ms");
            System.out.println("-----------------------------");
        }
    }
}
