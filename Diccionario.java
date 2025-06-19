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

        public Diccionario() {
            this.p_first = null;
            this.n_first = null;
            this.size = 0;
        }
    
    public void BFS(Palabra word){
        if (this.size == 0){
            /**hay que printear el error"*/
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
                                if ((auxint.enUSo == false) && (auxint.letra == word.info.charAt(cont_p))){
                                    auxint.enUSo = true;
                                    aux = auxint;
                                    break;
                                }
                            }
                            cont_n = 0;
                        }     
                    }else{
                        if(aux.num != 16){
                            aux = aux.vecinos[0];
                        }else{
                            return;
                        }
                    }
                }
                word.encontrada = true;
                t_f = System.currentTimeMillis();
                word.tiempo = t_f - t_o;
                return;
            }
        }
    }

    public void poner_vecinos(){
        Nodo let_1 = new Nodo();
        Nodo let_2 = new Nodo();
        while(let_1.num <= 16){
            while(let_2.num <= 16){
                if(let_1.num == 1 || let_1.num == 4 || let_1.num == 13 || let_1.num == 16){
                    if(let_1.num == 1 && let_2.num == (let_1.num +4)){
                        let_1.vecinos[1] = let_2;
                    }
                    if(let_1.num == 1 && let_2.num == (let_1.num +5))
                    if(let_1.num == 4 && let_2.num == (let_1.num +4)){
                        let_1.vecinos[1] = let_2;
                    }
                    if(let_1.num == 4 && let_2.num == (let_1.num +4)){
                        let_1.vecinos[1] = let_2;
                    }{
                        let_1.vecinos[2] = let_2;
                    }
                }else if(let_1.num == 6 || let_1.num == 7 || let_1.num == 10 || let_1.num == 11){
                    if(let_2.num == (let_1.num + 3)){
                        let_1.vecinos[1] = let_2;
                    }
                    if(let_2.num == (let_1.num + 4)){
                        let_1.vecinos[2] = let_2;
                    }
                    if(let_2.num == (let_1.num + 5)){
                        let_1.vecinos[3] = let_2;
                    }
                    if(let_2.num == (let_1.num - 5)){
                        let_1.vecinos[4] = let_2;
                    }
                    if(let_2.num == (let_1.num - 4)){
                        let_1.vecinos[5] = let_2;
                    }
                    if(let_2.num == (let_1.num - 3)){
                        let_1.vecinos[6] = let_2;
                    }
                    if(let_2.num == (let_1.num - 1)){
                        let_1.vecinos[7] = let_2;
                    }
                    
                }else{
                    
                }
                let_2 = let_2.vecinos[0];
            }
            let_1 = let_1.vecinos[0];
        }
    }
}
