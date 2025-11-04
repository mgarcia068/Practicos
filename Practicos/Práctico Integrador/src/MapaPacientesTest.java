public class MapaPacientesTest {
    private static void check(boolean cond, String msg) {
        System.out.println((cond ? "PASS: " : "FAIL: ") + msg);
        if (!cond) throw new RuntimeException("Test failed: " + msg);
    }

    public static void main(String[] args) {
        System.out.println("=== MapaPacientes tests ===");

        // Test 1: colisiones forzadas (capacity=1 -> todo en mismo bucket)
        MapaPacientesImpl m1 = new MapaPacientesImpl(1);
        m1.put("A", new Paciente("A","Ana"));
        m1.put("B", new Paciente("B","Bea"));
        m1.put("C", new Paciente("C","Caro"));
        check(m1.size() == 3, "size after inserts in capacity=1 == 3");
        check(m1.get("A").getNombre().equals("Ana"), "get A");
        check(m1.get("B").getNombre().equals("Bea"), "get B");

        // eliminar cabeza (A fue insertado primero, la estructura inserta al frente, por lo que A puede no ser cabeza)
        // Para garantizar pruebas de eliminación en cabeza/medio/cola, probamos secuencialmente removiendo varios elementos
        boolean removedA = m1.remove("A");
        check(removedA && m1.get("A") == null && m1.size() == 2, "remove A (head/middle depending) and size==2");

        boolean removedC = m1.remove("C");
        check(removedC && m1.get("C") == null && m1.size() == 1, "remove C (tail/middle) and size==1");

        boolean removedB = m1.remove("B");
        check(removedB && m1.get("B") == null && m1.size() == 0, "remove B last and size==0");

        // Test 2: rehash when exceeding load factor
        MapaPacientesImpl m2 = new MapaPacientesImpl(4); // threshold = 3 (4*0.75)
        m2.put("1", new Paciente("1","P1"));
        m2.put("2", new Paciente("2","P2"));
        m2.put("3", new Paciente("3","P3"));
        // still before rehash
        check(m2.size() == 3, "size 3 before rehash threshold");
        m2.put("4", new Paciente("4","P4")); // should trigger rehash
        check(m2.size() == 4, "size 4 after insert triggering rehash");
        check(m2.get("1") != null && m2.get("2") != null && m2.get("3") != null && m2.get("4") != null,
                "all keys present after rehash");

        // Test keys() iterable
        int cnt = 0;
        for (String k : m2.keys()) { cnt++; }
        check(cnt == m2.size(), "keys() returns correct number of keys");

        // Test containsKey
        check(m2.containsKey("3"), "containsKey true for '3'");
        check(!m2.containsKey("X"), "containsKey false for 'X'");

        System.out.println("All tests passed.");
    }
}
