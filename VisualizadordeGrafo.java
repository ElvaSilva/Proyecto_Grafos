/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author Windows User
 */

import java.awt.Component;
import javax.swing.*;
import java.util.List;

// Asegúrate de que las clases Nodo y BusquedaDePalabras.ResultadoBusquedaEspecifica
// estén definidas y que Nodo tenga métodos getFila(), getColumna(), getLetra()

public class VisualizadordeGrafo { // Cambiado a 'VisualizadorDeGrafo'

    private Graph grafo; // Cambiado a 'grafo'
    private final String ID_ATRIBUTO_COLOR = "ui.class"; // Para estilos CSS en GraphStream
    private final String CLASE_NODO_RAIZ = "root";
    private final String CLASE_NODO_CAMINO = "path-node";
    private final String CLASE_ARISTA_CAMINO = "path-edge";

    /**
     * Constructor de la clase VisualizadorDeGrafo.
     * Configura la propiedad del sistema para usar Swing como motor de UI de GraphStream.
     */
    public VisualizadordeGrafo() {
        // Importante: Esto debe configurarse antes de cualquier operación de GraphStream
        System.setProperty("org.graphstream.ui", "swing");
        // Asegúrate de que los archivos CSS sean accesibles (por ejemplo, en el classpath o en la misma carpeta)
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    }

