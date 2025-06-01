import java.util.List;
import java.util.ArrayList;

public class Node {

    private int data;
    private List<Node> descendent;

    // Fields for AStar
    private double g = Double.MAX_VALUE;
    private double h = 0;
    private double f = 0;
    private Node parent;

    public Node(){
        this.data = 0;
        this.descendent = new ArrayList<>();
    }

    public Node(int data){
        this.data = data;
        this.descendent = new ArrayList<>();
    }

    public void addDescendent(Node descendent){
        this.descendent.add(descendent);
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public List<Node> getDescendent() {
        return descendent;
    }

    public void setDescendent(List<Node> descendent) {
        this.descendent = descendent;
    }

    // Getters y setters para A*
    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
