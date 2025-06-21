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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionDeArchivos {
    private final JFileChooser seleccionadorDeArchivos;
    private File ultimoDirectorioAbierto;

    public GestionDeArchivos() {
        this.seleccionadorDeArchivos = new JFileChooser();
        FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Archivos de Texto (*.txt)", "txt");
        seleccionadorDeArchivos.addChoosableFileFilter(filtroTxt);
        this.ultimoDirectorioAbierto = new File(System.getProperty("user.dir"));
        seleccionadorDeArchivos.setCurrentDirectory(ultimoDirectorioAbierto);
    }

    public List<String> cargarArchivo(Component parentComponent) throws ArchivoException {
        List<String> contenidoArchivo = new ArrayList<>();
        seleccionadorDeArchivos.setDialogTitle("Seleccionar Archivo de Tablero o Diccionario");

        int valorRetorno = seleccionadorDeArchivos.showOpenDialog(parentComponent);

        if (valorRetorno == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = seleccionadorDeArchivos.getSelectedFile();
            this.ultimoDirectorioAbierto = archivoSeleccionado.getParentFile();
            seleccionadorDeArchivos.setCurrentDirectory(ultimoDirectorioAbierto);

            try (BufferedReader lector = new BufferedReader(new FileReader(archivoSeleccionado))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    contenidoArchivo.add(linea.trim());
                }
                return contenidoArchivo;
            } catch (IOException e) {
                throw new ArchivoException("Error al leer el archivo: " + archivoSeleccionado.getName() + ". " + e.getMessage(), e);
            }
        } else {
            throw new ArchivoException("Operación de carga de archivo cancelada por el usuario.");
        }
    }

    public void guardarArchivo(List<String> content, Component parentComponent) throws ArchivoException {
        seleccionadorDeArchivos.setDialogTitle("Guardar Diccionario");

        int valorRetorno = seleccionadorDeArchivos.showSaveDialog(parentComponent);

        if (valorRetorno == JFileChooser.APPROVE_OPTION) {
            File archivoAGuardar = seleccionadorDeArchivos.getSelectedFile();

            String rutaArchivo = archivoAGuardar.getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".txt")) {
                archivoAGuardar = new File(rutaArchivo + ".txt");
            }

            this.ultimoDirectorioAbierto = archivoAGuardar.getParentFile();
            seleccionadorDeArchivos.setCurrentDirectory(ultimoDirectorioAbierto);

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoAGuardar))) {
                for (String linea : content) {
                    escritor.write(linea);
                    escritor.newLine();
                }
            } catch (IOException e) {
                throw new ArchivoException("Error al guardar el archivo: " + archivoAGuardar.getName() + ". " + e.getMessage(), e);
            }
        } else {
            throw new ArchivoException("Operación de guardado de archivo cancelada por el usuario.");
        }
    }
}
