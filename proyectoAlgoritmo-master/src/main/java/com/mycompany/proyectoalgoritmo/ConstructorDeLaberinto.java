
package com.mycompany.proyectoalgoritmo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ConstructorDeLaberinto extends JFrame {
    
    private JPanel panelLaberinto;
    private JPanel panelControles;
    private JComboBox<String> comboAlgoritmos;
    private JLabel labelTiempo;
    private JLabel labelOptimo;
    
    private List<List<Integer>> adjList;
    private int filas;
    private int columnas;
    private int totalNodos;
    private Map<String, ResultadoAlgoritmo> resultados;
    
    public static class ResultadoAlgoritmo {
        List<Integer> ruta;
        long tiempo;
        int distancia;
        
        public ResultadoAlgoritmo(List<Integer> ruta, long tiempo, int distancia) {
            this.ruta = ruta;
            this.tiempo = tiempo;
            this.distancia = distancia;
        }
    }
    
    public ConstructorDeLaberinto(List<List<Integer>> adjList, int filas, int columnas, 
                                Map<String, ResultadoAlgoritmo> resultados) {
        this.adjList = adjList;
        this.filas = filas;
        this.columnas = columnas;
        this.totalNodos = filas * columnas;
        this.resultados = resultados;
        
        initComponents();
        setupLayout();
        configurarEventos();
        
        setTitle("Visualización del Laberinto y Soluciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }
    
    public void initComponents() {
        // Panel de controles
        panelControles = new JPanel(new FlowLayout());
        panelControles.setBackground(new Color(240, 240, 240));
        panelControles.setBorder(BorderFactory.createTitledBorder("Visualización de Algoritmos"));
        
        comboAlgoritmos = new JComboBox<>();
        comboAlgoritmos.addItem("Laberinto Original");
        for (String algoritmo : resultados.keySet()) {
            comboAlgoritmos.addItem(algoritmo);
        }
        
        labelTiempo = new JLabel("Tiempo: --");
        labelOptimo = new JLabel("");
        
        panelControles.add(new JLabel("Mostrar: "));
        panelControles.add(comboAlgoritmos);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(labelTiempo);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(labelOptimo);
        
        // Panel del laberinto
        panelLaberinto = new LaberintoPanel();
        panelLaberinto.setPreferredSize(new Dimension(
            Math.max(400, columnas * 30), 
            Math.max(400, filas * 30)
        ));
        
     
        determinarAlgoritmoOptimo();
    }
    
    public void setupLayout() {
        setLayout(new BorderLayout());
        add(panelControles, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(panelLaberinto);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelLeyenda = new JPanel(new FlowLayout());
        panelLeyenda.setBackground(new Color(250, 250, 250));
        panelLeyenda.setBorder(BorderFactory.createTitledBorder("Leyenda"));
        
        agregarLeyenda(panelLeyenda, Color.WHITE, "Celda libre");
        agregarLeyenda(panelLeyenda, Color.BLACK, "Pared");
        agregarLeyenda(panelLeyenda, new Color(255, 192, 203), "Inicio (0)"); // Rosa
        agregarLeyenda(panelLeyenda, new Color(128, 0, 128), "Meta (" + (totalNodos-1) + ")"); // Morado
        agregarLeyenda(panelLeyenda, Color.GREEN, "Camino BFS"); // Verde
        agregarLeyenda(panelLeyenda, Color.BLUE, "Camino Dijkstra"); // Azul
        agregarLeyenda(panelLeyenda, Color.ORANGE, "Camino A*"); // Naranja
        agregarLeyenda(panelLeyenda, Color.RED, "Algoritmo óptimo"); // Rojo para óptimo
        
        add(panelLeyenda, BorderLayout.SOUTH);
    }
    
    public void agregarLeyenda(JPanel panel, Color color, String texto) {
        JPanel cuadrito = new JPanel();
        cuadrito.setBackground(color);
        cuadrito.setPreferredSize(new Dimension(15, 15));
        cuadrito.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        panel.add(cuadrito);
        panel.add(new JLabel(texto));
        panel.add(Box.createHorizontalStrut(15));
    }
    
    public void configurarEventos() {
        comboAlgoritmos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboAlgoritmos.getSelectedItem();
                if ("Laberinto Original".equals(seleccionado)) {
                    labelTiempo.setText("Tiempo: --");
                } else {
                    ResultadoAlgoritmo resultado = resultados.get(seleccionado);
                    if (resultado != null) {
                        labelTiempo.setText("Tiempo: " + resultado.tiempo + " ms");
                    }
                }
                panelLaberinto.repaint();
            }
        });
    }
    
    public void determinarAlgoritmoOptimo() {
        String mejorAlgoritmo = null;
        long mejorTiempo = Long.MAX_VALUE;
        int mejorDistancia = Integer.MAX_VALUE;
        
        for (Map.Entry<String, ResultadoAlgoritmo> entry : resultados.entrySet()) {
            ResultadoAlgoritmo resultado = entry.getValue();
            if (!resultado.ruta.isEmpty()) {
                // Priorizar por distancia, luego por tiempo
                if (resultado.distancia < mejorDistancia || 
                    (resultado.distancia == mejorDistancia && resultado.tiempo < mejorTiempo)) {
                    mejorDistancia = resultado.distancia;
                    mejorTiempo = resultado.tiempo;
                    mejorAlgoritmo = entry.getKey();
                }
            }
        }
        
        if (mejorAlgoritmo != null) {
            labelOptimo.setText("Óptimo: " + mejorAlgoritmo + " (dist=" + mejorDistancia + ")");
            labelOptimo.setForeground(Color.RED); // Cambié a rojo para diferenciarlo
            labelOptimo.setFont(labelOptimo.getFont().deriveFont(Font.BOLD));
        }
    }
    
    public class LaberintoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int cellSize = Math.min(getWidth() / columnas, getHeight() / filas);
            cellSize = Math.max(20, cellSize); 
            
            int offsetX = (getWidth() - columnas * cellSize) / 2;
            int offsetY = (getHeight() - filas * cellSize) / 2;
            
       
            dibujarLaberinto(g2d, cellSize, offsetX, offsetY);
            
           
            String algoritmoSeleccionado = (String) comboAlgoritmos.getSelectedItem();
            if (!"Laberinto Original".equals(algoritmoSeleccionado)) {
                ResultadoAlgoritmo resultado = resultados.get(algoritmoSeleccionado);
                if (resultado != null && !resultado.ruta.isEmpty()) {
                    dibujarSolucion(g2d, resultado.ruta, cellSize, offsetX, offsetY, algoritmoSeleccionado);
                }
            }
        }
        
        public void dibujarLaberinto(Graphics2D g2d, int cellSize, int offsetX, int offsetY) {
           
            g2d.setColor(Color.BLACK);
            g2d.fillRect(offsetX, offsetY, columnas * cellSize, filas * cellSize);
            
  
            g2d.setColor(Color.WHITE);
            
            for (int nodo = 0; nodo < totalNodos; nodo++) {
                int fila = nodo / columnas;
                int col = nodo % columnas;
                
                int x = offsetX + col * cellSize;
                int y = offsetY + fila * cellSize;
                
              
                g2d.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
                
                
                for (int vecino : adjList.get(nodo)) {
                    int filaVecino = vecino / columnas;
                    int colVecino = vecino % columnas;
                    
                
                    if (Math.abs(fila - filaVecino) + Math.abs(col - colVecino) == 1) {
                        int xVecino = offsetX + colVecino * cellSize;
                        int yVecino = offsetY + filaVecino * cellSize;
                        
                 
                        if (fila == filaVecino) { 
                            int minX = Math.min(x, xVecino);
                            g2d.fillRect(minX + cellSize - 1, y + 1, 2, cellSize - 2);
                        } else { 
                            int minY = Math.min(y, yVecino);
                            g2d.fillRect(x + 1, minY + cellSize - 1, cellSize - 2, 2);
                        }
                    }
                }
            }
            
          
            marcarCeldaEspecial(g2d, 0, new Color(255, 192, 203), cellSize, offsetX, offsetY, "S"); // Rosa
            marcarCeldaEspecial(g2d, totalNodos - 1, new Color(128, 0, 128), cellSize, offsetX, offsetY, "E"); // Morado
        }
        
        public void marcarCeldaEspecial(Graphics2D g2d, int nodo, Color color, 
                                       int cellSize, int offsetX, int offsetY, String texto) {
            int fila = nodo / columnas;
            int col = nodo % columnas;
            
            int x = offsetX + col * cellSize;
            int y = offsetY + fila * cellSize;
            
            
            int circleSize = Math.max(8, cellSize / 3); 
            int margin = (cellSize - circleSize) / 2;
            
            g2d.setColor(color);
            g2d.fillOval(x + margin, y + margin, circleSize, circleSize);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, Math.max(6, circleSize / 3)));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellSize - fm.stringWidth(texto)) / 2;
            int textY = y + (cellSize + fm.getAscent()) / 2;
            g2d.drawString(texto, textX, textY);
        }
        
        public void dibujarSolucion(Graphics2D g2d, List<Integer> ruta, int cellSize, 
                                   int offsetX, int offsetY, String algoritmo) {
            if (ruta.size() < 2) return;
            
            /
            Color colorSolucion;
            switch (algoritmo.toUpperCase()) {
                case "BFS":
                    colorSolucion = Color.GREEN; 
                    break;
                case "DIJKSTRA":
                    colorSolucion = Color.BLUE; 
                    break;
                case "A*":
                    colorSolucion = Color.ORANGE; 
                    break;
                default:
                    colorSolucion = Color.BLUE;
            }
            
           
            String mejorAlgoritmo = labelOptimo.getText();
            if (mejorAlgoritmo.contains(algoritmo)) {
                colorSolucion = Color.RED;
            }
            
            g2d.setColor(colorSolucion);
            g2d.setStroke(new BasicStroke(Math.max(2, cellSize / 8), 
                         BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
         
            for (int i = 0; i < ruta.size() - 1; i++) {
                int nodoActual = ruta.get(i);
                int nodoSiguiente = ruta.get(i + 1);
                
                int filaActual = nodoActual / columnas;
                int colActual = nodoActual % columnas;
                int filaSiguiente = nodoSiguiente / columnas;
                int colSiguiente = nodoSiguiente % columnas;
                
                int x1 = offsetX + colActual * cellSize + cellSize / 2;
                int y1 = offsetY + filaActual * cellSize + cellSize / 2;
                int x2 = offsetX + colSiguiente * cellSize + cellSize / 2;
                int y2 = offsetY + filaSiguiente * cellSize + cellSize / 2;
                
                g2d.drawLine(x1, y1, x2, y2);
            }
            
     
            g2d.setColor(colorSolucion.darker());
            for (int nodo : ruta) {
                if (nodo != 0 && nodo != totalNodos - 1) {
                    int fila = nodo / columnas;
                    int col = nodo % columnas;
                    
                    int x = offsetX + col * cellSize + cellSize / 2;
                    int y = offsetY + fila * cellSize + cellSize / 2;
                    
                    g2d.fillOval(x - 3, y - 3, 6, 6);
                }
            }
        }
    }
    
    public static void mostrarLaberinto(List<List<Integer>> adjList, int filas, int columnas,
                                       Map<String, List<Integer>> rutas, Map<String, Long> tiempos) {
        Map<String, ResultadoAlgoritmo> resultados = new HashMap<>();
        
        for (String algoritmo : rutas.keySet()) {
            List<Integer> ruta = rutas.get(algoritmo);
            long tiempo = tiempos.getOrDefault(algoritmo, 0L);
            int distancia = ruta.isEmpty() ? Integer.MAX_VALUE : ruta.size() - 1;
            
            resultados.put(algoritmo, new ResultadoAlgoritmo(ruta, tiempo, distancia));
        }
        
        SwingUtilities.invokeLater(() -> {
            ConstructorDeLaberinto visualizador = new ConstructorDeLaberinto(adjList, filas, columnas, resultados);
            visualizador.setVisible(true);
        });
    }
}
