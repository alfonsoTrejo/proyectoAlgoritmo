package com.mycompany.proyectoalgoritmo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InterfazGrafica extends JFrame {

    // Ahora guardamos la lista de adyacencia actual
    private List<List<Integer>> adjListActual;
    private int filasActual;
    private int colsActual;

    // Componentes de la interfaz
    private JButton btnLeerCSV;

    private JButton btnGrafoManual;
    private JButton btnLaberintoAleatorio;
    private JTextArea textAreaContenido;
    private JScrollPane scrollPaneContenido;
    private JButton btnDijkstra;
    private JButton btnBFS;
    private JButton btnAStar;
    private JButton btnEjecutar; // Botón para ejecutar algoritmos
    private JTextArea textAreaTiempos;
    private JScrollPane scrollPaneTiempos;
    private JFileChooser fileChooser;

    public InterfazGrafica() {
        inicializarComponentes();
        configurarVentana();
        agregarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        btnLeerCSV = new JButton("Seleccionar Archivo");
        btnLeerCSV.setPreferredSize(new Dimension(150, 30));
        btnLeerCSV.setFont(new Font("Arial", Font.BOLD, 12));
        btnLeerCSV.setBackground(new Color(70, 130, 180));
        btnLeerCSV.setForeground(Color.WHITE);
        btnLeerCSV.setFocusPainted(false);



        btnGrafoManual = new JButton("Grafo Manual");
        btnGrafoManual.setPreferredSize(new Dimension(140, 30));
        btnGrafoManual.setFont(new Font("Arial", Font.BOLD, 12));
        btnGrafoManual.setBackground(new Color(255, 140, 0));
        btnGrafoManual.setForeground(Color.WHITE);
        btnGrafoManual.setFocusPainted(false);

        btnLaberintoAleatorio = new JButton("Laberinto Aleatorio");
        btnLaberintoAleatorio.setPreferredSize(new Dimension(160, 30));
        btnLaberintoAleatorio.setFont(new Font("Arial", Font.BOLD, 12));
        btnLaberintoAleatorio.setBackground(new Color(220, 20, 60));
        btnLaberintoAleatorio.setForeground(Color.WHITE);
        btnLaberintoAleatorio.setFocusPainted(false);

        textAreaContenido = new JTextArea(15, 40);
        textAreaContenido.setFont(new Font("Courier New", Font.PLAIN, 12));
        textAreaContenido.setEditable(false);
        textAreaContenido.setBackground(new Color(248, 248, 248));
        textAreaContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPaneContenido = new JScrollPane(textAreaContenido);
        scrollPaneContenido.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Contenido del Grafo",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        scrollPaneContenido.setPreferredSize(new Dimension(450, 300));

        btnDijkstra = new JButton("Dijkstra");
        btnBFS = new JButton("BFS");
        btnAStar = new JButton("A*");

        configurarBotonAlgoritmo(btnDijkstra);
        configurarBotonAlgoritmo(btnBFS);
        configurarBotonAlgoritmo(btnAStar);

        btnEjecutar = new JButton("Ejecutar"); // Inicializar botón
        btnEjecutar.setPreferredSize(new Dimension(120, 35));
        btnEjecutar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEjecutar.setBackground(new Color(70, 130, 180));
        btnEjecutar.setForeground(Color.WHITE);
        btnEjecutar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        textAreaTiempos = new JTextArea(8, 30);
        textAreaTiempos.setFont(new Font("Courier New", Font.PLAIN, 11));
        textAreaTiempos.setEditable(false);
        textAreaTiempos.setBackground(new Color(245, 255, 245));
        textAreaTiempos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textAreaTiempos.setText("Tiempos de ejecución aparecerán aquí...\n");

        scrollPaneTiempos = new JScrollPane(textAreaTiempos);
        scrollPaneTiempos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Tiempos de Ejecución",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        scrollPaneTiempos.setPreferredSize(new Dimension(350, 250));

        fileChooser = new JFileChooser();
        String directorioProyecto = "C:\\Users\\rauli\\Desktop\\ALGORITMOS\\ProyectoFinal\\proyectoAlgoritmo-main\\src\\main\\java\\com\\mycompany\\proyectoalgoritmo";
        File dirInicial = new File(directorioProyecto);
        if (dirInicial.exists()) {
            fileChooser.setCurrentDirectory(dirInicial);
        }
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos CSV", "csv"));
    }

    private void configurarVentana() {
        setTitle("Sistema de Laberintos - Representación con Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void agregarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSuperior.setBackground(new Color(240, 240, 240));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.add(btnLeerCSV);
        panelSuperior.add(btnGrafoManual);
        panelSuperior.add(btnLaberintoAleatorio);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelCentral.add(scrollPaneContenido, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel panelAlgoritmos = new JPanel(new GridLayout(4, 1, 5, 5));
        panelAlgoritmos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Seleccionar Algoritmos",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        panelAlgoritmos.add(btnDijkstra);
        panelAlgoritmos.add(btnBFS);
        panelAlgoritmos.add(btnAStar);
        panelAlgoritmos.add(btnEjecutar); // Añadir botón Ejecutar

        panelDerecho.add(panelAlgoritmos);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(scrollPaneTiempos);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(new Color(240, 240, 240));
        JLabel labelInfo = new JLabel("Sistema de Análisis de Laberintos usando Grafos");
        labelInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        labelInfo.setForeground(Color.GRAY);
        panelInferior.add(labelInfo);
        add(panelInferior, BorderLayout.SOUTH);

        pack();
    }

    private void configurarEventos() {
        btnLeerCSV.addActionListener(e -> leerArchivoCSV());

        btnGrafoManual.addActionListener(e -> crearGrafoManual());
        btnLaberintoAleatorio.addActionListener(e -> generarLaberintoAleatorio());

        btnDijkstra.addActionListener(e -> toggleAlgoritmo(btnDijkstra, "Dijkstra"));
        btnBFS.addActionListener(e -> toggleAlgoritmo(btnBFS, "BFS"));
        btnAStar.addActionListener(e -> toggleAlgoritmo(btnAStar, "A*"));

        btnEjecutar.addActionListener(e -> ejecutarAlgoritmos()); // Acción para ejecutar

        actualizarTiempos("Selecciona los algoritmos que desees usar\n");
    }

    private void leerArchivoCSV() {
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        String ruta = file.getAbsolutePath();

        // 1) Leer y mostrar contenido crudo
        try {
            String contenido = leerContenidoArchivo(ruta);
            textAreaContenido.setText("=== CONTENIDO DEL ARCHIVO CSV ===\n" + contenido);
            textAreaContenido.setCaretPosition(0);
        } catch (IOException ex) {
            actualizarTiempos("Error al leer el CSV: " + ex.getMessage() + "\n");
            return;
        }

        // 2) Obtener dimensiones y adjList usando castCSVtoList
        List<List<Integer>> lista = CSVReader.castCSVtoList(ruta);
        if (lista == null) {
            actualizarTiempos("Error: El CSV no tiene formato válido\n");
            return;
        }
        filasActual = CSVReader.rows;
        colsActual = CSVReader.columns;
        adjListActual = lista;

        // 3) Mostrar la lista de adyacencia
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTA DE ADYACENCIA GENERADA ===\n");
        sb.append("Dimensiones: ").append(filasActual).append("x").append(colsActual)
          .append(" (Total nodos=").append(adjListActual.size()).append(")\n\n");
        for (int i = 0; i < adjListActual.size(); i++) {
            sb.append(i).append(": ").append(adjListActual.get(i)).append("\n");
        }
        textAreaContenido.setText(sb.toString());
        textAreaContenido.setCaretPosition(0);

        actualizarTiempos("Archivo CSV cargado: " + file.getName() + "\n");
        actualizarTiempos("Dimensiones: " + filasActual + "x" + colsActual + "\n");
        actualizarTiempos("Listo para ejecutar algoritmos\n\n");
    }

 
    private void crearGrafoManual() {
        String dims = JOptionPane.showInputDialog(this,
            "Ingrese dimensiones (filas,columnas) para grafo manual, ej: 3,3:",
            "Grafo Manual", JOptionPane.QUESTION_MESSAGE);
        if (dims == null || dims.trim().isEmpty()) return;

        try {
            String[] partes = dims.split(",");
            int filas = Integer.parseInt(partes[0].trim());
            int cols = Integer.parseInt(partes[1].trim());
            int totalNodos = filas * cols;

            JTextArea inputArea = new JTextArea(15, 40);
            inputArea.setFont(new Font("Courier New", Font.PLAIN, 12));
            inputArea.setText("Ingrese conexiones (nodo:vecino1,vecino2,...)\n" +
                "Nodos válidos: 0 a " + (totalNodos - 1) + "\n\n");

            JScrollPane scrollPane = new JScrollPane(inputArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            int res = JOptionPane.showConfirmDialog(this, scrollPane,
                "Definir Conexiones del Grafo Manual", JOptionPane.OK_CANCEL_OPTION);
            if (res != JOptionPane.OK_OPTION) return;

            List<List<Integer>> nuevaAdj = new ArrayList<>(totalNodos);
            for (int i = 0; i < totalNodos; i++) {
                nuevaAdj.add(new ArrayList<>());
            }

            for (String line : inputArea.getText().split("\n")) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("Ingrese")) continue;
                String[] partesLinea = line.split(":");
                if (partesLinea.length < 2) continue;
                int nodo = Integer.parseInt(partesLinea[0].trim());
                String[] vecinos = partesLinea[1].split(",");
                for (String vStr : vecinos) {
                    vStr = vStr.trim();
                    if (vStr.isEmpty()) continue;
                    int vec = Integer.parseInt(vStr);
                    if (nodo >= 0 && nodo < totalNodos && vec >= 0 && vec < totalNodos) {
                        nuevaAdj.get(nodo).add(vec);
                    }
                }
            }

            filasActual = filas;
            colsActual = cols;
            adjListActual = nuevaAdj;

            StringBuilder sb = new StringBuilder();
            sb.append("=== GRAFO MANUAL ===\n");
            sb.append("Dimensiones: ").append(filas).append("x").append(cols)
              .append(" (Total nodos=").append(totalNodos).append(")\n\n");
            for (int i = 0; i < adjListActual.size(); i++) {
                sb.append(i).append(": ").append(adjListActual.get(i)).append("\n");
            }
            textAreaContenido.setText(sb.toString());
            textAreaContenido.setCaretPosition(0);

            actualizarTiempos("Grafo manual creado\n");
            actualizarTiempos("Dimensiones: " + filas + "x" + cols + "\n");
            actualizarTiempos("Listo para ejecutar algoritmos\n\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error en formato de dimensiones o conexiones.\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarLaberintoAleatorio() {
        String dims = JOptionPane.showInputDialog(this,
            "Ingrese dimensiones para laberinto aleatorio (filas,columnas), ej: 5,5:",
            "Laberinto Aleatorio", JOptionPane.QUESTION_MESSAGE);
        if (dims == null || dims.trim().isEmpty()) return;

        try {
            String[] partes = dims.split(",");
            int filas = Integer.parseInt(partes[0].trim());
            int cols = Integer.parseInt(partes[1].trim());
            if (filas < 2 || cols < 2 || filas > 20 || cols > 20) {
                JOptionPane.showMessageDialog(this,
                    "Dimensiones entre 2x2 y 20x20",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int totalNodos = filas * cols;
            List<List<Integer>> nuevaAdj = new ArrayList<>(totalNodos);
            for (int i = 0; i < totalNodos; i++) {
                nuevaAdj.add(new ArrayList<>());
            }

            Random rand = new Random();
            boolean[][] visitado = new boolean[filas][cols];
            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{0, 0});
            visitado[0][0] = true;
            while (!stack.isEmpty()) {
                int[] actual = stack.peek();
                int r = actual[0], c = actual[1];
                List<int[]> vecinos = new ArrayList<>();
                if (r > 0 && !visitado[r - 1][c]) vecinos.add(new int[]{r - 1, c});
                if (r < filas - 1 && !visitado[r + 1][c]) vecinos.add(new int[]{r + 1, c});
                if (c > 0 && !visitado[r][c - 1]) vecinos.add(new int[]{r, c - 1});
                if (c < cols - 1 && !visitado[r][c + 1]) vecinos.add(new int[]{r, c + 1});

                if (vecinos.isEmpty()) {
                    stack.pop();
                } else {
                    int[] elegido = vecinos.get(rand.nextInt(vecinos.size()));
                    int nr = elegido[0], nc = elegido[1];
                    int idxU = r * cols + c;
                    int idxV = nr * cols + nc;
                    nuevaAdj.get(idxU).add(idxV);
                    nuevaAdj.get(idxV).add(idxU);
                    visitado[nr][nc] = true;
                    stack.push(new int[]{nr, nc});
                }
            }

            filasActual = filas;
            colsActual = cols;
            adjListActual = nuevaAdj;

            StringBuilder sb = new StringBuilder();
            sb.append("=== LABERINTO ALEATORIO ===\n");
            sb.append("Dimensiones: ").append(filas).append("x").append(cols)
              .append(" (Total nodos=").append(totalNodos).append(")\n\n");
            for (int i = 0; i < Math.min(10, totalNodos); i++) {
                sb.append(i).append(": ").append(adjListActual.get(i)).append("\n");
            }
            if (totalNodos > 10) sb.append("...\n");
            textAreaContenido.setText(sb.toString());
            textAreaContenido.setCaretPosition(0);

            actualizarTiempos("Laberinto aleatorio generado\n");
            actualizarTiempos("Dimensiones: " + filas + "x" + cols + "\n");
            actualizarTiempos("Listo para ejecutar algoritmos\n\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error en formato de dimensiones.\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarAlgoritmos() {
        if (!tieneGrafoCargado()) {
            JOptionPane.showMessageDialog(this,
                "Debes cargar o generar un grafo primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int totalNodos = filasActual * colsActual;
        int inicio = 0;
        int meta = totalNodos - 1;

        textAreaTiempos.append("\n=== Ejecutando algoritmos ===\n");

        // Ejecutar BFS si está seleccionado
        if (btnBFS.getBackground().equals(new Color(70, 130, 180))) {
            long t0 = System.nanoTime();
            List<Integer> rutaBFS = BFS.bfs(adjListActual, inicio, meta);
            long t1 = System.nanoTime();
            long tiempo = t1 - t0;
            if (rutaBFS.isEmpty()) {
                textAreaTiempos.append("BFS: No encontró ruta. Tiempo = " + tiempo + " ns\n");
            } else {
                textAreaTiempos.append("BFS: Ruta = " + rutaBFS +
                    " (pasos=" + (rutaBFS.size() - 1) + ") " +
                    "Tiempo = " + tiempo + " ms\n");
            }
        }

        // Ejecutar Dijkstra si está seleccionado
        if (btnDijkstra.getBackground().equals(new Color(70, 130, 180))) {
            long t0 = System.nanoTime();
            Map<String, Object> resDij = Dijkstra.dijkstra(adjListActual, inicio, meta);
            long t1 = System.nanoTime();
            long tiempo = t1 - t0;
            @SuppressWarnings("unchecked")
            List<Integer> rutaDij = (List<Integer>) resDij.get("path");
            int distDij = (Integer) resDij.get("dist");
            if (rutaDij.isEmpty()) {
                textAreaTiempos.append("Dijkstra: No encontró ruta. Tiempo = " + tiempo + " ns\n");
            } else {
                textAreaTiempos.append("Dijkstra: Ruta = " + rutaDij +
                    " (dist=" + distDij + ") " +
                    "Tiempo = " + tiempo + " ms\n");
            }
        }

        // Ejecutar A* si está seleccionado
        if (btnAStar.getBackground().equals(new Color(70, 130, 180))) {
            long t0 = System.nanoTime();
            List<Integer> rutaAStar = AStar.aStarSearch(adjListActual, inicio, meta, filasActual, colsActual);
            long t1 = System.nanoTime();
            long tiempo = t1 - t0;
            if (rutaAStar.isEmpty()) {
                textAreaTiempos.append("A*: No encontró ruta. Tiempo = " + tiempo + " ns\n");
            } else {
                textAreaTiempos.append("A*: Ruta = " + rutaAStar +
                    " (pasos=" + (rutaAStar.size() - 1) + ") " +
                    "Tiempo = " + tiempo + " ms\n");
            }
        }

        textAreaTiempos.setCaretPosition(textAreaTiempos.getDocument().getLength());
    }

    private void configurarBotonAlgoritmo(JButton boton) {
        boton.setPreferredSize(new Dimension(120, 35));
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void toggleAlgoritmo(JButton boton, String algoritmo) {
        if (boton.getBackground().equals(new Color(70, 130, 180))) {
            boton.setBackground(Color.WHITE);
            boton.setForeground(Color.BLACK);
            actualizarTiempos("Algoritmo " + algoritmo + " desmarcado\n");
        } else {
            boton.setBackground(new Color(70, 130, 180));
            boton.setForeground(Color.WHITE);
            actualizarTiempos("Algoritmo " + algoritmo + " seleccionado\n");
        }
    }

    private String leerContenidoArchivo(String ruta) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        }
        return sb.toString();
    }

    private void actualizarTiempos(String mensaje) {
        textAreaTiempos.append("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + mensaje);
        textAreaTiempos.setCaretPosition(textAreaTiempos.getDocument().getLength());
    }

    // Métodos públicos para que ProyectoAlgoritmo (u otra clase) ejecute los algoritmos
    public boolean tieneGrafoCargado() {
        return adjListActual != null;
    }

    public int[] getDimensiones() {
        return new int[]{filasActual, colsActual};
    }

    public List<List<Integer>> getAdjListActual() {
        return adjListActual;
    }

    public String[] getAlgoritmosSeleccionados() {
        List<String> algs = new ArrayList<>();
        if (btnDijkstra.getBackground().equals(new Color(70, 130, 180))) algs.add("Dijkstra");
        if (btnBFS.getBackground().equals(new Color(70, 130, 180))) algs.add("BFS");
        if (btnAStar.getBackground().equals(new Color(70, 130, 180))) algs.add("A*");
        return algs.toArray(new String[0]);
    }

    public void reportarTiempo(String algoritmo, long tiempoMs) {
        actualizarTiempos("Tiempo " + algoritmo + ": " + tiempoMs + " ms\n");
    }
}
