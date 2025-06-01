package com.mycompany.proyectoalgoritmo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    public static int[][] castCSVtoMatrix(String filePath) {
        int[][] matrix = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Get dimension from the first line
            String line = br.readLine();
            
            // Limpiar la línea de comillas y espacios
            line = line.replaceAll("\"", "").trim();
            String[] dimensions = line.split(",");
            int rows = Integer.parseInt(dimensions[0].trim());
            int columns = Integer.parseInt(dimensions[1].trim());
            int totalNodes = rows * columns;
            
            // Initialize matrix
            matrix = new int[totalNodes][totalNodes];
            
            // Read CSV from the second line onwards
            String row;
            while ((row = br.readLine()) != null) {
                if (row.isEmpty()) continue;
                
                // Limpiar la línea de comillas y espacios extra
                row = row.replaceAll("\"", "").trim();
                
                // Split by comma to get all values in the row
                String[] values = row.split(",");
                
                if (values.length < 2) continue;
                
                // First value is the node
                String nodeStr = values[0].trim();
                if (nodeStr.isEmpty()) continue;
                
                int node = Integer.parseInt(nodeStr);
                
                // Rest of the values are the neighbors
                for (int i = 1; i < values.length; i++) {
                    String neighborStr = values[i].trim();
                    if (!neighborStr.isEmpty()) {
                        int neighbor = Integer.parseInt(neighborStr);
                        
                        // Verificar que los índices estén dentro del rango
                        if (node < totalNodes && neighbor < totalNodes && node >= 0 && neighbor >= 0) {
                            matrix[node][neighbor] = 1;
                            matrix[neighbor][node] = 1; // Make it bidirectional
                        }
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
        return matrix;
    }
    
    public static void printCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {}
    }
    
    public static void printMatrix(int[][] matrix) {
        if (matrix == null) {
            System.out.println("La matriz es null");
            return;
        }
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}