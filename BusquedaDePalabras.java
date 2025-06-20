/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author elohym
 */

import java.util.*;

public class BusquedaDePalabras { // Cambiado a 'BusquedaDePalabras'

    private final Tablero tablero; // Usar 'Tablero' y hacerlo final
    private final Diccionario diccionario; // Usar 'Diccionario' y hacerlo final

    public BusquedaDePalabras(Tablero tablero, Diccionario diccionario) {
        this.tablero = tablero;
        this.diccionario = diccionario;
    }

    /**
     * Busca todas las palabras válidas en el tablero utilizando el algoritmo DFS.
     *
     * @return Un conjunto de palabras encontradas.
     */
    public Set<String> buscarPalabrasDFS() {
        Set<String> palabrasEncontradas = new HashSet<>();
        // Itera sobre cada nodo del tablero como punto de inicio para una nueva búsqueda DFS
        for (Nodo nodoInicio : tablero.obtenerTodosLosNodos()) {
            // El Set 'visitados' se inicializa para CADA NUEVA RUTA de búsqueda DFS
            // y se maneja con backtracking dentro del método recursivo.
            dfs(nodoInicio, "", new Stack<>(), palabrasEncontradas);
        }
        return palabrasEncontradas;
    }

    /**
     * Método recursivo auxiliar para la búsqueda DFS.
     * Utiliza un Stack para la pila de recursión y un Set<Nodo> para los nodos visitados
     * en el CAMINO ACTUAL de la palabra.
     *
     * @param nodoActual El nodo actual en la búsqueda.
     * @param palabraActual La palabra formada hasta el nodo actual.
     * @param caminoActual Stack que representa el camino de nodos que forman palabraActual.
     * @param palabrasEncontradas Conjunto donde se añaden las palabras válidas encontradas.
     */
    private void dfs(Nodo nodoActual, String palabraActual, Stack<Nodo> caminoActual, Set<String> palabrasEncontradas) {
        // Marcamos el nodo actual como visitado para esta ruta
        caminoActual.push(nodoActual); // Agregamos al Stack para saber que estamos en este camino
        palabraActual += nodoActual.getLetra();

        // Optimización: Si la palabra actual no es prefijo de ninguna palabra en el diccionario,
        // no tiene sentido seguir por esta rama.
        if (!diccionario.esPrefijo(palabraActual)) {
            caminoActual.pop(); // Backtrack: Deshacemos la visita
            return;
        }

        // Verificación de palabra válida:
        // 1. Longitud mínima de 3 letras.
        // 2. Existe en el diccionario.
        if (palabraActual.length() >= 3 && diccionario.contiene(palabraActual)) {
            palabrasEncontradas.add(palabraActual);
        }

        // Explora los vecinos del nodo actual
        for (Nodo vecino : nodoActual.getVecinos()) {
            // Solo visita si no ha sido visitado en el CAMINO ACTUAL
            if (!caminoActual.contains(vecino)) { // 'contains' funciona bien si Nodo.equals() está bien definido
                dfs(vecino, palabraActual, caminoActual, palabrasEncontradas);
            }
        }

        // Backtrack: Al salir de la recursión para este nodo, lo "desvisitamos"
        // para que pueda ser utilizado en otros caminos diferentes.
        caminoActual.pop();
    }


    /**
     * Busca todas las palabras válidas en el tablero utilizando el algoritmo BFS.
     *
     * @return Un conjunto de palabras encontradas.
     */
    public Set<String> buscarPalabrasBFS() {
        Set<String> palabrasEncontradas = new HashSet<>();
        for (Nodo nodoInicio : tablero.obtenerTodosLosNodos()) {
            bfs(nodoInicio, palabrasEncontradas);
        }
        return palabrasEncontradas;
    }

    /**
     * Método auxiliar para la búsqueda BFS.
     * Recorre el grafo por niveles, construyendo palabras y verificando el diccionario.
     *
     * @param nodoInicio El nodo desde el cual iniciar la búsqueda BFS.
     * @param palabrasEncontradas Conjunto donde se añaden las palabras válidas encontradas.
     */
    private void bfs(Nodo nodoInicio, Set<String> palabrasEncontradas) {
        Queue<EstadoBusqueda> cola = new LinkedList<>();
        // El 'Set<Nodo> visitados' se crea para cada estado en la cola, reflejando el camino único
        cola.add(new EstadoBusqueda(nodoInicio, "" + nodoInicio.getLetra(), new HashSet<>(Collections.singletonList(nodoInicio))));

        while (!cola.isEmpty()) {
            EstadoBusqueda estadoActual = cola.poll();
            Nodo nodo = estadoActual.nodo;
            String palabraActual = estadoActual.palabra;
            Set<Nodo> visitadosEnCamino = estadoActual.visitados;

            // Optimización de prefijo: si la palabra actual no es prefijo, no continuamos esta rama.
            if (!diccionario.esPrefijo(palabraActual)) {
                continue;
            }

            if (palabraActual.length() >= 3 && diccionario.contiene(palabraActual)) {
                palabrasEncontradas.add(palabraActual);
            }

            for (Nodo vecino : nodo.getVecinos()) {
                if (!visitadosEnCamino.contains(vecino)) {
                    Set<Nodo> nuevosVisitados = new HashSet<>(visitadosEnCamino);
                    nuevosVisitados.add(vecino);
                    cola.add(new EstadoBusqueda(vecino, palabraActual + vecino.getLetra(), nuevosVisitados));
                }
            }
        }
    }


