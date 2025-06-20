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
import java.util.Collections;
import java.util.List;

public class Tablero { // Cambiado a 'Tablero' para consistencia

    private final Nodo[][] cuadricula; // Cambiado a 'cuadricula' y hecho 'final'
    private final int tamano; // Cambiado a 'tamano' y hecho 'final'

    // Constructor que recibe una matriz de caracteres para inicializar el tablero
    public Tablero(char[][] letras) {
        // Validación básica del tamaño del tablero
        if (letras == null || letras.length != 4 || letras[0].length != 4) {
            throw new IllegalArgumentException("El tablero debe ser de 4x4.");
        }

        this.tamano = 4; // Tamaño fijo según el requerimiento
        this.cuadricula = new Nodo[tamano][tamano];

        inicializarCuadricula(letras); // Inicializa los nodos en la cuadrícula
        construirGrafo(); // Establece las relaciones de adyacencia entre los nodos
    }

    /**
     * Inicializa cada celda de la cuadrícula con un nuevo objeto Nodo,
     * asignando la letra y sus coordenadas (fila, columna).
     *
     * @param letras Matriz de caracteres 4x4 con las letras del tablero.
     */
    private void inicializarCuadricula(char[][] letras) {
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                // Se asegura que la letra se convierta a mayúscula al crear el Nodo
                // Esto es útil si las letras de entrada pueden ser minúsculas,
                // manteniendo consistencia para la búsqueda de palabras.
                cuadricula[i][j] = new Nodo(letras[i][j], i, j);
            }
        }
    }

    /**
     * Establece las relaciones de adyacencia (vecinos) para cada nodo en el tablero.
     * Considera todas las 8 direcciones posibles (horizontal, vertical, diagonal).
     */
    private void construirGrafo() {
        // Desplazamientos para las 8 direcciones (dx para fila, dy para columna)
        // Arriba-izquierda, Arriba, Arriba-derecha, Derecha, Abajo-derecha, Abajo, Abajo-izquierda, Izquierda
        int[] df = {-1, -1, -1, 0, 1, 1, 1, 0}; // delta fila
        int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1}; // delta columna

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                Nodo nodoActual = cuadricula[i][j];
                for (int d = 0; d < df.length; d++) {
                    int nuevaFila = i + df[d];
                    int nuevaColumna = j + dc[d];

                    // Verifica que las nuevas coordenadas estén dentro de los límites del tablero
                    if (esCoordenadaValida(nuevaFila, nuevaColumna)) {
                        nodoActual.agregarVecino(cuadricula[nuevaFila][nuevaColumna]);
                    }
                }
            }
        }
    }

    /**
     * Verifica si un par de coordenadas (fila, columna) está dentro de los límites del tablero.
     *
     * @param fila La fila a verificar.
     * @param columna La columna a verificar.
     * @return true si las coordenadas son válidas, false en caso contrario.
     */
    private boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < tamano && columna >= 0 && columna < tamano;
    }

    /**
     * Obtiene el Nodo en las coordenadas especificadas.
     *
     * @param fila La fila del nodo.
     * @param columna La columna del nodo.
     * @return El Nodo en las coordenadas dadas, o null si las coordenadas son inválidas.
     */
    public Nodo obtenerNodo(int fila, int columna) { // Cambiado a 'obtenerNodo'
        if (esCoordenadaValida(fila, columna)) {
            return cuadricula[fila][columna];
        }
        return null;
    }

    /**
     * Devuelve una lista de todos los Nodos en el tablero.
     * Esto es útil para iniciar búsquedas desde cada posible Nodo del tablero.
     *
     * @return Una lista inmutable de todos los Nodos del tablero.
     */
    public List<Nodo> obtenerTodosLosNodos() {
        List<Nodo> todosLosNodos = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                todosLosNodos.add(cuadricula[i][j]);
            }
        }
        return Collections.unmodifiableList(todosLosNodos); // Devuelve una lista inmutable
    }

    /**
     * Devuelve el tamaño del tablero (lado).
     *
     * @return El tamaño del tablero.
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * Muestra el tablero en la consola.
     * Nota: Según los requerimientos, la salida final debe ser en la interfaz gráfica,
     * pero este método es útil para depuración.
     */
    public void mostrarTableroConsola() { // Cambiado a 'mostrarTableroConsola'
        System.out.println("Tablero:");
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                System.out.print(cuadricula[i][j].getLetra() + " ");
            }
            System.out.println();
        }
    }

    // Opcional: Si el tablero necesita una representación de cadena completa (ej. para la interfaz gráfica)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                sb.append(cuadricula[i][j].getLetra()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
