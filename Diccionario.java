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
    
    public boolean BFS(Palabra word){
        if (this.size == 0){
            return false;
        }else{
            Nodo aux = new Nodo();
            Nodo auxint = new Nodo();
            aux = this.n_first;
            while (aux != null){
                for (int i_p = 0; i_p <word.info.length(); i_p++){
                    if (aux.letra == word.info.charAt(i_p)){
                        aux.enUSo = true;
                        for (int i=0; i < aux.vecinos.length; i++){
                            auxint = aux.vecinos[i];
                            
                            
                        }
                    }else{
                        aux = aux.vecinos[0];
                    }
                } 
                int cont_p = 0;
                int cont_n = 0;
                while (cont_p <= word.info.length()){
                    if (aux.enUSo == false && aux.letra == word.info.charAt(cont_p)){
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
                                    cont_p = cont_p + 1;
                                    break;
                                }
                            }
                        }
                        cont_p = cont_p + 1;
                            
                        
                            
                           
                    }else{
                        aux = aux.vecinos[0];
                    }
                }
            }
        }
    }
}
