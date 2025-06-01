import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int id;  // identificador único del nodo
    private double g = Double.MAX_VALUE;
    private double h = 0;
    private double f = 0;
    private Vertex parent;
    private List<Vertex> neighbors = new ArrayList<>();

    public Vertex(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;
        Vertex v = (Vertex) obj;
        return id == v.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
