import java.util.Scanner;

public class ej10 {
    public static void main(String[] args) {
        MinHeapTareas heap = new MinHeapTareas(20);
        Scanner sc = new Scanner(System.in);
        int opc;

        do {
            System.out.println("\n--- AGENDA DE TAREAS ---");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Ver próxima tarea urgente");
            System.out.println("3. Completar tarea más urgente");
            System.out.println("4. Mostrar todas las tareas");
            System.out.println("5. Salir");
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1 -> {
                    System.out.print("Descripción: ");
                    String desc = sc.nextLine();
                    System.out.print("Prioridad (1=alta, 2=media, 3=baja): ");
                    int prio = sc.nextInt();
                    sc.nextLine();
                    heap.agregar(new Tarea(desc, prio));
                }
                case 2 -> {
                    System.out.println("Próxima tarea: " + heap.peek());
                }
                case 3 -> {
                    System.out.println("Completando: " + heap.completar());
                }
                case 4 -> heap.mostrar();
            }
        } while (opc != 5);

        sc.close();
    }
}