package com.mycompany.proyectoalgoritmo;

/**
 *
 * @author rauli
 */
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InterfazGrafica extends JFrame {
    
    // Variable para almacenar la matriz actual
    private int[][] matrizActual;
    
    // Componentes de la interfaz
    private JButton btnLeerCSV;
    private JButton btnCargarLaberinto; // Nuevo botón para cargar archivo específico
    private JButton btnGrafoManual; // Nuevo botón para grafo manual
    private JButton btnLaberintoAleatorio; // Nuevo botón para laberinto aleatorio
    private JTextArea textAreaContenido;
    private JScrollPane scrollPaneContenido;
    private JButton btnDijkstra;
    private JButton btnBFS;
    private JButton btnAStar;
    private JTextArea textAreaTiempos;
    private JScrollPane scrollPaneTiempos;
    private JFileChooser fileChooser;
    
    // Constructor
    public InterfazGrafica() {
        inicializarComponentes();
        configurarVentana();
        agregarComponentes();
        configurarEventos();
    }
    
    private void inicializarComponentes() {
        // Botón para leer archivo CSV
        btnLeerCSV = new JButton("Seleccionar Archivo");
        btnLeerCSV.setPreferredSize(new Dimension(150, 30));
        btnLeerCSV.setFont(new Font("Arial", Font.BOLD, 12));
        btnLeerCSV.setBackground(new Color(70, 130, 180));
        btnLeerCSV.setForeground(Color.WHITE);
        btnLeerCSV.setFocusPainted(false);
        
        // Botón para cargar el archivo específico del proyecto
        btnCargarLaberinto = new JButton("Cargar laberintoTEXT");
        btnCargarLaberinto.setPreferredSize(new Dimension(170, 30));
        btnCargarLaberinto.setFont(new Font("Arial", Font.BOLD, 12));
        btnCargarLaberinto.setBackground(new Color(34, 139, 34));
        btnCargarLaberinto.setForeground(Color.WHITE);
        btnCargarLaberinto.setFocusPainted(false);
        
        // Botón para grafo manual
        btnGrafoManual = new JButton("Grafo Manual");
        btnGrafoManual.setPreferredSize(new Dimension(140, 30));
        btnGrafoManual.setFont(new Font("Arial", Font.BOLD, 12));
        btnGrafoManual.setBackground(new Color(255, 140, 0));
        btnGrafoManual.setForeground(Color.WHITE);
        btnGrafoManual.setFocusPainted(false);
        
        // Botón para laberinto aleatorio
        btnLaberintoAleatorio = new JButton("Laberinto Aleatorio");
        btnLaberintoAleatorio.setPreferredSize(new Dimension(160, 30));
        btnLaberintoAleatorio.setFont(new Font("Arial", Font.BOLD, 12));
        btnLaberintoAleatorio.setBackground(new Color(220, 20, 60));
        btnLaberintoAleatorio.setForeground(Color.WHITE);
        btnLaberintoAleatorio.setFocusPainted(false);
        
        // Área de texto para mostrar contenido del archivo
        textAreaContenido = new JTextArea(15, 40);
        textAreaContenido.setFont(new Font("Courier New", Font.PLAIN, 12));
        textAreaContenido.setEditable(false);
        textAreaContenido.setBackground(new Color(248, 248, 248));
        textAreaContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scrollPaneContenido = new JScrollPane(textAreaContenido);
        scrollPaneContenido.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Contenido del Archivo CSV",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        scrollPaneContenido.setPreferredSize(new Dimension(450, 300));
        
        // Botones para seleccionar algoritmos
        btnDijkstra = new JButton("Dijkstra");
        btnBFS = new JButton("BFS");
        btnAStar = new JButton("A*");
        
        // Configurar botones de algoritmos
        configurarBotonAlgoritmo(btnDijkstra);
        configurarBotonAlgoritmo(btnBFS);
        configurarBotonAlgoritmo(btnAStar);
        
        // Área de texto para mostrar tiempos de ejecución
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
        scrollPaneTiempos.setPreferredSize(new Dimension(350, 200));
        
        // FileChooser para seleccionar archivos
        fileChooser = new JFileChooser();
        
        // Configurar directorio inicial
        String directorioProyecto = "C:\\Users\\rauli\\Desktop\\ALGORITMOS\\ProyectoFinal\\proyectoAlgoritmo-main\\src\\main\\java\\com\\mycompany\\proyectoalgoritmo";
        File directorioInicial = new File(directorioProyecto);
        if (directorioInicial.exists()) {
            fileChooser.setCurrentDirectory(directorioInicial);
        }
        
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos de texto (txt, csv)", "txt", "csv"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Todos los archivos", "*"));
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Laberintos - Representación con Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        
        // Configurar icono (opcional)
        try {
            setIconImage(Toolkit.getDefaultToolkit().createImage(""));
        } catch (Exception e) {
            // Icono por defecto si no se encuentra
        }
    }
    
    private void crearGrafoManual() {
        // Crear ventana de diálogo para entrada manual
        String dimensiones = JOptionPane.showInputDialog(this, 
            "Ingrese las dimensiones del grafo (formato: filas,columnas):\n" +
            "Ejemplo: 3,3 para un grafo de 3x3",
            "Crear Grafo Manual", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (dimensiones == null || dimensiones.trim().isEmpty()) {
            return; // Usuario canceló
        }
        
        try {
            String[] dims = dimensiones.split(",");
            int filas = Integer.parseInt(dims[0].trim());
            int columnas = Integer.parseInt(dims[1].trim());
            int totalNodes = filas * columnas;
            
            // Crear área de texto para entrada manual
            JTextArea inputArea = new JTextArea(15, 40);
            inputArea.setFont(new Font("Courier New", Font.PLAIN, 12));
            inputArea.setText("Ingrese las conexiones del grafo:\n\n" +
                "Formato: nodo,vecino1,vecino2,...\n" +
                "Ejemplo:\n" +
                "0,1,3\n" +
                "1,0,2,4\n" +
                "2,1,5\n\n" +
                "Nodos disponibles: 0 a " + (totalNodes - 1) + "\n" +
                "Para un grafo " + filas + "x" + columnas + "\n\n");
            
            JScrollPane scrollPane = new JScrollPane(inputArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            
            int result = JOptionPane.showConfirmDialog(this, scrollPane, 
                "Definir Conexiones del Grafo", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                // Procesar la entrada manual
                procesarGrafoManual(filas, columnas, inputArea.getText());
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de dimensiones.\nUse el formato: filas,columnas\nEjemplo: 3,3",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void procesarGrafoManual(int filas, int columnas, String input) {
        try {
            int totalNodes = filas * columnas;
            matrizActual = new int[totalNodes][totalNodes];
            
            String[] lines = input.split("\n");
            StringBuilder contenidoMostrar = new StringBuilder();
            contenidoMostrar.append("=== GRAFO MANUAL CREADO ===\n");
            contenidoMostrar.append("Dimensiones: ").append(filas).append("x").append(columnas).append("\n\n");
            
            boolean hayConexiones = false;
            
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("Ingrese") || line.startsWith("Formato") || 
                    line.startsWith("Ejemplo") || line.startsWith("Nodos") || line.startsWith("Para")) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int nodo = Integer.parseInt(parts[0].trim());
                    contenidoMostrar.append(nodo).append(",");
                    
                    for (int i = 1; i < parts.length; i++) {
                        int vecino = Integer.parseInt(parts[i].trim());
                        if (nodo < totalNodes && vecino < totalNodes && nodo >= 0 && vecino >= 0) {
                            matrizActual[nodo][vecino] = 1;
                            matrizActual[vecino][nodo] = 1;
                            hayConexiones = true;
                        }
                        contenidoMostrar.append(vecino);
                        if (i < parts.length - 1) contenidoMostrar.append(",");
                    }
                    contenidoMostrar.append("\n");
                }
            }
            
            contenidoMostrar.append("\n=== MATRIZ DE ADYACENCIA GENERADA ===\n");
            contenidoMostrar.append("Dimensiones: ").append(totalNodes).append("x").append(totalNodes).append("\n\n");
            
            // Mostrar matriz (máximo 10x10 para visualización)
            int maxShow = Math.min(10, totalNodes);
            for (int i = 0; i < maxShow; i++) {
                for (int j = 0; j < Math.min(10, totalNodes); j++) {
                    contenidoMostrar.append(matrizActual[i][j]).append(" ");
                }
                if (totalNodes > 10) contenidoMostrar.append("...");
                contenidoMostrar.append("\n");
            }
            if (totalNodes > 10) contenidoMostrar.append("...\n");
            
            textAreaContenido.setText(contenidoMostrar.toString());
            textAreaContenido.setCaretPosition(0);
            
            actualizarTiempos("Grafo manual creado exitosamente\n");
            actualizarTiempos("Dimensiones: " + filas + "x" + columnas + " (" + totalNodes + " nodos)\n");
            if (hayConexiones) {
                actualizarTiempos("Matriz generada con conexiones\n");
            } else {
                actualizarTiempos("Advertencia: No se encontraron conexiones válidas\n");
            }
            actualizarTiempos("Listo para ejecutar algoritmos\n\n");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al procesar el grafo manual:\n" + e.getMessage() +
                "\n\nVerifique el formato de entrada.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarLaberintoAleatorio() {
        // Pedir dimensiones al usuario
        String dimensiones = JOptionPane.showInputDialog(this, 
            "Ingrese las dimensiones del laberinto (formato: filas,columnas):\n" +
            "Ejemplo: 5,5 para un laberinto de 5x5",
            "Generar Laberinto Aleatorio", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (dimensiones == null || dimensiones.trim().isEmpty()) {
            return; // Usuario canceló
        }
        
        try {
            String[] dims = dimensiones.split(",");
            int filas = Integer.parseInt(dims[0].trim());
            int columnas = Integer.parseInt(dims[1].trim());
            
            // Validar dimensiones
            if (filas < 2 || columnas < 2 || filas > 20 || columnas > 20) {
                JOptionPane.showMessageDialog(this, 
                    "Las dimensiones deben estar entre 2x2 y 20x20",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Generar laberinto aleatorio
            generarMatrizAleatoria(filas, columnas);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de dimensiones.\nUse el formato: filas,columnas\nEjemplo: 5,5",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarMatrizAleatoria(int filas, int columnas) {
        int totalNodes = filas * columnas;
        matrizActual = new int[totalNodes][totalNodes];
        
        java.util.Random random = new java.util.Random();
        StringBuilder contenidoMostrar = new StringBuilder();
        contenidoMostrar.append("=== LABERINTO ALEATORIO GENERADO ===\n");
        contenidoMostrar.append("Dimensiones: ").append(filas).append("x").append(columnas).append("\n\n");
        
        // Generar conexiones aleatorias
        int conexionesCreadas = 0;
        for (int i = 0; i < totalNodes; i++) {
            // Cada nodo tiene entre 1 y 4 conexiones aleatorias
            int numConexiones = random.nextInt(3) + 1; // 1 a 3 conexiones
            
            for (int j = 0; j < numConexiones; j++) {
                int vecino = random.nextInt(totalNodes);
                if (vecino != i) { // No conectar consigo mismo
                    matrizActual[i][vecino] = 1;
                    matrizActual[vecino][i] = 1;
                    conexionesCreadas++;
                }
            }
        }
        
        // Mostrar algunas conexiones generadas
        contenidoMostrar.append("Conexiones generadas aleatoriamente:\n");
        for (int i = 0; i < Math.min(10, totalNodes); i++) {
            boolean tieneConexiones = false;
            contenidoMostrar.append(i).append(",");
            for (int j = 0; j < totalNodes; j++) {
                if (matrizActual[i][j] == 1) {
                    if (tieneConexiones) contenidoMostrar.append(",");
                    contenidoMostrar.append(j);
                    tieneConexiones = true;
                }
            }
            if (!tieneConexiones) contenidoMostrar.append("(sin conexiones)");
            contenidoMostrar.append("\n");
        }
        if (totalNodes > 10) contenidoMostrar.append("...\n");
        
        contenidoMostrar.append("\n=== MATRIZ DE ADYACENCIA GENERADA ===\n");
        contenidoMostrar.append("Dimensiones: ").append(totalNodes).append("x").append(totalNodes).append("\n\n");
        
        // Mostrar matriz (máximo 10x10 para visualización)
        int maxShow = Math.min(10, totalNodes);
        for (int i = 0; i < maxShow; i++) {
            for (int j = 0; j < Math.min(10, totalNodes); j++) {
                contenidoMostrar.append(matrizActual[i][j]).append(" ");
            }
            if (totalNodes > 10) contenidoMostrar.append("...");
            contenidoMostrar.append("\n");
        }
        if (totalNodes > 10) contenidoMostrar.append("...\n");
        
        textAreaContenido.setText(contenidoMostrar.toString());
        textAreaContenido.setCaretPosition(0);
        
        actualizarTiempos("Laberinto aleatorio generado exitosamente\n");
        actualizarTiempos("Dimensiones: " + filas + "x" + columnas + " (" + totalNodes + " nodos)\n");
        actualizarTiempos("Conexiones creadas: " + (conexionesCreadas / 2) + " (bidireccionales)\n");
        actualizarTiempos("Listo para ejecutar algoritmos\n\n");
    }
    public void agregarComponentes() {
        // Panel principal con BorderLayout
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior para el botón de cargar archivo
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSuperior.setBackground(new Color(240, 240, 240));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.add(btnLeerCSV);
        panelSuperior.add(btnCargarLaberinto);
        panelSuperior.add(btnGrafoManual);
        panelSuperior.add(btnLaberintoAleatorio);
        
        // Panel central para el contenido del archivo
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelCentral.add(scrollPaneContenido, BorderLayout.CENTER);
        
        // Panel derecho para algoritmos y tiempos
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        // Subpanel para selección de algoritmos
        JPanel panelAlgoritmos = new JPanel(new GridLayout(3, 1, 5, 5));
        panelAlgoritmos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Seleccionar Algoritmos",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        
        panelAlgoritmos.add(btnDijkstra);
        panelAlgoritmos.add(btnBFS);
        panelAlgoritmos.add(btnAStar);
        
        panelDerecho.add(panelAlgoritmos);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(scrollPaneTiempos);
        
        // Agregar paneles a la ventana principal
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
        
        // Panel inferior con información adicional
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(new Color(240, 240, 240));
        JLabel labelInfo = new JLabel("Sistema de Análisis de Laberintos usando Estructuras de Grafos");
        labelInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        labelInfo.setForeground(Color.GRAY);
        panelInferior.add(labelInfo);
        add(panelInferior, BorderLayout.SOUTH);
        
        pack();
    }
    
    private void configurarEventos() {
        // Evento para el botón de leer CSV
        btnLeerCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leerArchivoCSV();
            }
        });
        
        // Evento para el botón de cargar laberinto específico
        btnCargarLaberinto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarLaberintoTexto();
            }
        });
        
        // Evento para el botón de grafo manual
        btnGrafoManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearGrafoManual();
            }
        });
        
        // Evento para el botón de laberinto aleatorio
        btnLaberintoAleatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarLaberintoAleatorio();
            }
        });
        
        // Eventos para botones de algoritmos
        btnDijkstra.addActionListener(e -> toggleAlgoritmo(btnDijkstra, "Dijkstra"));
        btnBFS.addActionListener(e -> toggleAlgoritmo(btnBFS, "BFS"));
        btnAStar.addActionListener(e -> toggleAlgoritmo(btnAStar, "A*"));
        
        // Evento para cambio de algoritmo (ahora maneja múltiples selecciones)
        actualizarTiempos("Selecciona los algoritmos que deseas usar\n");
    }
    
    private void leerArchivoCSV() {
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String rutaArchivo = archivoSeleccionado.getAbsolutePath();
            
            try {
                // Leer el contenido original del archivo CSV
                String contenidoArchivo = leerContenidoArchivo(rutaArchivo);
                
                // Mostrar el contenido del archivo en el área de texto
                textAreaContenido.setText(contenidoArchivo);
                textAreaContenido.setCaretPosition(0); // Scroll al inicio
                
                // Usar CSVReader para convertir a matriz
                int[][] matrix = CSVReader.castCSVtoMatrix(rutaArchivo);
                matrizActual = matrix;  // Guardar la matriz para uso posterior
                
                // Agregar información de la matriz al contenido
                StringBuilder contenidoCompleto = new StringBuilder();
                contenidoCompleto.append("=== CONTENIDO DEL ARCHIVO CSV ===\n");
                contenidoCompleto.append(contenidoArchivo);
                contenidoCompleto.append("\n\n=== MATRIZ DE ADYACENCIA GENERADA ===\n");
                
                if (matrix != null) {
                    contenidoCompleto.append("Dimensiones: ").append(matrix.length).append("x").append(matrix[0].length).append("\n\n");
                    
                    // Mostrar solo una parte de la matriz si es muy grande
                    int maxShow = Math.min(10, matrix.length);
                    for (int i = 0; i < maxShow; i++) {
                        for (int j = 0; j < Math.min(10, matrix[i].length); j++) {
                            contenidoCompleto.append(matrix[i][j]).append(" ");
                        }
                        if (matrix[i].length > 10) {
                            contenidoCompleto.append("...");
                        }
                        contenidoCompleto.append("\n");
                    }
                    if (matrix.length > 10) {
                        contenidoCompleto.append("...\n");
                    }
                } else {
                    contenidoCompleto.append("Error: No se pudo generar la matriz\n");
                }
                
                // Mostrar todo el contenido
                textAreaContenido.setText(contenidoCompleto.toString());
                textAreaContenido.setCaretPosition(0);
                
                // Actualizar información en el área de tiempos
                actualizarTiempos("Archivo cargado: " + archivoSeleccionado.getName() + "\n");
                actualizarTiempos("Tamaño: " + archivoSeleccionado.length() + " bytes\n");
                if (matrix != null) {
                    actualizarTiempos("Matriz generada: " + matrix.length + "x" + matrix[0].length + "\n");
                }
                actualizarTiempos("Listo para ejecutar algoritmos\n\n");
                
            } catch (Exception e) {
                // Manejar errores
                actualizarTiempos("Error al leer archivo: " + e.getMessage() + "\n");
                textAreaContenido.setText("Error al leer el archivo:\n" + e.getMessage() + 
                    "\n\nVerifica que el archivo tenga el formato correcto:\n" +
                    "Primera línea: filas,columnas\n" +
                    "Siguientes líneas: nodo:vecino1,vecino2,...");
            }
        }
    }
    
    private String leerContenidoArchivo(String rutaArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString();
    }
    
    //CAMBIEN AQUI LA RUTA PARA QUE LES LEA EL CSV"
    private void cargarLaberintoTexto() {
        String rutaArchivo = "C:\\Users\\rauli\\Desktop\\ALGORITMOS\\ProyectoFinal\\proyectoAlgoritmo-main\\src\\main\\java\\com\\mycompany\\proyectoalgoritmo\\laberintoTEXT";
        File archivo = new File(rutaArchivo);
        
        if (!archivo.exists()) {
            actualizarTiempos("Error: No se encontró el archivo laberintoTEXT en la ruta especificada\n");
            textAreaContenido.setText("Error: Archivo 'laberintoTEXT' no encontrado en:\n" + rutaArchivo + 
                "\n\nVerifica que el archivo existe en esa ubicación.");
            return;
        }
        
        try {
            // Leer el contenido original del archivo
            String contenidoArchivo = leerContenidoArchivo(rutaArchivo);
            
            // Mostrar el contenido del archivo en el área de texto
            textAreaContenido.setText(contenidoArchivo);
            textAreaContenido.setCaretPosition(0);
            
            // Usar CSVReader para convertir a matriz
            int[][] matrix = CSVReader.castCSVtoMatrix(rutaArchivo);
            matrizActual = matrix;
            
            // Agregar información de la matriz al contenido
            StringBuilder contenidoCompleto = new StringBuilder();
            contenidoCompleto.append("=== ARCHIVO LABERINTO CARGADO ===\n");
            contenidoCompleto.append(contenidoArchivo);
            contenidoCompleto.append("\n\n=== MATRIZ DE ADYACENCIA GENERADA ===\n");
            
            if (matrix != null) {
                contenidoCompleto.append("Dimensiones: ").append(matrix.length).append("x").append(matrix[0].length).append("\n\n");
                
                // Mostrar solo una parte de la matriz si es muy grande
                int maxShow = Math.min(10, matrix.length);
                for (int i = 0; i < maxShow; i++) {
                    for (int j = 0; j < Math.min(10, matrix[i].length); j++) {
                        contenidoCompleto.append(matrix[i][j]).append(" ");
                    }
                    if (matrix[i].length > 10) {
                        contenidoCompleto.append("...");
                    }
                    contenidoCompleto.append("\n");
                }
                if (matrix.length > 10) {
                    contenidoCompleto.append("...\n");
                }
            } else {
                contenidoCompleto.append("Error: No se pudo generar la matriz\n");
            }
            
            // Mostrar todo el contenido
            textAreaContenido.setText(contenidoCompleto.toString());
            textAreaContenido.setCaretPosition(0);
            
            // Actualizar información en el área de tiempos
            actualizarTiempos("Archivo laberintoTEXT cargado exitosamente\n");
            actualizarTiempos("Tamaño: " + archivo.length() + " bytes\n");
            if (matrix != null) {
                actualizarTiempos("Matriz generada: " + matrix.length + "x" + matrix[0].length + "\n");
            }
            actualizarTiempos("Listo para ejecutar algoritmos\n\n");
            
        } catch (Exception e) {
            actualizarTiempos("Error al leer laberintoTEXT: " + e.getMessage() + "\n");
            textAreaContenido.setText("Error al leer el archivo laberintoTEXT:\n" + e.getMessage() + 
                "\n\nVerifica que el archivo tenga el formato correcto:\n" +
                "Primera línea: filas,columnas\n" +
                "Siguientes líneas: nodo:vecino1,vecino2,...");
        }
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
            // Desmarcar - volver a blanco
            boton.setBackground(Color.WHITE);
            boton.setForeground(Color.BLACK);
            actualizarTiempos("Algoritmo " + algoritmo + " desmarcado\n");
        } else {
            // Marcar - poner azul
            boton.setBackground(new Color(70, 130, 180));
            boton.setForeground(Color.WHITE);
            actualizarTiempos("Algoritmo " + algoritmo + " seleccionado\n");
        }
    }
    
    private String generarContenidoSimulado(String nombreArchivo) {
        StringBuilder contenido = new StringBuilder();
        contenido.append("Archivo: ").append(nombreArchivo).append("\n");
        contenido.append("Formato esperado del archivo CSV:\n\n");
        contenido.append("5,5\n");
        contenido.append("0:1,5\n");
        contenido.append("1:0,2,6\n");
        contenido.append("2:1,3,7\n");
        contenido.append("3:2,4,8\n");
        contenido.append("4:3,9\n");
        contenido.append("5:0,6,10\n");
        contenido.append("6:1,5,7,11\n");
        contenido.append("7:2,6,8,12\n");
        contenido.append("8:3,7,9,13\n");
        contenido.append("9:4,8,14\n");
        contenido.append("...\n\n");
        contenido.append("Donde:\n");
        contenido.append("- Primera línea: dimensiones del laberinto (filas,columnas)\n");
        contenido.append("- Siguientes líneas: nodo:lista_de_adyacentes\n");
        contenido.append("- Cada nodo representa una celda del laberinto\n");
        contenido.append("- Los números después de ':' son los nodos adyacentes\n\n");
        contenido.append("Contenido del archivo seleccionado aparecerá aquí\n");
        contenido.append("una vez implementada la lógica de lectura...");
        
        return contenido.toString();
    }
    
    private void actualizarTiempos(String mensaje) {
        textAreaTiempos.append("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + mensaje);
        textAreaTiempos.setCaretPosition(textAreaTiempos.getDocument().getLength());
    }
    
    // Métodos públicos para integrar con tu lógica
    public void mostrarContenidoArchivo(String contenido) {
        textAreaContenido.setText(contenido);
        textAreaContenido.setCaretPosition(0);
    }
    
    public String[] getAlgoritmosSeleccionados() {
        java.util.List<String> algoritmos = new java.util.ArrayList<>();
        
        if (btnDijkstra.getBackground().equals(new Color(70, 130, 180))) {
            algoritmos.add("Dijkstra");
        }
        if (btnBFS.getBackground().equals(new Color(70, 130, 180))) {
            algoritmos.add("BFS");
        }
        if (btnAStar.getBackground().equals(new Color(70, 130, 180))) {
            algoritmos.add("A*");
        }
        
        return algoritmos.toArray(new String[0]);
    }
    
    public void agregarTiempoEjecucion(String algoritmo, long tiempo) {
        actualizarTiempos("Tiempo " + algoritmo + ": " + tiempo + " ms\n");
    }
    
    public void limpiarTiempos() {
        textAreaTiempos.setText("Tiempos de ejecución:\n");
    }
    
    public int[][] getMatrizActual() {
        return matrizActual;
    }
    
    public boolean tieneMatrizCargada() {
        return matrizActual != null;
    }
    
    // La clase ProyectoAlgoritmo se encargará de ejecutar esta interfaz
}