package com.mycompany.proyectoalgoritmo;
import java.util.*;

public class BFS {
    public static List<Integer> bfs(List<List<Integer>> listaAdyacencia, int inicio, int objetivo) {
        int n = listaAdyacencia.size();
        boolean[] visitado = new boolean[n];
        int[] padre = new int[n];
        Arrays.fill(padre, -1);
        
        Queue<Integer> cola = new LinkedList<>();
        visitado[inicio] = true;
        cola.add(inicio);
        
        while (!cola.isEmpty()) {
            int nodoActual = cola.poll();
            
            if (nodoActual == objetivo) {
                return reconstruirCamino(padre, inicio, objetivo);
            }
            
            for (int vecino : listaAdyacencia.get(nodoActual)) {
                if (!visitado[vecino]) {
                    visitado[vecino] = true;
                    padre[vecino] = nodoActual;
                    cola.add(vecino);
                }
            }
        }
        return Collections.emptyList();
    }
    
    public static List<Integer> reconstruirCamino(int[] padre, int inicio, int objetivo) {
        List<Integer> camino = new ArrayList<>();
        for (int actual = objetivo; actual != -1; actual = padre[actual]) {
            camino.add(actual);
            if (actual == inicio) break;
        }
        Collections.reverse(camino);
        if (!camino.isEmpty() && camino.get(0) == inicio) {
            return camino;
        }
        return Collections.emptyList();
    }
}