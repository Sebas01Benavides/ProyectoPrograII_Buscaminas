/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import db.ConexionBD;
import logica.Celda;
import logica.Tablero;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Fabricio Castro Vega
 */
public class BuscaminasGUI extends JFrame {
    private Tablero tablero;
    private JButton[][] botones;
    private JPanel panelTablero;

    public BuscaminasGUI(int filas, int columnas, int minas) {
        this.tablero = new Tablero(filas, columnas, minas);
        this.botones = new JButton[filas][columnas];
        this.panelTablero = new JPanel(new GridLayout(filas, columnas));
        inicializarGUI();
    }

    private void inicializarGUI() {
        setTitle("Buscaminas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Se establece un tamaño de ventana grande 
        int anchoVentana = tablero.getColumnas() * 60;
        int altoVentana = tablero.getFilas() * 60;
        setSize(anchoVentana, altoVentana);
        // -------------------

        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                JButton boton = new JButton();
                final int fila = i;
                final int columna = j;

                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (tablero.esJuegoTerminado()) {
                            return;
                        }
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            tablero.revelarCelda(fila, columna);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            marcarCelda(fila, columna);
                        }
                        actualizarTableroVisual();
                        comprobarEstadoJuego();
                    }
                });
                botones[i][j] = boton;
                panelTablero.add(boton);
            }
        }
        
        add(panelTablero);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    private void marcarCelda(int fila, int columna) {
        Celda celda = tablero.getCelda(fila, columna);
        if (!celda.estaRevelada()) {
            celda.setMarcada(!celda.estaMarcada());
        }
    }

private void actualizarTableroVisual() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Celda celda = tablero.getCelda(i, j);
                JButton boton = botones[i][j];
                int fontSize = (int) (Math.min(boton.getWidth(), boton.getHeight()) * 0.4);
                if (fontSize > 0) {
                    // Usar una fuente lógica (Dialog) para mayor compatibilidad
                    boton.setFont(new Font("Dialog", Font.BOLD, fontSize)); 
                }
                if (celda.estaRevelada()) {
                    boton.setEnabled(false);
                    if (celda.esMina()) {
                        boton.setText("💥");
                    } else if (celda.getNumeroMinasAdyacentes() > 0) {
                        boton.setText(String.valueOf(celda.getNumeroMinasAdyacentes()));
                    } else {
                        boton.setText("");
                    }
                } else if (celda.estaMarcada()) {
                    boton.setText("🚩");
                } else {
                    boton.setText("");
                    boton.setEnabled(true);
                }
            }
        }
    }

    private void comprobarEstadoJuego() {
        if (tablero.esJuegoTerminado()) {
            String resultado = tablero.esJuegoGanado() ? "Felicidades, has ganado " : "Has perdido";
            JOptionPane.showMessageDialog(this, resultado);
            String playerName = JOptionPane.showInputDialog(this, "Ingresa tu nombre para guardar tu puntuacion:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                int score = tablero.getCeldasReveladasCount();
                String dificultad = "Fácil"; 
                ConexionBD.guardarPuntuacion(playerName, score, dificultad);
            }
            // ----------------------------------------------------
        }
    }
}