/*
 * Esta es la clase principal que inicia la aplicación del Buscaminas.
 * Se encarga de mostrar el menú de dificultad al usuario.
 */
package app;
import gui.BuscaminasGUI;
import javax.swing.JOptionPane;
/**
 *
 * @author sebas
 */
public class Main {
    /*
     * Método principal que se ejecuta al iniciar el programa.
     */
    public static void main(String[] args) {
        String[] opciones = {"Fácil", "Medio", "Difícil"};
        // Muestra una ventana de diálogo para que el usuario elija la dificultad.
        int seleccion = JOptionPane.showOptionDialog(
            null,
            "Selecciona la dificultad",
            "Menú de Buscaminas",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        int filas = 0, columnas = 0, minas = 0;

        // Asigna los valores de filas, columnas y minas según la opción elegida.
        switch (seleccion) {
            case 0: // Fácil
                filas = 8;
                columnas = 8;
                minas = 10;
                break;
            case 1: // Medio
                filas = 16;
                columnas = 16;
                minas = 40;
                break;
            case 2: // Difícil
                filas = 16;
                columnas = 30;
                minas = 99;
                break;
            default:
                return; // Si el usuario cierra el diálogo, la aplicación termina.
        }
        
        // Crea una nueva instancia de BuscaminasGUI con los parámetros de dificultad.
        new BuscaminasGUI(filas, columnas, minas);
    }
}
