package com.mycompany.proyectoalgoritmo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /**
     * Lee el CSV y retorna la lista de adyacencia:
     *  - El primer renglón indica "filas,columnas".
     *  - A partir del segundo renglón: "nodo:vecino1,vecino2,...".
     *
     * Nota: NO agregamos la arista inversa aquí. Se asume que el CSV ya define ambas direcciones.
     */
    public static List<List<Integer>> castCSVtoList(String filePath) {
        List<List<Integer>> listaAdyacencia = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Leer primera línea con dimensiones "filas,columnas"
            String line = br.readLine();
            if (line == null) {
                throw new IOException("El archivo está vacío");
            }
            line = line.replaceAll("\"", "").trim();
            String[] dimensions = line.split(",");
            int filas = Integer.parseInt(dimensions[0].trim());
            int columnas = Integer.parseInt(dimensions[1].trim());
            int totalNodes = filas * columnas;

            // Inicializar lista de adyacencia con "totalNodes" listas vacías
            listaAdyacencia = new ArrayList<>(totalNodes);
            for (int i = 0; i < totalNodes; i++) {
                listaAdyacencia.add(new ArrayList<>());
            }

            // Leer el resto de líneas para poblar la lista de adyacencia
            String row;
            while ((row = br.readLine()) != null) {
                row = row.replaceAll("\"", "").trim();
                if (row.isEmpty()) continue;

                // Formato esperado: "nodo:vecino1,vecino2,..."
                String[] partes = row.split(":");
                if (partes.length < 2) continue;

                int nodo = Integer.parseInt(partes[0].trim());
                String[] vecinos = partes[1].split(",");

                for (String vStr : vecinos) {
                    vStr = vStr.trim();
                    if (vStr.isEmpty()) continue;

                    int vecino = Integer.parseInt(vStr);
                    // Validar rango
                    if (nodo >= 0 && nodo < totalNodes && vecino >= 0 && vecino < totalNodes) {
                        // Solo agregamos la arista en la dirección indicada por el CSV.
                        // NO hacemos listaAdyacencia.get(vecino).add(nodo);
                        listaAdyacencia.get(nodo).add(vecino);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear número: " + e.getMessage());
        }

        return listaAdyacencia;
    }

    /**
     * Imprime por consola cada entrada de la lista de adyacencia:
     * 0: [1, 3]
     * 1: [0, 2, 4]
     * ...
     */
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
