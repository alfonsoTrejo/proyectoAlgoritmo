package com.mycompany.proyectoalgoritmo;
import java.util.*;

public class AStar {
   
    public static List<Integer> aStarSearch(List<List<Integer>> listaAdyacencia, int inicio, int objetivo, int filas, int columnas) {
        int n = listaAdyacencia.size();
       
        double[] puntuacionG = new double[n];
        double[] puntuacionF = new double[n];
        int[] padre = new int[n];
        boolean[] conjuntoCerrado = new boolean[n];
        Arrays.fill(puntuacionG, Double.POSITIVE_INFINITY);
        Arrays.fill(puntuacionF, Double.POSITIVE_INFINITY);
        Arrays.fill(padre, -1);
       
        PriorityQueue<int[]> colaAbierta = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        puntuacionG[inicio] = 0;
        puntuacionF[inicio] = puntuacionG[inicio] + heuristicaManhattan(inicio, objetivo, columnas);
        colaAbierta.add(new int[]{inicio, (int) puntuacionF[inicio]});
        
        while (!colaAbierta.isEmpty()) {
            int actual = colaAbierta.poll()[0];
            
            if (actual == objetivo) {
                return reconstruirCamino(padre, inicio, objetivo);
            }
            
            conjuntoCerrado[actual] = true;
            
            // Explorar vecinos de 'actual'
            for (int vecino : listaAdyacencia.get(actual)) {
                if (conjuntoCerrado[vecino]) continue;
                
                double puntuacionGTentativa = puntuacionG[actual] + 1; // peso = 1
                
                if (puntuacionGTentativa < puntuacionG[vecino]) {
                    padre[vecino] = actual;
                    puntuacionG[vecino] = puntuacionGTentativa;
                    puntuacionF[vecino] = puntuacionGTentativa + heuristicaManhattan(vecino, objetivo, columnas);
                    colaAbierta.add(new int[]{vecino, (int) puntuacionF[vecino]});
                }
            }
        }
    
        return Collections.emptyList();
    }
    
    public static int heuristicaManhattan(int indiceNodo, int indiceMeta, int columnas) {
        int fila1 = indiceNodo / columnas;
        int columna1 = indiceNodo % columnas;
        int fila2 = indiceMeta / columnas;
        int columna2 = indiceMeta % columnas;
        return Math.abs(fila1 - fila2) + Math.abs(columna1 - columna2);
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