package com.mycompany.proyectoalgoritmo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    
    public static int filas;
    public static int columnas;
    public static List<List<Integer>> listaAdyacencia;
    
    public static List<List<Integer>> castCSVtoList(String rutaArchivo) {
        List<List<Integer>> listaAdyacencia = null;
        try (BufferedReader lectorBuffer = new BufferedReader(new FileReader(rutaArchivo))) {
            
            String linea = lectorBuffer.readLine();
            if (linea == null) {
                throw new IOException("El archivo está vacío");
            }
            linea = linea.replaceAll("\"", "").trim();
            String[] dimensiones = linea.split(",");
            filas = Integer.parseInt(dimensiones[0].trim());
            columnas = Integer.parseInt(dimensiones[1].trim());
            int totalNodos = filas * columnas;
            
            // Inicializar lista de adyacencia con "totalNodos" listas vacías
            listaAdyacencia = new ArrayList<>(totalNodos);
            for (int i = 0; i < totalNodos; i++) {
                listaAdyacencia.add(new ArrayList<>());
            }
            
            String fila;
            while ((fila = lectorBuffer.readLine()) != null) {
                fila = fila.replaceAll("\"", "").trim();
                if (fila.isEmpty()) continue;
                
                // Formato correcto: nodo:vecino1,vecino2
                String[] partes = fila.split(":");
                if (partes.length < 2) continue;
                
                int nodo = Integer.parseInt(partes[0].trim());
                String[] vecinos = partes[1].split(",");
                
                for (String cadenaVecino : vecinos) {
                    cadenaVecino = cadenaVecino.trim();
                    if (cadenaVecino.isEmpty()) continue;
                    
                    int vecino = Integer.parseInt(cadenaVecino);
                  
                    if (nodo >= 0 && nodo < totalNodos && vecino >= 0 && vecino < totalNodos) {
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
   
    public static void imprimirListaAdyacencia(List<List<Integer>> listaAdyacencia) {
        if (listaAdyacencia == null) {
            System.out.println("La lista de adyacencia es null");
            return;
        }
        for (int i = 0; i < listaAdyacencia.size(); i++) {
            System.out.println(i + ": " + listaAdyacencia.get(i));
        }
    }
    
  
    public static void main(String[] args) {
        String rutaArchivo =
            "C:\\Users\\trejo\\OneDrive\\Documentos\\NetBeansProjects\\proyectoAlgoritmo\\"
            + "src\\main\\java\\com\\mycompany\\proyectoalgoritmo\\ejemp;o.csv";
        List<List<Integer>> listaAdyacencia = castCSVtoList(rutaArchivo);
        if (listaAdyacencia != null) {
            imprimirListaAdyacencia(listaAdyacencia);
        } else {
            System.out.println("No se pudo crear la lista de adyacencia desde el CSV.");
        }
    }
}
