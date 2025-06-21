/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boceto.de.proyecto;

/**
 *
 * @author elohym
 */

public class ArchivoException extends Exception {

    private static final long serialVersionUID = 1L;
    public ArchivoException(String message) {
        super(message);
    }

    public ArchivoException(String message, Throwable cause) {
        super(message, cause);
    }
}

