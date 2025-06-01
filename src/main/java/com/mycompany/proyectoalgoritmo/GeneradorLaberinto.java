package com.mycompany.proyectoalgoritmo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GeneradorLaberinto {
    


    public static int[][] generar(int filas, int columnas) {
        int totalCeldas = filas * columnas;
        int[][] matrizAdy = new int[totalCeldas][totalCeldas];
        boolean[][] visitado = new boolean[filas][columnas];
        Random aleatorio = new Random();
        Stack<int[]> pila = new Stack<>();

        // === Generación del “espacio principal” con DFS aleatorio ===
        pila.push(new int[]{0, 0});
        visitado[0][0] = true;

        while (!pila.isEmpty()) {
            int[] celdaActual = pila.peek();
            int filaActual = celdaActual[0];
            int colActual = celdaActual[1];

            // Recolectar vecinos no visitados de la celda actual
            List<int[]> vecinosNoVisitados = new ArrayList<>();
            if (filaActual > 0 && !visitado[filaActual - 1][colActual]) {
                vecinosNoVisitados.add(new int[]{filaActual - 1, colActual});
            }
            if (filaActual < filas - 1 && !visitado[filaActual + 1][colActual]) {
                vecinosNoVisitados.add(new int[]{filaActual + 1, colActual});
            }
            if (colActual > 0 && !visitado[filaActual][colActual - 1]) {
                vecinosNoVisitados.add(new int[]{filaActual, colActual - 1});
            }
            if (colActual < columnas - 1 && !visitado[filaActual][colActual + 1]) {
                vecinosNoVisitados.add(new int[]{filaActual, colActual + 1});
            }

            if (vecinosNoVisitados.isEmpty()) {
                // Si no hay vecinos libres, retrocedemos
                pila.pop();
            } else {
                // Elegimos un vecino al azar
                int[] vecino = vecinosNoVisitados.get(aleatorio.nextInt(vecinosNoVisitados.size()));
                int filaVecino = vecino[0];
                int colVecino = vecino[1];

                // Convertir coordenadas a índices lineales
                int indiceActual = filaActual * columnas + colActual;
                int indiceVecino = filaVecino * columnas + colVecino;

                // Abrir pasillo entre la celda actual y el vecino
                matrizAdy[indiceActual][indiceVecino] = 1;
                matrizAdy[indiceVecino][indiceActual] = 1;

                // Marcar vecino como visitado y apilar
                visitado[filaVecino][colVecino] = true;
                pila.push(new int[]{filaVecino, colVecino});
            }
        }

        // === Agregar “islas” (celdas completamente aisladas) ===
        // Calculamos cuántas islas generar (por ejemplo, 10% del total de celdas)
        int numIslas = Math.max(1, totalCeldas / 10);
        for (int i = 0; i < numIslas; i++) {
            int celdaIsla = aleatorio.nextInt(totalCeldas);
            // Desconectar la celdaIsla de todos sus vecinos, haciéndola una isla
            for (int j = 0; j < totalCeldas; j++) {
                matrizAdy[celdaIsla][j] = 0;
                matrizAdy[j][celdaIsla] = 0;
            }
        }

        // === Agregar “caminos falsos” (pequeños pasillos muertos) ===
        // Se crean algunos pasillos cortos que terminan en callejón sin salida.
        // Para ello, intentamos varias veces:
        int intentos = totalCeldas / 5; // número de intentos de crear pasillos falsos
        for (int k = 0; k < intentos; k++) {
            // Escogemos un nodo de inicio aleatorio que ya esté en el laberinto
            int nodoInicio = aleatorio.nextInt(totalCeldas);
            // Hallamos sus vecinos potenciales (arriba/abajo/izq/der) en la cuadrícula
            int filaInicio = nodoInicio / columnas;
            int colInicio = nodoInicio % columnas;

            List<int[]> vecinosPotenciales = new ArrayList<>();
            if (filaInicio > 0)                    vecinosPotenciales.add(new int[]{filaInicio - 1, colInicio});
            if (filaInicio < filas - 1)           vecinosPotenciales.add(new int[]{filaInicio + 1, colInicio});
            if (colInicio > 0)                    vecinosPotenciales.add(new int[]{filaInicio, colInicio - 1});
            if (colInicio < columnas - 1)         vecinosPotenciales.add(new int[]{filaInicio, colInicio + 1});

            if (vecinosPotenciales.isEmpty()) continue;

            // Elegir un vecino aleatorio y crear un pasillo de longitud 2 (nodoInicio → vecino → “cul-de-sac”)
            int[] vecino = vecinosPotenciales.get(aleatorio.nextInt(vecinosPotenciales.size()));
            int filaVec = vecino[0];
            int colVec = vecino[1];
            int nodoVec = filaVec * columnas + colVec;

            // Solo si aún no están conectados en el grafo principal (evitamos chocar con pasillos existentes)
            if (matrizAdy[nodoInicio][nodoVec] == 0) {
                // Conectar nodoInicio ↔ nodoVec
                matrizAdy[nodoInicio][nodoVec] = 1;
                matrizAdy[nodoVec][nodoInicio] = 1;

                // Ahora buscamos un “final” para el cul-de-sac desde nodoVec: un vecino de nodoVec que NO sea nodoInicio
                List<int[]> vecinosParaCulDeSac = new ArrayList<>();
                if (filaVec > 0 && !(filaVec - 1 == filaInicio && colVec == colInicio))
                    vecinosParaCulDeSac.add(new int[]{filaVec - 1, colVec});
                if (filaVec < filas - 1 && !(filaVec + 1 == filaInicio && colVec == colInicio))
                    vecinosParaCulDeSac.add(new int[]{filaVec + 1, colVec});
                if (colVec > 0 && !(filaVec == filaInicio && colVec - 1 == colInicio))
                    vecinosParaCulDeSac.add(new int[]{filaVec, colVec - 1});
                if (colVec < columnas - 1 && !(filaVec == filaInicio && colVec + 1 == colInicio))
                    vecinosParaCulDeSac.add(new int[]{filaVec, colVec + 1});

                if (!vecinosParaCulDeSac.isEmpty()) {
                    // Elegimos uno de esos vecinos como extremo del pasillo falso
                    int[] culDeSac = vecinosParaCulDeSac.get(aleatorio.nextInt(vecinosParaCulDeSac.size()));
                    int filaCul = culDeSac[0];
                    int colCul = culDeSac[1];
                    int nodoCul = filaCul * columnas + colCul;

                    // Solo conectar si no existe ya conexión (para no romper grafo principal)
                    if (matrizAdy[nodoVec][nodoCul] == 0) {
                        matrizAdy[nodoVec][nodoCul] = 1;
                        matrizAdy[nodoCul][nodoVec] = 1;
                        // No seguimos extendiendo más: así queda un pasillo acabado en callejón sin salida.
                    }
                }
            }
        }

        return matrizAdy;
    }

    public static int[][] leerCSV(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leer primera línea con dimensiones "filas,columnas"
            String linea = br.readLine();
            if (linea == null) {
                throw new IOException("El archivo está vacío");
            }
            String[] dims = linea.split(",");
            int filas = Integer.parseInt(dims[0].trim());
            int columnas = Integer.parseInt(dims[1].trim());
            int total = filas * columnas;

            // Inicializar matriz de adyacencia vacía
            int[][] matrizAdy = new int[total][total];

            // Leer lista de adyacencia desde la segunda línea en adelante
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                // Formato esperado: "origen:vecino1,vecino2,vecino3"
                String[] partes = linea.split(":");
                int origen = Integer.parseInt(partes[0].trim());
                String[] vecinos = partes[1].split(",");

                for (String vStr : vecinos) {
                    int v = Integer.parseInt(vStr.trim());
                    matrizAdy[origen][v] = 1;
                    matrizAdy[v][origen] = 1;
                }
            }

            return matrizAdy;
        }
    }
    // main para hacer pruebas
    public static void main(String[] args) throws Exception {
        int[][] laberinto = GeneradorLaberinto.leerCSV("C:\\Users\\trejo\\OneDrive\\Documentos\\NetBeansProjects\\proyectoAlgoritmo\\src\\main\\java\\com\\mycompany\\proyectoalgoritmo\\ejemp;o.csv");
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                System.out.print(laberinto[i][j] + " ");
            }
            System.out.println();
        }
    }
}
