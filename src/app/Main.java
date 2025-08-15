package app;
import gui.BuscaminasGUI;
import javax.swing.JOptionPane;
/**
 *
 * @author sebas
 */
public class Main {
    public static void main(String[] args) {
        String[] opciones = {"Fácil", "Medio", "Difícil"};
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
                return; // Cerrar la aplicación si se cancela
        }
        
        new BuscaminasGUI(filas, columnas, minas);
    }
}
