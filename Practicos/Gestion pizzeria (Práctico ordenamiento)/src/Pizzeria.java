package src;

import java.util.ArrayList;

public class Pizzeria {

    ArrayList<Pedido> listaPedidos = new ArrayList<>();

    public void agregarPedido(Pedido nuevoPedido) {
        listaPedidos.add(nuevoPedido);
    }

    public boolean eliminarPedido(int id) {
        for (Pedido pedido : listaPedidos) {
            if (pedido.getId() == id) {
                listaPedidos.remove(pedido);
                return true;
            }
        }
        return false;
    }

    public void mostrarPedidos() {
        for (Pedido pedido : listaPedidos) {
            System.out.println("\nID: " + pedido.getId() + "\nNombre: " + pedido.getNombre() + "\nDescripcion: "
                    + pedido.getDescripcion() + "\nTotal: " + pedido.getTotal() + "\nTiempo de Preparacion: " + pedido.getTiempoPreparacion() + " minutos\n");
        }
    }

    public void modificarNombre(int index, String cadena) {
        listaPedidos.get(index).setNombre(cadena);
    }

    public void modificarDescripcion(int index, String cadena) {
        listaPedidos.get(index).setDescripcion(cadena);
    }
}
