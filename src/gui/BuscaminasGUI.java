/*
 * Esta clase es la interfaz gráfica del juego.
 * Extiende JFrame para crear la ventana principal y
 * se encarga de mostrar el tablero y manejar las interacciones del usuario.
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
    private Tablero tablero; // Objeto que contiene la lógica del juego.
    private JButton[][] botones; // Arreglo 2D de botones que representan las celdas en la GUI.
    private JPanel panelTablero; // Panel que contendrá todos los botones.

    /*
     * Constructor de la clase.
     * Inicializa el tablero lógico y la interfaz gráfica.
     */
    public BuscaminasGUI(int filas, int columnas, int minas) {
        this.tablero = new Tablero(filas, columnas, minas);
        this.botones = new JButton[filas][columnas];
        this.panelTablero = new JPanel(new GridLayout(filas, columnas));
        inicializarGUI();
    }

    /*
     * Configura la ventana principal y sus componentes.
     */
    private void inicializarGUI() {
        setTitle("Buscaminas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Se establece un tamaño de ventana dinámico basado en las dimensiones del tablero.
        int anchoVentana = tablero.getColumnas() * 60;
        int altoVentana = tablero.getFilas() * 60;
        setSize(anchoVentana, altoVentana);
        // -------------------

        // Bucle para crear y configurar cada botón del tablero.
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                JButton boton = new JButton();
                final int fila = i;
                final int columna = j;

                // Añade un escuchador para los clics del ratón (izquierdo y derecho).
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Si el juego ha terminado, no se procesan más clics.
                        if (tablero.esJuegoTerminado()) {
                            return;
                        }
                        // Lógica para el clic izquierdo (revelar celda) y derecho (marcar bandera).
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            tablero.revelarCelda(fila, columna);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            marcarCelda(fila, columna);
                        }
                        // Actualiza la visualización del tablero después de cada clic.
                        actualizarTableroVisual();
                        // Comprueba si el juego ha terminado después del movimiento.
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

    /*
     * Marca o desmarca una celda con una bandera.
     */
    private void marcarCelda(int fila, int columna) {
        Celda celda = tablero.getCelda(fila, columna);
        if (!celda.estaRevelada()) { // Solo se puede marcar si la celda no está revelada.
            celda.setMarcada(!celda.estaMarcada());
        }
    }

    /*
     * Sincroniza el estado del tablero lógico con la interfaz visual.
     * Actualiza el texto y la apariencia de cada botón.
     */
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
                if (celda.estaRevelada()) { // Si la celda está revelada...
                    boton.setEnabled(false); // Deshabilita el botón.
                    if (celda.esMina()) {
                        boton.setText("💥"); // Si es una mina, muestra una explosión.
                    } else if (celda.getNumeroMinasAdyacentes() > 0) {
                        boton.setText(String.valueOf(celda.getNumeroMinasAdyacentes())); // Si tiene minas adyacentes, muestra el número.
                    } else {
                        boton.setText(""); // Si no tiene minas adyacentes, queda vacío.
                    }
                } else if (celda.estaMarcada()) {
                    boton.setText("🚩"); // Si está marcada, muestra una bandera.
                } else {
                    boton.setText(""); // Si no, el botón está vacío.
                    boton.setEnabled(true); // Se asegura de que el botón esté habilitado.
                }
            }
        }
    }

    /*
     * Verifica si el juego ha terminado (ganado o perdido).
     * Si es el caso, muestra un mensaje y, si se ganó, guarda la puntuación.
     */
    private void comprobarEstadoJuego() {
        if (tablero.esJuegoTerminado()) {
            String resultado = tablero.esJuegoGanado() ? "Felicidades, has ganado " : "Has perdido";
            JOptionPane.showMessageDialog(this, resultado);
            // Solicita el nombre del jugador para guardar la puntuación.
            String playerName = JOptionPane.showInputDialog(this, "Ingresa tu nombre para guardar tu puntuacion:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                int score = tablero.getCeldasReveladasCount(); // La puntuación se basa en celdas reveladas.
                String dificultad = "Fácil"; // Esto debería ser dinámico.
                // Llama al método estático de ConexionBD para guardar la puntuación.
                ConexionBD.guardarPuntuacion(playerName, score, dificultad);
            }
            // ----------------------------------------------------
        }
    }
}