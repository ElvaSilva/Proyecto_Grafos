/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author elohym
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Para hashCode y equals

public class Nodo { // Cambiado a 'Nodo' para consistencia con el español

    private final char letra; // Cambiado a 'letra' y hecho 'final' (inmutable)
    private final int fila; // Cambiado a 'fila' y hecho 'final'
    private final int columna; // Cambiado a 'columna' y hecho 'final'
    private final List<Nodo> vecinos; // Cambiado a 'vecinos' y hecho 'final'

    // Constructor actualizado con nombres en español
    public Nodo(char letra, int fila, int columna) {
        this.letra = Character.toUpperCase(letra); // Opcional: Asegurar que la letra siempre esté en mayúscula
        this.fila = fila;
        this.columna = columna;
        this.vecinos = new ArrayList<>(); // Inicializa la lista de vecinos
    }

    // Getters
    public char getLetra() {
        return letra;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    // Método para añadir un vecino
    public void agregarVecino(Nodo vecino) {
        // Evitar agregar el mismo vecino más de una vez
        if (!vecinos.contains(vecino)) {
            vecinos.add(vecino);
        }
    }

    // Getter para la lista de vecinos
    // Se devuelve una copia de la lista para evitar modificaciones externas directas a la lista original
    // aunque en este caso, al ser un grafo no dirigido, no es estrictamente necesario si solo se usa addNeighbor.
    // Pero es una buena práctica de encapsulamiento.
    public List<Nodo> getVecinos() {
        return new ArrayList<>(vecinos); // Devuelve una copia para proteger la lista interna
    }

    // **Mejoras Clave:**
    // Sobreescribir equals() y hashCode() es FUNDAMENTAL cuando usas objetos en colecciones
    // como List.contains() o si usas Sets de Nodos.
    // Esto asegura que la igualdad se base en las coordenadas (y no solo en la referencia de memoria).

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nodo nodo = (Nodo) o;
        // Dos nodos son iguales si tienen las mismas coordenadas en el tablero
        return fila == nodo.fila &&
               columna == nodo.columna;
    }

    @Override
    public int hashCode() {
        // Genera un hashCode basado en la fila y la columna
        return Objects.hash(fila, columna);
    }

    // Opcional: Método toString para facilitar la depuración
    @Override
    public String toString() {
        return "Nodo{" +
               "letra=" + letra +
               ", fila=" + fila +
               ", columna=" + columna +
               '}';
    }

}
