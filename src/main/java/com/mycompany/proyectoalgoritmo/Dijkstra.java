package com.mycompany.proyectoalgoritmo;
import java.util.Arrays;

public class Dijkstra {

    private int V;

    public Dijkstra(int V) {
        this.V = V;
    }

    public int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    public void dijkstra(int[][] graph, int origin) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        int[] parents = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        Arrays.fill(parents, -1);

        dist[origin] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);
            if (u == -1) break; // No more reachable vertices
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != 0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    parents[v] = u;
                }
            }
        }
        printResult(dist, parents, origin);
    }

    public void printResult(int[] dist, int[] parents, int origin) {
        System.out.println("Node\tDistance from node " + origin + "\tRoute");
        for (int i = 0; i < V; i++) {
            System.out.print(i + "\t\t\t" + dist[i] + "\t\t");
            printRoute(parents, i);
            System.out.println();
        }
    }

    private void printRoute(int[] parents, int j) {
        if (parents[j] == -1) {
            System.out.print(j);
            return;
        }
        printRoute(parents, parents[j]);
        System.out.print(" -> " + j);
    }
}
