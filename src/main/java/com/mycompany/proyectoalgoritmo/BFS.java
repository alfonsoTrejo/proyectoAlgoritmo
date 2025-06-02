package com.mycompany.proyectoalgoritmo;

import java.util.*;

public class BFS {

    /**
     * Ejecuta BFS (Breadth-First Search) en un grafo no ponderado representado
     * como lista de adyacencia. Retorna la ruta desde start hasta goal
     * (o lista vacía si no existe camino).
     *
     * @param adjList Lista de adyacencia; adjList.get(u) es la lista de vecinos de u.
     * @param start   Índice del nodo de inicio (0 ≤ start < N).
     * @param goal    Índice del nodo objetivo (0 ≤ goal < N).
     * @return Lista de índices que conforman la ruta de start a goal (ambos incluidos),
     *         o lista vacía si no hay camino.
     */
    public static List<Integer> bfs(List<List<Integer>> adjList, int start, int goal) {
        int n = adjList.size();
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (u == goal) {
                return reconstructPath(parent, start, goal);
            }
            for (int v : adjList.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * Reconstruye la ruta desde 'start' hasta 'goal' usando el array 'parent'.
     *
     * @param parent Array donde parent[v] es el nodo desde el que se llegó a v.
     * @param start  Nodo inicial.
     * @param goal   Nodo destino.
     * @return Lista de índices que va de start a goal.
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
