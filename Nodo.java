/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_grafos;

/**
 *
 * @author elva
 */
public class Nodo {
    char letra;
    boolean enUSo;
    Nodo[] vecinos;

    public Nodo() {
        this.letra = '\0';
        this.enUSo = false;
        this.vecinos = null;
    }

    
    public Nodo(char letra, int numVecinos) {
        this.letra = letra;
        this.enUSo = false;
        this.vecinos = new Nodo[numVecinos];
    }
    
    
}
