import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementación simplificada de AVL para agenda por medico.
 * Node.key = turno.fechaHora
 */
public class AvlAgenda implements AgendaMedico {

    private static class Node {
        Turno turno;
        Node left, right;
        int height;
        Node(Turno t) { turno = t; height = 1; }
    }

    private Node root;
    private int size;
    // map id -> fechaHora (para poder borrar por id en O(log n) combinando búsqueda por key)
    private final SimpleHashMapString<LocalDateTime> indexById = new SimpleHashMapString<>();

    public AvlAgenda() { this.root = null; this.size = 0; }
    private int height(Node n) { return n == null ? 0 : n.height; }
    private int balance(Node n) { return n == null ? 0 : height(n.left) - height(n.right); }
    private Node rotateRight(Node y) {
        Node x = y.left; Node T2 = x.right;
        x.right = y; y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }
    private Node rotateLeft(Node x) {
        Node y = x.right; Node T2 = y.left;
        y.left = x; x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    // find predecessor (max < key)
    private Node floorNode(Node node, LocalDateTime key) {
        Node res = null;
        while (node != null) {
            if (node.turno.fechaHora.isEqual(key)) return node;
            if (node.turno.fechaHora.isBefore(key)) { res = node; node = node.right; }
            else node = node.left;
        }
        return res;
    }

    // find successor (min >= key)
    private Node ceilNode(Node node, LocalDateTime key) {
        Node res = null;
        while (node != null) {
            if (!node.turno.fechaHora.isBefore(key)) { res = node; node = node.left; }
            else node = node.right;
        }
        return res;
    }

    @Override
    public boolean agendar(Turno t) {
        // check duplicates by id
        if (indexById.containsKey(t.id)) return false;

        // find predecessor and successor to check overlaps
        Node pred = floorNode(root, t.fechaHora);
        if (pred != null) {
            if (!pred.turno.fin().isBefore(t.fechaHora)) {
                // pred overlaps (its end >= t.start)
                return false;
            }
        }
        Node succ = ceilNode(root, t.fechaHora);
        if (succ != null) {
            if (!t.fin().isBefore(succ.turno.fechaHora)) {
                // overlaps with successor
                return false;
            }
        }

        root = insert(root, t);
        indexById.put(t.id, t.fechaHora);
        size++;
        return true;
    }

    private Node insert(Node node, Turno t) {
        if (node == null) return new Node(t);
        if (t.fechaHora.isBefore(node.turno.fechaHora))
            node.left = insert(node.left, t);
        else
            node.right = insert(node.right, t);

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int bal = balance(node);

        // LL
        if (bal > 1 && t.fechaHora.isBefore(node.left.turno.fechaHora)) return rotateRight(node);
        // RR
        if (bal < -1 && t.fechaHora.isAfter(node.right.turno.fechaHora)) return rotateLeft(node);
        // LR
        if (bal > 1 && t.fechaHora.isAfter(node.left.turno.fechaHora)) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // RL
        if (bal < -1 && t.fechaHora.isBefore(node.right.turno.fechaHora)) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    @Override
    public boolean cancelar(String id) {
        LocalDateTime key = indexById.get(id);
        if (key == null) return false;
        root = delete(root, key);
        indexById.remove(id);
        size--;
        return true;
    }

    private Node delete(Node node, LocalDateTime key) {
        if (node == null) return null;
        if (key.isBefore(node.turno.fechaHora)) node.left = delete(node.left, key);
        else if (key.isAfter(node.turno.fechaHora)) node.right = delete(node.right, key);
        else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    node = null;
                    return null;
                } else node = temp;
            } else {
                Node succ = minValueNode(node.right);
                node.turno = succ.turno; 
                node.right = delete(node.right, succ.turno.fechaHora);
            }
        }
        if (node == null) return null;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int bal = balance(node);
        if (bal > 1 && balance(node.left) >= 0) return rotateRight(node);
        if (bal > 1 && balance(node.left) < 0) { node.left = rotateLeft(node.left); return rotateRight(node); }
        if (bal < -1 && balance(node.right) <= 0) return rotateLeft(node);
        if (bal < -1 && balance(node.right) > 0) { node.right = rotateRight(node.right); return rotateLeft(node); }
        return node;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    @Override
    public Optional<Turno> siguiente(LocalDateTime t) {
        Node node = ceilNode(root, t);
        return node == null ? Optional.empty() : Optional.of(node.turno);
    }

    @Override
    public Optional<LocalDateTime> primerHueco(LocalDateTime t0, int durMin, LocalDateTime inicioDia, LocalDateTime finDia) {
        LocalDateTime cursor = t0;
        for (int dias = 0; dias < 365; dias++) {
            LocalDate currentDate = cursor.toLocalDate();
            LocalDateTime dayStart = LocalDateTime.of(currentDate, inicioDia.toLocalTime());
            LocalDateTime dayEnd = LocalDateTime.of(currentDate, finDia.toLocalTime());
            if (cursor.isBefore(dayStart)) cursor = dayStart;
            if (cursor.plusMinutes(durMin).isAfter(dayEnd)) {
                cursor = dayStart.plusDays(1);
                continue;
            }
            Node succ = ceilNode(root, cursor);
            if (succ == null || cursor.plusMinutes(durMin).isBefore(succ.turno.fechaHora) || cursor.plusMinutes(durMin).isEqual(succ.turno.fechaHora)) {
                return Optional.of(cursor);
            } else {
                cursor = succ.turno.fin();
            }
        }
        return Optional.empty();
    }

    public Optional<LocalDateTime> primerHueco(LocalDateTime t0, int durMin) {
        LocalDateTime inicioDia = LocalDateTime.of(t0.toLocalDate(), java.time.LocalTime.of(8,0));
        LocalDateTime finDia = LocalDateTime.of(t0.toLocalDate(), java.time.LocalTime.of(18,0));
        return primerHueco(t0, durMin, inicioDia, finDia);
    }

    public int size() { return size; }
    public void printInOrder() { printInOrder(root); System.out.println(); }
    private void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.println(n.turno);
        printInOrder(n.right);
    }
}
