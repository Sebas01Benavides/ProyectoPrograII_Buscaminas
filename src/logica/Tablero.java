/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;
import java.util.Random;

/**
 *
 * @author sebas
 */

public class Tablero {
    private final int filas;
    private final int columnas;
    private final int numeroMinas;
    private Celda[][] celdas;
    private boolean juegoTerminado;
    private boolean juegoGanado;

    public Tablero(int filas, int columnas, int numeroMinas) {
        this.filas = filas;
        this.columnas = columnas;
        this.numeroMinas = numeroMinas;
        this.celdas = new Celda[filas][columnas];
        this.juegoTerminado = false;
        this.juegoGanado = false;
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
        colocarMinas();
        contarMinasAdyacentes();
    }

    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;
        while (minasColocadas < numeroMinas) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);
            if (!celdas[fila][columna].esMina()) {
                celdas[fila][columna].setMina(true);
                minasColocadas++;
            }
        }
    }

    private void contarMinasAdyacentes() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!celdas[i][j].esMina()) {
                    int contador = 0;
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            int filaVecina = i + x;
                            int columnaVecina = j + y;
                            if (esCoordenadaValida(filaVecina, columnaVecina) && celdas[filaVecina][columnaVecina].esMina()) {
                                contador++;
                            }
                        }
                    }
                    celdas[i][j].setNumeroMinasAdyacentes(contador);
                }
            }
        }
    }

    public void revelarCelda(int fila, int columna) {
        if (!esCoordenadaValida(fila, columna) || celdas[fila][columna].estaRevelada() || celdas[fila][columna].estaMarcada()) {
            return;
        }

        celdas[fila][columna].setRevelada(true);

        if (celdas[fila][columna].esMina()) {
            juegoTerminado = true;
            juegoGanado = false;
            return;
        }

        if (celdas[fila][columna].getNumeroMinasAdyacentes() == 0) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    revelarCelda(fila + x, columna + y);
                }
            }
        }
        comprobarVictoria();
    }

     public int getCeldasReveladasCount() {
        int count = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j].estaRevelada() && !celdas[i][j].esMina()) {
                    count++;
                }
            }
        }
        return count;
    }
     
    private void comprobarVictoria() {
        int celdasReveladas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j].estaRevelada()) {
                    celdasReveladas++;
                }
            }
        }
        if (celdasReveladas == (filas * columnas - numeroMinas)) {
            juegoTerminado = true;
            juegoGanado = true;
        }
    }
    
    private boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    // Getters
    public Celda getCelda(int fila, int columna) { return celdas[fila][columna]; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public boolean esJuegoTerminado() { return juegoTerminado; }
    public boolean esJuegoGanado() { return juegoGanado;}
}