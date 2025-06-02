package com.mycompany.proyectoalgoritmo;

import java.util.*;

public class Dijkstra {

    /**
     * Ejecuta Dijkstra en un grafo no dirigido con peso uniforme = 1 en cada arista,
     * usando lista de adyacencia. Retorna la ruta mínima desde start hasta goal
     * (o lista vacía si no existe camino), junto con la distancia mínima.
     *
     * @param adjList Lista de adyacencia; adjList.get(u) es la lista de vecinos de u.
     * @param start   Índice del nodo de inicio (0 ≤ start < N).
     * @param goal    Índice del nodo objetivo (0 ≤ goal < N).
     * @return Un Map con dos entradas:
     *         - "path"  → List<Integer> con la ruta (vacía si no hay).
     *         - "dist"  → Integer con la distancia mínima (Integer.MAX_VALUE si no hay).
     */
    public static Map<String, Object> dijkstra(List<List<Integer>> adjList, int start, int goal) {
        int n = adjList.size();
        int[] dist = new int[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        // Cada elemento en pq es { nodo, distancia_acumulada }

        dist[start] = 0;
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int u = top[0];
            int d = top[1];
            if (visited[u]) continue;
            visited[u] = true;
            if (u == goal) break;

            for (int v : adjList.get(u)) {
                if (!visited[v] && d + 1 < dist[v]) {
                    dist[v] = d + 1;
                    parent[v] = u;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        if (dist[goal] == Integer.MAX_VALUE) {
            resultado.put("path", Collections.emptyList());
            resultado.put("dist", Integer.MAX_VALUE);
        } else {
            resultado.put("path", reconstructPath(parent, start, goal));
            resultado.put("dist", dist[goal]);
        }
        return resultado;
    }

    /**
     * Reconstruye la ruta usando el array de padres, de start a goal.
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
