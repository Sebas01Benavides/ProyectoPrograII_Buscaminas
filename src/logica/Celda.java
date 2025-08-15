/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author sebas
 */
public class Celda {
    private boolean esMina;
    private int numeroMinasAdyacentes;
    private boolean estaRevelada;
    private boolean estaMarcada;

    public Celda() {
        this.esMina = false;
        this.numeroMinasAdyacentes = 0;
        this.estaRevelada = false;
        this.estaMarcada = false;
    }

    // Getters y setters
    public boolean esMina() { return esMina; }
    public void setMina(boolean esMina) { this.esMina = esMina; }

    public int getNumeroMinasAdyacentes() { return numeroMinasAdyacentes; }
    public void setNumeroMinasAdyacentes(int numero) { this.numeroMinasAdyacentes = numero; }

    public boolean estaRevelada() { return estaRevelada; }
    public void setRevelada(boolean revelada) { this.estaRevelada = revelada; }

    public boolean estaMarcada() { return estaMarcada; }
    public void setMarcada(boolean marcada) { this.estaMarcada = marcada; }
}
