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

/**
 * Representa el tablero de juego de Buscaminas.
 * Contiene la matriz de el número de minas y la lógica
 *principal del juego (colocación de minas, conteo de minas adyacentes,
 * revelado de celdas y comprobación de victoria/derrota).
 */
public class Tablero {
    /** Número de filas del tablero. */
    private final int filas;
    /** Número de columnas del tablero. */
    private final int columnas;
    /** Número total de minas que se colocarán en el tablero. */
    private final int numeroMinas;
    /** Matriz bidimensional que almacena las celdas del tablero. */
    private Celda[][] celdas;
    /** Indica si la partida ya ha terminado (victoria o derrota). */
    private boolean juegoTerminado;
    /** Indica si el jugador ganó la partida. */
    private boolean juegoGanado;

    /**
     * Crea un tablero nuevo con las dimensiones y minas especificadas.
     * Inicializa la matriz de celdas y coloca aleatoriamente las minas.
     * @param filas número de filas del tablero
     * @param columnas número de columnas del tablero
     * @param numeroMinas cantidad de minas a colocar
     */
    public Tablero(int filas, int columnas, int numeroMinas) {
        this.filas = filas;
        this.columnas = columnas;
        this.numeroMinas = numeroMinas;
        this.celdas = new Celda[filas][columnas];
        this.juegoTerminado = false;
        this.juegoGanado = false;
        inicializarTablero();
    }

    /**
     * Inicializa el tablero creando las celdas vacías,
     * colocando las minas y contando las minas adyacentes.
     */
    private void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
        colocarMinas();
        contarMinasAdyacentes();
    }

    /**
     * Coloca aleatoriamente las minas en el tablero
     * asegurando que no se repitan posiciones.
     */
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

    /**
     * Calcula y asigna a cada celda el número de minas adyacentes.
     */
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

    /**
     * Revela una celda del tablero. Si la celda es una mina, el juego termina.
     * Si no es mina y no tiene minas alrededor, revela recursivamente las vecinas.
     * @param fila coordenada de fila
     * @param columna coordenada de columna
     */
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

    /**
     * Cuenta cuántas celdas se han revelado sin ser minas.
     * @return número de celdas reveladas (no minas).
     */
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
     
    /**
     * Verifica si el jugador ganó la partida.
     * El juego se gana si todas las celdas sin minas han sido reveladas.
     */
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
    
    /**
     * Verifica que una coordenada pertenezca al tablero.
     * @param fila índice de fila a comprobar
     * @param columna índice de columna a comprobar
     * @return true si la coordenada está dentro de los límites
     */
    private boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    /** Métodos de acceso a información del tablero y estado del juego. */
    // Getters
    public Celda getCelda(int fila, int columna) { return celdas[fila][columna]; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public boolean esJuegoTerminado() { return juegoTerminado; }
    public boolean esJuegoGanado() { return juegoGanado;}
}