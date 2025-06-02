package com.mycompany.proyectoalgoritmo;

import java.util.*;

/**
 * Implementación de A* que utiliza lista de adyacencia (List<List<Integer>>)
 * en un grafo no ponderado (peso = 1) organizado sobre una cuadrícula de filas×cols.
 */
public class AStar {

    /**
     * Ejecuta A* Search usando lista de adyacencia.
     *
     * @param adjList Lista de adyacencia; adjList.get(u) es la lista de vecinos de u.
     * @param start   Índice del nodo de inicio (0 ≤ start < filas*cols).
     * @param goal    Índice del nodo objetivo (0 ≤ goal < filas*cols).
     * @param filas   Número de filas en la cuadrícula.
     * @param cols    Número de columnas en la cuadrícula.
     * @return Ruta (List<Integer>) desde start hasta goal; lista vacía si no existe camino.
     */
    public static List<Integer> aStarSearch(List<List<Integer>> adjList, int start, int goal, int filas, int cols) {
        int n = adjList.size();
        // gScore[u] = costo desde start hasta u; fScore[u] = gScore[u] + heurística(u, goal)
        double[] gScore = new double[n];
        double[] fScore = new double[n];
        int[] parent = new int[n];
        boolean[] closedSet = new boolean[n];

        Arrays.fill(gScore, Double.POSITIVE_INFINITY);
        Arrays.fill(fScore, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);

        // Comparador para la cola prioritaria basado en fScore
        PriorityQueue<int[]> openPQ = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));

        gScore[start] = 0;
        fScore[start] = gScore[start] + heuristicManhattan(start, goal, cols);
        openPQ.add(new int[]{start, (int) fScore[start]});

        while (!openPQ.isEmpty()) {
            int current = openPQ.poll()[0];
            if (current == goal) {
                return reconstructPath(parent, start, goal);
            }
            closedSet[current] = true;

            // Explorar vecinos de 'current'
            for (int neighbor : adjList.get(current)) {
                if (closedSet[neighbor]) continue;

                double tentativeG = gScore[current] + 1; // peso = 1
                if (tentativeG < gScore[neighbor]) {
                    parent[neighbor] = current;
                    gScore[neighbor] = tentativeG;
                    fScore[neighbor] = tentativeG + heuristicManhattan(neighbor, goal, cols);
                    openPQ.add(new int[]{neighbor, (int) fScore[neighbor]});
                }
            }
        }

        // No se encontró camino
        return Collections.emptyList();
    }

    /**
     * Heurística Manhattan para índices en cuadrícula filas×cols.
     * @param idxNodo Índice de la celda actual.
     * @param idxMeta Índice de la celda objetivo.
     * @param cols    Número de columnas para convertir índice→(fila,columna).
     * @return Distancia Manhattan entre idxNodo y idxMeta.
     */
    private static int heuristicManhattan(int idxNodo, int idxMeta, int cols) {
        int fila1 = idxNodo / cols;
        int col1 = idxNodo % cols;
        int fila2 = idxMeta / cols;
        int col2 = idxMeta % cols;
        return Math.abs(fila1 - fila2) + Math.abs(col1 - col2);
    }

    /**
     * Reconstruye la ruta desde 'start' hasta 'goal' usando el array 'parent'.
     */
    private static List<Integer> reconstructPath(int[] parent, int start, int goal) {
        List<Integer> path = new ArrayList<>();
        for (int at = goal; at != -1; at = parent[at]) {
            path.add(at);
            if (at == start) break;
        }
        Collections.reverse(path);
        if (!path.isEmpty() && path.get(0) == start) {
            return path;
        }
        return Collections.emptyList();
    }
}
