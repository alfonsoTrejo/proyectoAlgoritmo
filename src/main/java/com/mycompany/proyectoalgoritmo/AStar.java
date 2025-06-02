package com.mycompany.proyectoalgoritmo;

import java.util.*;

public class AStar {

    public static List<Node> aStarSearch(Node start, Node goal, Map<Node, Map<Node, Integer>> graph) {
        Set<Node> closedSet = new HashSet<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));

        start.setG(0);
        start.setF(start.getG() + heuristic(start, goal));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Node neighbor : graph.getOrDefault(current, new HashMap<>()).keySet()) {
                if (closedSet.contains(neighbor)) continue;

                double tentativeG = current.getG() + graph.get(current).get(neighbor);

                if (tentativeG < neighbor.getG()) {
                    neighbor.setParent(current);
                    neighbor.setG(tentativeG);
                    neighbor.setH(heuristic(neighbor, goal));
                    neighbor.setF(neighbor.getG() + neighbor.getH());

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList(); // No hay camino
    }

    private static List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    // Si tienes coordenadas (x, y), usa eso aquí. Si no, puedes dejarlo en 0.
    private static double heuristic(Node a, Node b) {
        // return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()); // Manhattan
        return 0; // Heurística nula (Dijkstra)
    }
}
