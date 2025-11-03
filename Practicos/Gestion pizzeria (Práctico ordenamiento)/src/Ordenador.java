package src;

import java.util.ArrayList;

public class Ordenador {
    public static void ordenarPorTiempoPreparacion(ArrayList<Pedido> listaPedidos) {
        for (int i = 1; i < listaPedidos.size(); i++) {
            Pedido key = listaPedidos.get(i);
            int j = i - 1;

            while (j >= 0 && listaPedidos.get(j).getTiempoPreparacion() > key.getTiempoPreparacion()) {
                listaPedidos.set(j + 1, listaPedidos.get(j));
                j = j - 1;
            }
            listaPedidos.set(j + 1, key);
        }
    }

    public static void ordenarPorTotal(ArrayList<Pedido> listaPedidos) {
        int n = listaPedidos.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Pedido temp = listaPedidos.get(i);
                int j;
                for (j = i; j >= gap && listaPedidos.get(j - gap).getTotal() > temp.getTotal(); j -= gap) {
                    listaPedidos.set(j, listaPedidos.get(j - gap));
                }
                listaPedidos.set(j, temp);
            }
        }
    }

    public static void ordenarPorNombre(ArrayList<Pedido> listaPedidos) {
        quicksort(listaPedidos, 0, listaPedidos.size() - 1);
    }

    private static void quicksort(ArrayList<Pedido> lista, int low, int high) {
        if (low < high) {
            int pi = partition(lista, low, high);

            quicksort(lista, low, pi - 1);
            quicksort(lista, pi + 1, high);
        }
    }

    private static int partition(ArrayList<Pedido> lista, int low, int high) {
        String pivot = lista.get(high).getNombre();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (lista.get(j).getNombre().compareToIgnoreCase(pivot) <= 0) {
                i++;
                Pedido temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        Pedido temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);

        return i + 1;
    }
}
