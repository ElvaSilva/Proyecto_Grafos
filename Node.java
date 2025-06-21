/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author elohym
 */

// Archivo: Nodo.java
import java.util.HashSet;
import java.util.Set;

public class Nodo {
    private final char letra;
    private final int fila;
    private final int columna;
    private final Set<Nodo> vecinos; // Nodos adyacentes

    public Nodo(char letra, int fila, int columna) {
        this.letra = letra;
        this.fila = fila;
        this.columna = columna;
        this.vecinos = new HashSet<>();
    }

    public char getLetra() {
        return letra;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Set<Nodo> getVecinos() {
        return vecinos;
    }

    public void agregarVecino(Nodo vecino) {
        this.vecinos.add(vecino);
    }

    @Override
    public String toString() {
        return "("+fila+","+columna+")[" + letra + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nodo nodo = (Nodo) o;
        return fila == nodo.fila && columna == nodo.columna; // Identidad por posición
    }

    @Override
    public int hashCode() {
        // Un hashcode simple pero efectivo para coordenadas 4x4
        return 31 * fila + columna;
    }
}
