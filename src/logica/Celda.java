/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author sebas
 */
/**
 * Representa una celda del tablero de Buscaminas.
 * Mantiene el estado de la celda (si es mina, número de minas vecinas,
 * si está revelada y si está marcada con bandera).
 * 
 * <p>Esta clase es intencionalmente ligera y sin lógica de presentación;
 * la lógica de juego que la manipula vive en {@link Tablero}.</p>
 */
public class Celda {
    /** Indica si esta celda contiene una mina. */
    private boolean esMina;
    /** Número de minas presentes en las 8 celdas vecinas. */
    private int numeroMinasAdyacentes;
    /** true si el jugador ya reveló esta celda. */
    private boolean estaRevelada;
    /** true si el jugador marcó esta celda con una bandera. */
    private boolean estaMarcada;

    public Celda() {
        this.esMina = false;
        this.numeroMinasAdyacentes = 0;
        this.estaRevelada = false;
        this.estaMarcada = false;
    }

    // Getters y setters
    /**
     * @return {@code true} si esta celda contiene una mina.
     */
    public boolean esMina() { return esMina; }
    /**
     * Define si esta celda contiene una mina.
     * @param esMina valor booleano que indica si la celda es mina.
     */
    public void setMina(boolean esMina) { this.esMina = esMina; }

    /**
     * @return cantidad de minas en las celdas vecinas (0..8).
     */
    public int getNumeroMinasAdyacentes() { return numeroMinasAdyacentes; }
    /**
     * Establece la cantidad de minas adyacentes calculada por el tablero.
     * @param numero valor entero entre 0 y 8.
     */
    public void setNumeroMinasAdyacentes(int numero) { this.numeroMinasAdyacentes = numero; }

    /**
     * @return {@code true} si la celda ya fue revelada por el jugador.
     */
    public boolean estaRevelada() { return estaRevelada; }
    /**
     * Marca la celda como revelada/no revelada.
     * @param revelada estado de revelado.
     */
    public void setRevelada(boolean revelada) { this.estaRevelada = revelada; }

    /**
     * @return {@code true} si la celda fue marcada con bandera por el jugador.
     */
    public boolean estaMarcada() { return estaMarcada; }
    /**
     * Marca o desmarca la celda con una bandera.
     * @param marcada estado de marcado (bandera).
     */
    public void setMarcada(boolean marcada) { this.estaMarcada = marcada; }
}