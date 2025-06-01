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

                String[] parts = row.split(":");
                int node = Integer.parseInt(parts[0].trim());

                if (parts.length > 1) {
                    String[] neighbors = parts[1].split(",");
                    for (String neighborStr : neighbors) {
                        int neighbor = Integer.parseInt(neighborStr.trim());
                        matrix[node][neighbor] = 1;
                        matrix[neighbor][node] = 1;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
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
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
