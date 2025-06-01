import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private Map<Node, Map<Node, Integer>> adjacency;
    private int vertixQty;

    public Graph(int vertixQty) {
        this.vertixQty = vertixQty;
        adjacency = new HashMap<>();

        for (int i = 0; i < vertixQty; i++) {
            adjacency.put(new Node(i), new HashMap<>());
        }
    }

    public void addArista(int sourceId, int destinationId, int weight) {
        Node source = new Node(sourceId);
        Node destination = new Node(destinationId);

        adjacency.get(source).put(destination, weight);
        adjacency.get(destination).put(source, weight);
    }

    public int[][] getAdjacencyVertix() {
        int[][] matrix = new int[vertixQty][vertixQty];

        for (Node source : adjacency.keySet()) {
            int i = source.getData();
            for (Node destination : adjacency.get(source).keySet()) {
                int j = destination.getData();
                matrix[i][j] = adjacency.get(source).get(destination);
            }
        }

        return matrix;
    }

    public void printAdjacencyVertix() {
        int[][] matrix = getAdjacencyVertix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static Set<Node> CSVtoGraph(String rutaCSV, Map<Integer, Node> mapaNodos) {
        Set<Node> todosLosNodos = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;
            int lineNumber = 0;

            while ((linea = br.readLine()) != null) {
                if (lineNumber == 0) {
                    lineNumber++;
                    continue; // Saltar primera línea (dimensión)
                }

                String[] partes = linea.split(":");
                int idNodo = Integer.parseInt(partes[0].trim());

                Node nodo = mapaNodos.computeIfAbsent(idNodo, Node::new);

                if (partes.length > 1) {
                    String[] conexiones = partes[1].split(",");
                    for (String conexion : conexiones) {
                        int idVecino = Integer.parseInt(conexion.trim());
                        Node vecino = mapaNodos.computeIfAbsent(idVecino, Node::new);
                        nodo.addDescendent(vecino);
                    }
                }

                todosLosNodos.add(nodo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return todosLosNodos;
    }
}
