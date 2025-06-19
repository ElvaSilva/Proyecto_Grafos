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
    int num;

    public Nodo() {
        this.letra = '\0';
        this.enUSo = false;
        this.vecinos = new Nodo[8];
        this.num = 0;
    }

    public Nodo(char letra) {
        this.letra = letra;
        this.enUSo = false;
        this.vecinos = new Nodo[8];
        this.num =0;
    }
    
    
}
