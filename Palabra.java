/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_grafos;

/**
 *
 * @author elva
 */
public class Palabra {
    String info;
    Palabra sig;
    boolean encontrada;
    long tiempo;

    public Palabra(String info) {
        this.info = info;
        this.sig = null;
        this.encontrada = false;
        this.tiempo = 0;
    }
    
    
}
