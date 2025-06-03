package com.mycompany.proyectoalgoritmo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    
    public static int rows;
    public static int columns;
    public static List<List<Integer>> adjList;

    public static List<List<Integer>> castCSVtoList(String filePath) {
        List<List<Integer>> adjList  = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            
            String line = br.readLine();
            if (line == null) {
                throw new IOException("El archivo está vacío");
            }
            line = line.replaceAll("\"", "").trim();
            String[] dimensions = line.split(",");
            rows = Integer.parseInt(dimensions[0].trim());
            columns = Integer.parseInt(dimensions[1].trim());
            int totalNodes = rows * columns;

            // Inicializar lista de adyacencia con "totalNodes" listas vacías
            adjList  = new ArrayList<>(totalNodes);
            for (int i = 0; i < totalNodes; i++) {
                adjList .add(new ArrayList<>());
            }

            
            String row;
            while ((row = br.readLine()) != null) {
                row = row.replaceAll("\"", "").trim();
                if (row.isEmpty()) continue;

                // Formato correcto: nodo:vecino1,vecino2
                String[] partes = row.split(":");
                if (partes.length < 2) continue;

                int nodo = Integer.parseInt(partes[0].trim());
                String[] vecinos = partes[1].split(",");

                for (String vStr : vecinos) {
                    vStr = vStr.trim();
                    if (vStr.isEmpty()) continue;

                    int vecino = Integer.parseInt(vStr);
                  
                    if (nodo >= 0 && nodo < totalNodes && vecino >= 0 && vecino < totalNodes) {
                       
                        adjList .get(nodo).add(vecino);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear número: " + e.getMessage());
        }

        return adjList ;
    }

   
    public static void printAdjList(List<List<Integer>> listaAdy) {
        if (listaAdy == null) {
            System.out.println("La lista de adyacencia es null");
            return;
        }
        for (int i = 0; i < listaAdy.size(); i++) {
            System.out.println(i + ": " + listaAdy.get(i));
        }
    }

    // main para probar el lector
    public static void main(String[] args) {
        String filePath =
            "C:\\Users\\trejo\\OneDrive\\Documentos\\NetBeansProjects\\proyectoAlgoritmo\\"
            + "src\\main\\java\\com\\mycompany\\proyectoalgoritmo\\ejemp;o.csv";

        List<List<Integer>> adjList = castCSVtoList(filePath);
        if (adjList != null) {
            printAdjList(adjList);
        } else {
            System.out.println("No se pudo crear la lista de adyacencia desde el CSV.");
        }
    }
}
