/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author elohym
 */

import javax.swing.*;
import java.awt.Component;
import java.util.List;

public class VisualizadorDeGrafo {
    private Graph grafo;
    private final String ID_ATRIBUTO_COLOR = "ui.class";
    private final String CLASE_NODO_RAIZ = "root";
    private final String CLASE_NODO_CAMINO = "path-node";
    private final String CLASE_ARISTA_CAMINO = "path-edge";

    public VisualizadorDeGrafo() {
        System.setProperty("org.graphstream.ui", "swing");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    }

    public void visualizarArbolBFS(List<Nodo> caminoPalabra, String palabra, Component parentComponent) {
        if (caminoPalabra == null || caminoPalabra.isEmpty()) {
            throw new IllegalArgumentException("El camino de la palabra no puede ser nulo o vacío para la visualización.");
        }

        this.grafo = new SingleGraph("Recorrido BFS para: " + palabra);
        grafo.setAttribute("ui.stylesheet", getGraphStylesheet());

        construirGrafoDelCamino(caminoPalabra);

        JFrame frame = new JFrame("Visualización del Recorrido BFS para: " + palabra);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(parentComponent);

        Viewer viewer = (Viewer) grafo.display(false);

        // Asegúrate de que este JComponent se añade al frame.
        // viewer.getDefaultView() devuelve un JComponent que es el panel de renderizado.
        frame.add(viewer.getDefaultView(), "Center");

        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                viewer.close();
            }
        });
    }

    private void construirGrafoDelCamino(List<Nodo> caminoPalabra) {
        Nodo nodoAnterior = null;
        int edgeCounter = 0;

        for (int i = 0; i < caminoPalabra.size(); i++) {
            Nodo nodoActual = caminoPalabra.get(i);
            String idNodoActual = generarIdUnico(nodoActual);

            if (grafo.getNode(idNodoActual) == null) {
                Node gsNode = grafo.addNode(idNodoActual);
                gsNode.setAttribute("ui.label", String.valueOf(nodoActual.getLetra()));
                gsNode.setAttribute(ID_ATRIBUTO_COLOR, CLASE_NODO_CAMINO);

                if (i == 0) {
                    gsNode.setAttribute(ID_ATRIBUTO_COLOR, CLASE_NODO_RAIZ);
                }
            }

            if (nodoAnterior != null) {
                String idNodoAnterior = generarIdUnico(nodoAnterior);
                String idArista = idNodoAnterior + "-" + idNodoActual + "-" + (edgeCounter++);
                Edge gsEdge = (Edge) grafo.addEdge(idArista, idNodoAnterior, idNodoActual, true);
                gsEdge.setAttribute(ID_ATRIBUTO_COLOR, CLASE_ARISTA_CAMINO);
            }
            nodoAnterior = nodoActual;
        }
    }

    private String generarIdUnico(Nodo nodo) {
        return "N_" + nodo.getFila() + "_" + nodo.getColumna();
    }

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
               "   fill-color: #FF7F50;" +
               "   stroke-color: #E64A19;" +
               "   size: 35px;" +
               "}" +
               "node.path-node {" +
               "   fill-color: #87CEEB;" +
               "   stroke-color: #4682B4;" +
               "}" +
               "edge {" +
               "   shape: line;" +
               "   size: 2px;" +
               "   fill-color: #CCC;" +
               "   arrow-size: 8px, 4px;" +
               "   text-mode: normal;" +
               "   text-size: 12px;" +
               "}" +
               "edge.path-edge {" +
               "   fill-color: #FFD700;" +
               "   size: 3px;" +
               "   arrow-size: 10px, 5px;" +
               "}";
    }

    private static class Edge {

        public Edge() {
        }

        private void setAttribute(String ID_ATRIBUTO_COLOR, String CLASE_ARISTA_CAMINO) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }

    private static class Viewer {

        public Viewer() {
        }

        private void close() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private Component getDefaultView() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
