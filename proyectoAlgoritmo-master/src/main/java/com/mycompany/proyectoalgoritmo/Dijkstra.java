package com.mycompany.proyectoalgoritmo;
import java.util.*;

public class Dijkstra {
  
    public static Map<String, Object> dijkstra(List<List<Integer>> listaAdyacencia, int inicio, int objetivo) {
        int n = listaAdyacencia.size();
        int[] distancia = new int[n];
        int[] padre = new int[n];
        boolean[] visitado = new boolean[n];
        Arrays.fill(distancia, Integer.MAX_VALUE);
        Arrays.fill(padre, -1);
        PriorityQueue<int[]> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
     
        distancia[inicio] = 0;
        colaPrioridad.add(new int[]{inicio, 0});
        
        while (!colaPrioridad.isEmpty()) {
            int[] superior = colaPrioridad.poll();
            int nodoActual = superior[0];
            int distanciaActual = superior[1];
            
            if (visitado[nodoActual]) continue;
            visitado[nodoActual] = true;
            
            if (nodoActual == objetivo) break;
            
            for (int vecino : listaAdyacencia.get(nodoActual)) {
                if (!visitado[vecino] && distanciaActual + 1 < distancia[vecino]) {
                    distancia[vecino] = distanciaActual + 1;
                    padre[vecino] = nodoActual;
                    colaPrioridad.add(new int[]{vecino, distancia[vecino]});
                }
            }
        }
        
        Map<String, Object> resultado = new HashMap<>();
        if (distancia[objetivo] == Integer.MAX_VALUE) {
            resultado.put("path", Collections.emptyList());
            resultado.put("dist", Integer.MAX_VALUE);
        } else {
            resultado.put("path", reconstruirCamino(padre, inicio, objetivo));
            resultado.put("dist", distancia[objetivo]);
        }
        return resultado;
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