    /**
     * Visualiza el árbol de recorrido BFS para una palabra específica encontrada.
     * El grafo mostrará solo los nodos y aristas que forman la palabra en el camino encontrado.
     *
     * @param caminoPalabra La lista de Nodos que forman la palabra encontrada.
     * @param palabra La palabra que fue encontrada (para el título de la ventana).
     * @param parentComponent El componente padre para el JFrame (normalmente la ventana principal de la GUI).
     * @throws IllegalArgumentException Si el camino de la palabra es nulo o vacío.
     */
    public void visualizarArbolBFS(List<Nodo> caminoPalabra, String palabra, Component parentComponent) {
        if (caminoPalabra == null || caminoPalabra.isEmpty()) {
            throw new IllegalArgumentException("El camino de la palabra no puede ser nulo o vacío para la visualización.");
        }

        // Limpia el grafo existente y crea uno nuevo para la visualización actual
        this.grafo = new SingleGraph("Recorrido BFS para: " + palabra);

        // Aplicar estilos CSS básicos para mejorar la visualización
        grafo.setAttribute("ui.stylesheet", getGraphStylesheet());

        // Construir el grafo basado en el camino de la palabra
        construirGrafoDelCamino(caminoPalabra);

        // Configurar y mostrar el JFrame con el visor de GraphStream
        JFrame frame = new JFrame("Visualización del Recorrido BFS para: " + palabra);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana, no la aplicación
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(parentComponent); // Centrar respecto al componente padre

        // Crea el visor en un hilo separado para que la interfaz no se congele
        Viewer viewer; // 'false' indica que no se detenga la ejecución del hilo principal
        viewer = (Viewer) grafo.display(false);

        // Añadir el panel de visualización al JFrame
        // viewer.getDefaultView() devuelve el componente JComponent para incrustar
        frame.add(viewer.getDefaultView(), "Center"); // Asegúrate de que estás usando 'viewer.getDefaultView()'

        frame.setVisible(true);

        // Opcional: Asegurarse de que el visor se apague correctamente al cerrar la ventana
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                viewer.close(); // Detiene el hilo de renderizado de GraphStream
            }
        });
    }

    /**
     * Construye el grafo de GraphStream a partir de la lista de Nodos que forman la palabra.
     * Añade los nodos y las aristas en la secuencia del camino, aplicando estilos.
     *
     * @param caminoPalabra La lista ordenada de Nodos que forman la palabra.
     */
    private void construirGrafoDelCamino(List<Nodo> caminoPalabra) {
        Nodo nodoAnterior = null;
        int edgeCounter = 0; // Para asegurar IDs de aristas únicos si hay ciclos o caminos repetidos

        for (int i = 0; i < caminoPalabra.size(); i++) {
            Nodo nodoActual = caminoPalabra.get(i);
            String idNodoActual = generarIdUnico(nodoActual); // Genera ID único basado en coordenadas

            // Añadir nodo si no existe
            if (grafo.getNode(idNodoActual) == null) {
                Node gsNode = grafo.addNode(idNodoActual);
                gsNode.setAttribute("ui.label", String.valueOf(nodoActual.getLetra())); // Etiqueta con la letra
                gsNode.setAttribute(ID_ATRIBUTO_COLOR, CLASE_NODO_CAMINO); // Clase para estilo de nodo en el camino

                // Marcar el primer nodo como raíz
                if (i == 0) {
                    gsNode.setAttribute(ID_ATRIBUTO_COLOR, CLASE_NODO_RAIZ);
                }
            }

            // Añadir arista si hay un nodo anterior
            if (nodoAnterior != null) {
                String idNodoAnterior = generarIdUnico(nodoAnterior);
                // Usar un ID de arista único, ya que podría haber múltiples aristas entre los mismos nodos
                // o para diferenciar las aristas que forman la palabra.
                String idArista = idNodoAnterior + "-" + idNodoActual + "-" + (edgeCounter++);
                Edge gsEdge; // true = dirigida
                gsEdge = (Edge) grafo.addEdge(idArista, idNodoAnterior, idNodoActual, true);
                gsEdge.setAttribute(ID_ATRIBUTO_COLOR, CLASE_ARISTA_CAMINO); // Clase para estilo de arista en el camino
            }
            nodoAnterior = nodoActual;
        }
    }

    /**
     * Genera un ID único para un Nodo GraphStream basado en las coordenadas del Nodo.
     * Esto es crucial porque las letras pueden repetirse en el tablero.
     * Ejemplo: "N_0_0" para el nodo en (0,0), "N_0_1" para (0,1).
     * @param nodo El Nodo de tu modelo de datos.
     * @return Un String único que identifica el nodo.
     */
    private String generarIdUnico(Nodo nodo) {
        return "N_" + nodo.getFila() + "_" + nodo.getColumna();
    }

    /**
     * Define y retorna el estilo CSS para el grafo.
     * Esto ayuda a hacer la visualización más clara y atractiva.
     */
    private String getGraphStylesheet() {
        return "node {" +
               "   text-mode: normal;" +
               "   text-size: 14px;" +
               "   text-color: #333;" +
               "   text-alignment: center;" +
               "   text-padding: 3px;" +
               "   text-background-mode: rounded-box;" +
               "   text-background-color: #EEE;" +
               "   shape: circle;" +
               "   size: 30px;" +
               "   fill-mode: gradient-radial;" +
               "   fill-color: #DDD, #AAA;" +
               "   stroke-mode: plain;" +
               "   stroke-color: #999;" +
               "   stroke-width: 1px;" +
               "}" +
               "node.root {" +
               "   fill-color: #FF7F50;" + // Color naranja coral para la raíz
               "   stroke-color: #E64A19;" +
               "   size: 35px;" + // Un poco más grande
               "}" +
               "node.path-node {" +
               "   fill-color: #87CEEB;" + // Azul cielo para nodos del camino
               "   stroke-color: #4682B4;" +
               "}" +
               "edge {" +
               "   shape: line;" +
               "   size: 2px;" +
               "   fill-color: #CCC;" +
               "   arrow-size: 8px, 4px;" + // Añade una flecha
               "   text-mode: normal;" +
               "   text-size: 12px;" +
               "}" +
               "edge.path-edge {" +
               "   fill-color: #FFD700;" + // Oro para aristas del camino
               "   size: 3px;" +
               "   arrow-size: 10px, 5px;" +
               "}";
    }

    private static class Viewer {

        public Viewer() {
        }

        private Component getDefaultView() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void close() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    private static class Edge {

        public Edge() {
        }

        private void setAttribute(String ID_ATRIBUTO_COLOR, String CLASE_ARISTA_CAMINO) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }
}