    /**
     * Busca una palabra específica en el tablero y la agrega al diccionario si la encuentra.
     *
     * @param palabraBuscada La palabra a buscar.
     * @param metodoBusqueda "DFS" o "BFS".
     * @return Una clase 'ResultadoBusquedaEspecifica' que contiene si fue encontrada y el camino (si es BFS).
     */
    public ResultadoBusquedaEspecifica buscarPalabraEspecifica(String palabraBuscada, String metodoBusqueda) {
        String palabraNormalizada = diccionario.normalizarPalabra(palabraBuscada); // Reutilizar la normalización del diccionario

        if (palabraNormalizada.length() < 3) {
            return new ResultadoBusquedaEspecifica(false, null, "La palabra debe tener al menos 3 letras.");
        }

        for (Nodo nodoInicio : tablero.obtenerTodosLosNodos()) {
            List<Nodo> caminoEncontrado = null; // Para almacenar el camino si se encuentra la palabra
            boolean encontrada = false;

            if ("DFS".equalsIgnoreCase(metodoBusqueda)) {
                // Para DFS, se podría adaptar para devolver el camino si se encuentra
                // Pero el requerimiento E (visualización) es solo para BFS.
                // Aquí, simplificamos y solo verificamos si existe.
                encontrada = dfsParaPalabraEspecifica(nodoInicio, "", new Stack<>(), palabraNormalizada);
            } else if ("BFS".equalsIgnoreCase(metodoBusqueda)) {
                // BFS para palabra específica, CAPTURANDO EL CAMINO para la visualización
                ResultadoBusquedaBFS resultadoBFS = bfsParaPalabraEspecifica(nodoInicio, palabraNormalizada);
                encontrada = resultadoBFS.encontrada;
                caminoEncontrado = resultadoBFS.camino;
            } else {
                return new ResultadoBusquedaEspecifica(false, null, "Método de búsqueda no válido. Use 'DFS' o 'BFS'.");
            }

            if (encontrada) {
                // Si la palabra es encontrada, agregarla al diccionario.
                diccionario.agregarPalabra(palabraNormalizada);
                return new ResultadoBusquedaEspecifica(true, caminoEncontrado, "Palabra encontrada y agregada al diccionario.");
            }
        }
        return new ResultadoBusquedaEspecifica(false, null, "Palabra no encontrada en el tablero.");
    }

    /**
     * Método auxiliar DFS para buscar una palabra específica.
     * No captura el camino para la visualización (ya que el requisito E es solo para BFS).
     *
     * @param nodoActual El nodo actual en la búsqueda.
     * @param palabraActual La palabra formada hasta el nodo actual.
     * @param caminoActual Stack que representa el camino de nodos para la palabra actual.
     * @param palabraObjetivo La palabra a buscar.
     * @return true si la palabra objetivo es encontrada, false en caso contrario.
     */
    private boolean dfsParaPalabraEspecifica(Nodo nodoActual, String palabraActual, Stack<Nodo> caminoActual, String palabraObjetivo) {
        // Marcamos el nodo actual como visitado para esta ruta
        caminoActual.push(nodoActual);
        palabraActual += nodoActual.getLetra();

        // Poda: Si la palabra actual ya es más larga que la objetivo o no es un prefijo
        if (palabraActual.length() > palabraObjetivo.length() || !palabraObjetivo.startsWith(palabraActual)) {
            caminoActual.pop(); // Backtrack
            return false;
        }

        // Si encontramos la palabra objetivo
        if (palabraActual.equals(palabraObjetivo)) {
            // No necesitamos hacer pop aquí, ya que el true se propaga.
            // La pila caminoActual contendrá la ruta, pero no la devolvemos en este método.
            return true;
        }

        // Explora los vecinos
        for (Nodo vecino : nodoActual.getVecinos()) {
            if (!caminoActual.contains(vecino)) {
                if (dfsParaPalabraEspecifica(vecino, palabraActual, caminoActual, palabraObjetivo)) {
                    return true;
                }
            }
        }

        caminoActual.pop(); // Backtrack: Deshacemos la visita al salir
        return false;
    }


