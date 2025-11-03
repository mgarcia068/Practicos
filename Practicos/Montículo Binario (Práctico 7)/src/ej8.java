public class ej8 {
        public static void main(String[] args) {
        MinHeapPacientes heap = new MinHeapPacientes(10);

        heap.ingresar(new Paciente("Ana", 2));
        heap.ingresar(new Paciente("Luis", 1));
        heap.ingresar(new Paciente("Marta", 3));
        heap.ingresar(new Paciente("Pedro", 2));
        heap.ingresar(new Paciente("Sofia", 1));

        System.out.println("Atendiendo pacientes en orden de prioridad:");
        while (!heap.isEmpty()) {
            System.out.println("Atendiendo: " + heap.atender());
        }
    }
}
