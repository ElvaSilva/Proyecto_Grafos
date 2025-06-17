/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_grafos;

/**
 *
 * @author elva
 */
public class Diccionario {
    Palabra p_first;
    Nodo n_first;
    int size;

        public Diccionario(Palabra p_first, Nodo n_first) {
            this.p_first = p_first;
            this.n_first = n_first;
            this.size = 0;
        }
    
    public void BFS(Palabra word){
        if (this.size == 0){
            "hay que printear el error";
        }else{
            long t_o = 0;
            long t_f = 0;
            long resul = 0;
            t_o = System.currentTimeMillis();
            Nodo aux = new Nodo();
            Nodo auxint = new Nodo();
            aux = this.n_first;
            while (aux != null){
                int cont_p = 0;
                int cont_n = 0;
                while (cont_p < word.info.length()){
                    if (aux.letra == word.info.charAt(cont_p)){
                        aux.enUSo = true;
                        auxint = aux.vecinos[cont_n];
                        cont_p = cont_p + 1;
                        if (auxint.enUSo == false && auxint.letra == word.info.charAt(cont_p)){
                            auxint.enUSo = true;
                            aux = auxint;
                        }else{
                            while (cont_n <= aux.vecinos.length){
                                cont_n = cont_n +1;
                                auxint = aux.vecinos[cont_n];
                                if (auxint.enUSo == false && auxint.letra == word.info.charAt(cont_p)){
                                    auxint.enUSo = true;
                                    aux = auxint;
                                    break;
                                }
                            }
                            cont_n = 0;
                        }     
                    }else{
                        aux = aux.vecinos[0];
                    }
                }
                word.encontrada = true;
                t_f = System.currentTimeMillis();
                word.tiempo = t_f - t_o;
                return;
            }
        }
    }
}