    /**
     * Método auxiliar BFS para buscar una palabra específica, capturando el camino.
     *
     * @param nodoInicio El nodo desde el cual iniciar la búsqueda.
     * @param palabraObjetivo La palabra a buscar.
     * @return Un objeto ResultadoBusquedaBFS que contiene si fue encontrada y el camino.
     */
    private ResultadoBusquedaBFS bfsParaPalabraEspecifica(Nodo nodoInicio, String palabraObjetivo) {
        Queue<EstadoBusquedaConCamino> cola = new LinkedList<>();
        // El camino se almacena como una lista de nodos
        List<Nodo> caminoInicial = new ArrayList<>();
        caminoInicial.add(nodoInicio);
        cola.add(new EstadoBusquedaConCamino(nodoInicio, "" + nodoInicio.getLetra(), new HashSet<>(caminoInicial), caminoInicial));

        while (!cola.isEmpty()) {
            EstadoBusquedaConCamino estadoActual = cola.poll();
            Nodo nodo = estadoActual.nodo;
            String palabraActual = estadoActual.palabra;
            Set<Nodo> visitadosEnCamino = estadoActual.visitados;
            List<Nodo> caminoEncontrado = estadoActual.camino;

            // Poda: Si la palabra actual ya es más larga que la objetivo o no es un prefijo
            if (palabraActual.length() > palabraObjetivo.length() || !palabraObjetivo.startsWith(palabraActual)) {
                continue;
            }

            // Si encontramos la palabra objetivo
            if (palabraActual.equals(palabraObjetivo)) {
                return new ResultadoBusquedaBFS(true, caminoEncontrado);
            }

            for (Nodo vecino : nodo.getVecinos()) {
                if (!visitadosEnCamino.contains(vecino)) {
                    // Crear nuevas copias para el próximo estado
                    Set<Nodo> nuevosVisitados = new HashSet<>(visitadosEnCamino);
                    nuevosVisitados.add(vecino);
                    List<Nodo> nuevoCamino = new ArrayList<>(caminoEncontrado);
                    nuevoCamino.add(vecino);

                    cola.add(new EstadoBusquedaConCamino(vecino, palabraActual + vecino.getLetra(), nuevosVisitados, nuevoCamino));
                }
            }
        }
        return new ResultadoBusquedaBFS(false, null); // Palabra no encontrada
    }


    // --- Clases Internas Auxiliares ---

    /**
     * Clase para mantener el estado de la búsqueda BFS general.
     * No necesita almacenar el camino completo, solo los nodos visitados.
     */
    private static class EstadoBusqueda {
        final Nodo nodo;
        final String palabra;
        final Set<Nodo> visitados; // Nodos visitados en el camino actual

        EstadoBusqueda(Nodo nodo, String palabra, Set<Nodo> visitados) {
            this.nodo = nodo;
            this.palabra = palabra;
            this.visitados = visitados;
        }
    }

    /**
     * Clase para mantener el estado de la búsqueda BFS para una palabra específica,
     * incluyendo el camino de nodos para su visualización.
     */
    private static class EstadoBusquedaConCamino extends EstadoBusqueda {
        final List<Nodo> camino; // Lista de nodos en el camino hasta este estado

        EstadoBusquedaConCamino(Nodo nodo, String palabra, Set<Nodo> visitados, List<Nodo> camino) {
            super(nodo, palabra, visitados);
            this.camino = camino;
        }
    }

    /**
     * Clase para encapsular el resultado de una búsqueda de palabra específica.
     * Contiene si la palabra fue encontrada, el camino (si es BFS y se encontró), y un mensaje.
     */
    public static class ResultadoBusquedaEspecifica {
        public final boolean encontrada;
        public final List<Nodo> camino; // Solo relevante para BFS específico
        public final String mensaje;

        public ResultadoBusquedaEspecifica(boolean encontrada, List<Nodo> camino, String mensaje) {
            this.encontrada = encontrada;
            this.camino = camino;
            this.mensaje = mensaje;
        }
    }

    /**
     * Clase auxiliar interna para devolver el resultado de bfsParaPalabraEspecifica.
     */
    private static class ResultadoBusquedaBFS {
        final boolean encontrada;
        final List<Nodo> camino;

        ResultadoBusquedaBFS(boolean encontrada, List<Nodo> camino) {
            this.encontrada = encontrada;
            this.camino = camino;
        }
    }
}
