package com.mycompany.proyectoalgoritmo;

import java.util.*;

public class BFS {

    // Devuelve la ruta desde inicio hasta objetivo si existe
    public static List<Node> bfs(Node start, Node goal) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        start.setParent(null);
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(goal)) {
                return reconstructPath(goal);
            }

            for (Node neighbor : current.getDescendent()) {
                if (!visited.contains(neighbor)) {
                    neighbor.setParent(current);
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return Collections.emptyList(); // No se encontró camino
    }

    private static List<Node> reconstructPath(Node target) {
        List<Node> path = new ArrayList<>();
        for (Node at = target; at != null; at = at.getParent()) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
