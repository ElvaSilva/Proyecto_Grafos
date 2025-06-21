/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author elohym
 */

// Archivo: Tablero.java
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Tablero {
    private static final int FILAS = 4;
    private static final int COLUMNAS = 4;
    private Nodo[][] nodos; // Matriz de nodos

    public Tablero() {
        this.nodos = new Nodo[FILAS][COLUMNAS];
    }

    /**
     * Carga el tablero a partir de una lista de cadenas, donde cada cadena representa una fila.
     * Los caracteres de cada línea se usan para crear los Nodos.
     *
     * @param lineasTablero Una lista de 4 cadenas, cada una con 4 caracteres (espacios se ignoran).
     * @throws IllegalArgumentException Si el formato de las líneas es incorrecto.
     */
    public void cargarDesdeLineas(List<String> lineasTablero) throws IllegalArgumentException {
        if (lineasTablero == null || lineasTablero.size() != FILAS) {
            throw new IllegalArgumentException("El archivo del tablero debe contener exactamente " + FILAS + " filas.");
        }

        // Limpiar y re-inicializar el tablero
        this.nodos = new Nodo[FILAS][COLUMNAS];

        for (int i = 0; i < FILAS; i++) {
            String lineaLimpia = lineasTablero.get(i).replaceAll("\\s+", "").toUpperCase(); // Eliminar espacios y mayúsculas
            if (lineaLimpia.length() != COLUMNAS) {
                throw new IllegalArgumentException("La fila " + (i + 1) + " del tablero debe contener exactamente " + COLUMNAS + " letras.");
            }
            for (int j = 0; j < COLUMNAS; j++) {
                nodos[i][j] = new Nodo(lineaLimpia.charAt(j), i, j);
            }
        }
        conectarVecinos(); // Una vez cargados todos los nodos, establecer sus vecinos
    }

    /**
     * Establece las conexiones de adyacencia (vecinos) para cada nodo en el tablero.
     * Considera vecinos horizontales, verticales y diagonales.
     */
    private void conectarVecinos() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Nodo nodoActual = nodos[i][j];
                // Recorrer los 8 posibles vecinos
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) continue; // No es el propio nodo

                        int ni = i + di; // Nueva fila
                        int nj = j + dj; // Nueva columna

                        // Verificar que el vecino esté dentro de los límites del tablero
                        if (ni >= 0 && ni < FILAS && nj >= 0 && nj < COLUMNAS) {
                            nodoActual.agregarVecino(nodos[ni][nj]);
                        }
                    }
                }
            }
        }
    }

    public Nodo getNodo(int fila, int columna) {
        if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
            return nodos[fila][columna];
        }
        return null;
    }

    /**
     * Retorna una lista de todos los nodos en el tablero.
     * @return List<Nodo> de todos los nodos.
     */
    public List<Nodo> obtenerTodosLosNodos() {
        List<Nodo> allNodes = new ArrayList<>();
        for (int i = 0; i < FILAS; i++) {
            allNodes.addAll(Arrays.asList(nodos[i]).subList(0, COLUMNAS));
        }
        return allNodes;
    }

    /**
     * Retorna una representación en String del tablero para mostrar en la GUI.
     * @return String que representa el tablero.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                sb.append(nodos[i][j].getLetra()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
