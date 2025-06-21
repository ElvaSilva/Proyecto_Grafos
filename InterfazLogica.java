/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;

/**
 *
 * @author elohym
 */

import boceto.de.proyecto.ArchivoException;
import boceto.de.proyecto.BusquedaDePalabras;
import boceto.de.proyecto.Diccionario;
import boceto.de.proyecto.GestionDeArchivos;
import boceto.de.proyecto.Tablero;
import boceto.de.proyecto.VisualizadorDeGrafo;
import javax.swing.*;
import java.awt.Component; // Para los diálogos del JFileChooser
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class InterfazLogica {

    private final Tablero tablero;
    private final Diccionario diccionario;
    private final BusquedaDePalabras busquedaDePalabras;
    private final GestionDeArchivos gestionDeArchivos;
    private final VisualizadorDeGrafo visualizadorDeGrafo;

    private final Component parentComponent; // Para los JOptionPane y JFileChooser

    public InterfazLogica(Component parent) {
        this.parentComponent = parent;
        this.tablero = new Tablero();
        this.diccionario = new Diccionario();
        this.busquedaDePalabras = new BusquedaDePalabras(tablero, diccionario);
        this.gestionDeArchivos = new GestionDeArchivos();
        this.visualizadorDeGrafo = new VisualizadorDeGrafo();
    }

    public String cargarTablero() {
        try {
            List<String> lineasTablero = gestionDeArchivos.cargarArchivo(parentComponent);
            tablero.cargarDesdeLineas(lineasTablero);
            mostrarMensaje("Tablero cargado exitosamente.", "Carga Exitosa", JOptionPane.INFORMATION_MESSAGE);
            return tablero.toString(); // Retorna la representación del tablero para mostrar en la GUI
        } catch (ArchivoException e) {
            mostrarMensaje(e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            return null; // O una cadena vacía para indicar fallo
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error en el formato del archivo del tablero: " + e.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String cargarDiccionario() {
        try {
            List<String> lineasDiccionario = gestionDeArchivos.cargarArchivo(parentComponent);
            diccionario.cargarPalabras(lineasDiccionario); // El diccionario ya maneja los tags "dic"
            mostrarMensaje("Diccionario cargado exitosamente. Palabras: " + diccionario.getTamano(), "Carga Exitosa", JOptionPane.INFORMATION_MESSAGE);
            return diccionario.toString(); // Retorna la representación del diccionario para mostrar en la GUI
        } catch (ArchivoException e) {
            mostrarMensaje(e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String buscarTodasLasPalabrasDFS() {
        if (!isTableroCargado()) {
            mostrarMensaje("Cargue un tablero primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return "";
        }
        if (!isDiccionarioCargado()) {
            mostrarMensaje("Cargue un diccionario primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return "";
        }

        long startTime = System.currentTimeMillis();
        Set<String> palabrasEncontradas = busquedaDePalabras.buscarPalabrasDFS();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        StringBuilder sb = new StringBuilder();
        sb.append("Palabras encontradas (DFS):\n");
        if (palabrasEncontradas.isEmpty()) {
            sb.append("Ninguna palabra encontrada.\n");
        } else {
            palabrasEncontradas.stream().sorted().forEach(p -> sb.append(p).append("\n"));
        }
        sb.append("\nTiempo de ejecución: ").append(duration).append(" ms\n");
        return sb.toString();
    }

    public String buscarTodasLasPalabrasBFS() {
        if (!isTableroCargado()) {
            mostrarMensaje("Cargue un tablero primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return "";
        }
        if (!isDiccionarioCargado()) {
            mostrarMensaje("Cargue un diccionario primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return "";
        }

        long startTime = System.currentTimeMillis();
        Set<String> palabrasEncontradas = busquedaDePalabras.buscarPalabrasBFS();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        StringBuilder sb = new StringBuilder();
        sb.append("Palabras encontradas (BFS):\n");
        if (palabrasEncontradas.isEmpty()) {
            sb.append("Ninguna palabra encontrada.\n");
        } else {
            palabrasEncontradas.stream().sorted().forEach(p -> sb.append(p).append("\n"));
        }
        sb.append("\nTiempo de ejecución: ").append(duration).append(" ms\n");
        return sb.toString();
    }

    public String buscarPalabraEspecifica(String palabra, String metodoBusqueda) {
        if (!isTableroCargado()) {
            mostrarMensaje("Cargue un tablero primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return "";
        }
        if (!isDiccionarioCargado()) {
            mostrarMensaje("Cargue un diccionario primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return "";
        }
        if (palabra == null || palabra.trim().isEmpty()) {
            mostrarMensaje("Por favor, ingrese una palabra para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return "";
        }

        BusquedaDePalabras.ResultadoBusquedaEspecifica resultado =
                busquedaDePalabras.buscarPalabraEspecifica(palabra, metodoBusqueda);

        if (resultado.encontrada) {
            mostrarMensaje(resultado.mensaje, "Palabra Encontrada", JOptionPane.INFORMATION_MESSAGE);
            // Si la búsqueda fue con BFS y se encontró el camino, visualizar
            if ("BFS".equalsIgnoreCase(metodoBusqueda) && resultado.camino != null && !resultado.camino.isEmpty()) {
                try {
                    visualizadorDeGrafo.visualizarArbolBFS(resultado.camino, palabra, parentComponent);
                } catch (IllegalArgumentException e) {
                    mostrarMensaje("Error al visualizar el grafo: " + e.getMessage(), "Error de Visualización", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("DFS".equalsIgnoreCase(metodoBusqueda)) {
                 mostrarMensaje("Visualización del camino solo disponible para búsquedas BFS.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            return "Palabra '" + palabra + "' encontrada. Añadida al diccionario si no existía.";
        } else {
            mostrarMensaje(resultado.mensaje, "Palabra No Encontrada", JOptionPane.WARNING_MESSAGE);
            return "Palabra '" + palabra + "' no encontrada.";
        }
    }

    public void guardarDiccionario() {
        if (!isDiccionarioCargado()) {
            mostrarMensaje("Cargue un diccionario primero para poder guardarlo.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<String> contenidoAGuardar = new ArrayList<>();
            contenidoAGuardar.add("dic");
            // Ordenar las palabras alfabéticamente antes de guardar (opcional, pero buena práctica)
            diccionario.obtenerTodasLasPalabras().stream().sorted().forEach(contenidoAGuardar::add);
            contenidoAGuardar.add("/dic");

            gestionDeArchivos.guardarArchivo(contenidoAGuardar, parentComponent);
            mostrarMensaje("Diccionario guardado exitosamente.", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (ArchivoException e) {
            mostrarMensaje(e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Métodos auxiliares para verificar el estado
    private boolean isTableroCargado() {
        // Asume que un tablero "vacío" o no inicializado correctamente se identifica por getNodo(0,0) == null
        return tablero.getNodo(0, 0) != null;
    }

    private boolean isDiccionarioCargado() {
        return diccionario.getTamano() > 0;
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(parentComponent, mensaje, titulo, tipoMensaje);
    }
}
