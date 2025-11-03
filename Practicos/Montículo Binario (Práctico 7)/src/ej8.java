public class ej8 {
        public static void main(String[] args) {
        MinHeapPacientes heap = new MinHeapPacientes(10);

        heap.ingresar(new PacienteMonticulo("Ana", 2));
        heap.ingresar(new PacienteMonticulo("Luis", 1));
        heap.ingresar(new PacienteMonticulo("Marta", 3));
        heap.ingresar(new PacienteMonticulo("Pedro", 2));
        heap.ingresar(new PacienteMonticulo("Sofia", 1));

        System.out.println("Atendiendo pacientes en orden de prioridad:");
        while (!heap.isEmpty()) {
            System.out.println("Atendiendo: " + heap.atender());
        }
    }
}
