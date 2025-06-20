/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author elohym
 */

import java.text.Normalizer; // Para manejar acentos
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class Diccionario { // Cambiado a 'Diccionario' para consistencia

    // Conjunto de palabras del diccionario. Se hace 'final' para asegurar que la referencia
    // al HashSet no cambie después de la inicialización.
    private final Set<String> palabras;

    public Diccionario() {
        this.palabras = new HashSet<>();
    }

    /**
     * Constructor que permite inicializar el diccionario con un conjunto preexistente de palabras.
     * Útil si las palabras son leídas por otra clase (ej. GestionDeArchivos) y pasadas aquí.
     *
     * @param palabrasIniciales Un conjunto de cadenas a añadir al diccionario.
     */
    public Diccionario(Set<String> palabrasIniciales) {
        this(); // Llama al constructor por defecto para inicializar 'palabras'
        if (palabrasIniciales != null) {
            // Se añaden las palabras, asegurando normalización y validación.
            for (String palabra : palabrasIniciales) {
               
                agregarPalabra(palabra);
            }
        }
    }

    /**
     * Limpia el diccionario actual y carga palabras desde un archivo.
     * Esta clase no debería manejar directamente la apertura/cierre del archivo.
     * En su lugar, debería recibir una lista de palabras ya leídas por 'GestionDeArchivos'.
     * O, alternativamente, 'GestionDeArchivos' llama a 'agregarPalabra' por cada línea leída.
     *
     * @param palabrasLeidasUnaPorUna Lista de palabras leídas del archivo.
     */
    public void cargarPalabras(List<String> palabrasLeidasUnaPorUna) {
        this.palabras.clear(); // Limpia el diccionario actual antes de cargar nuevas palabras
        if (palabrasLeidasUnaPorUna != null) {
            for (String linea : palabrasLeidasUnaPorUna) {
                // Normaliza y agrega cada palabra.
                agregarPalabra(linea); // Reutiliza la lógica de agregarPalabra
            }
        }
    }

    /**
     * Verifica si una palabra (ignorando mayúsculas/minúsculas y acentos) está en el diccionario.
     *
     * @param palabra La palabra a verificar.
     * @return true si la palabra existe en el diccionario, false en caso contrario.
     */
    public boolean contiene(String palabra) { // Cambiado a 'contiene'
        if (palabra == null || palabra.trim().isEmpty()) {
            return false;
        }
        // Normaliza la palabra de entrada antes de buscarla
        String palabraNormalizada = normalizarPalabra(palabra);
        return palabras.contains(palabraNormalizada);
    }

    /**
     * Agrega una nueva palabra al diccionario, si cumple con los criterios.
     * La palabra se normaliza (sin acentos, mayúsculas) y se verifica su longitud.
     *
     * @param palabra La palabra a agregar.
     * @return true si la palabra fue agregada, false si no cumple los requisitos o ya existía.
     */
    public boolean agregarPalabra(String palabra) { // Cambiado a 'agregarPalabra'
        if (palabra == null || palabra.trim().isEmpty()) {
            return false;
        }

        String palabraNormalizada = normalizarPalabra(palabra);

        // La palabra debe tener al menos 3 letras, según el requerimiento
        if (palabraNormalizada.length() < 3) {
            // No se imprime por consola. En un contexto GUI, esto se comunicaría a la interfaz.
            // System.out.println("La palabra '" + palabra + "' debe tener al menos 3 letras para ser agregada.");
            return false;
        }
        return this.palabras.add(palabraNormalizada); // add() retorna true si se añade, false si ya existía
    }

    /**
     * Guardar el diccionario actualizado en un archivo.
     * Similar a 'loadFromFile', esta operación debería ser coordinada por 'GestionDeArchivos'.
     * Aquí simplemente proporcionamos las palabras para que 'GestionDeArchivos' las escriba.
     *
     * @return Un conjunto inmutable de todas las palabras en el diccionario.
     */
    public Set<String> obtenerTodasLasPalabras() { // Cambiado a 'obtenerTodasLasPalabras'
        // Devolvemos una vista inmutable para proteger el conjunto interno.
        return Collections.unmodifiableSet(palabras);
    }

    /**
     * Normaliza una palabra: la convierte a mayúsculas y remueve acentos.
     * Esto asegura que las palabras en el diccionario y las buscadas sean consistentes.
     *
     * @param palabra La palabra a normalizar.
     * @return La palabra normalizada.
     */
    String normalizarPalabra(String palabra) {
        String normalizada = Normalizer.normalize(palabra, Normalizer.Form.NFD);
        // Elimina diacríticos (acentos) y convierte a mayúsculas
        return normalizada.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toUpperCase();
    }

    /**
     * Devuelve el número de palabras en el diccionario.
     * @return El tamaño del diccionario.
     */
    public int getTamano() {
        return palabras.size();
    }

    // Opcional: Método toString para depuración o visualización en la interfaz
    @Override
    public String toString() {
        // Formato para mostrar en la interfaz, por ejemplo, en un JTextArea
        return palabras.stream()
                       .collect(Collectors.joining("\n"));
    }
    
    /**
     * Verifica si una cadena es un prefijo de alguna palabra en el diccionario.
     * Esta implementación es O(N*L) donde N es el número de palabras y L la longitud,
     * un Trie lo haria en O(L).
     * 
     * @param prefijo la cadena verificar.
     * @return true si es un prefijo de alguna palabra, false en lo contrario. 
     */
    
    public boolean esPrefijo(String prefijo) {
        if (prefijo == null || prefijo.isEmpty()) {
            return true; // Cadena vacía es prefijo de todo
        }
        String prefijoNormalizado = normalizarPalabra(prefijo);
        for (String palabraDiccionario : palabras) {
            if (palabraDiccionario.startsWith(prefijoNormalizado)) {
                return true;
            }
        }
        return false;
    }

   
    }
