/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_grafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.
import java.io.IOException;
import javax.swing.JOptionPane;

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
    
    /**Se itera el grafo, realizando comparaciones con cada letra de la palabra.
     * El loop externo cambia al siguiente nodo y el loop interno revisa cada uno 
     * de los vecinos.
     * @param word Se recibe un objeto de tipo Palabra.
     */
    public void BFS(Palabra word){
        if (this.size == 0){
            JOptionPane.showInternalMessageDialog(parentComponent, size);
        }else{
            long t_o = 0;
            long t_f = 0;
            long resul = 0;
            t_o = System.currentTimeMillis();
            Nodo aux = new Nodo();
            Nodo auxint = new Nodo();
            Nodo reset = new Nodo();
            aux = this.n_first;
            while (aux != null){
                int cont_p = 0;
                int cont_n = 0;
                while (cont_p < word.info.length()){
                    if (aux.letra == word.info.charAt(cont_p)){
                        aux.enUSo = true;
                        auxint = aux.vecinos[cont_n];
                        cont_p = cont_p + 1;
                            while (cont_n < aux.vecinos.length){
                                auxint = aux.vecinos[cont_n];
                                if ((auxint.enUSo == false) && (auxint.letra == word.info.charAt(cont_p))){
                                    auxint.enUSo = true;
                                    aux = auxint;
                                    if(cont_n == (word.info.length() - 1)){
                                        word.encontrada = true;
                                        t_f = System.currentTimeMillis();
                                        word.tiempo = t_f - t_o;
                                        while(reset.sig != null){
                                        reset.enUSo = false;
                                        reset = reset.sig;
                                        }
                                        return;
                                    }
                                    break;
                                }
                                if(cont_n == aux.vecinos.length && auxint.enUSo == false){
                                    reset = this.n_first;
                                    while(reset.sig != null){
                                        reset.enUSo = false;
                                        reset = reset.sig;
                                    }
                                }
                                    
                                cont_n = cont_n +1;
                            }
                            cont_n = 0;     
                    }else{
                        if(aux.sig != null){
                            aux = aux.sig;
                        }else{
                            return;
                        }
                    }   
                }
            }
        }
    }

    /**Se recorren los nodos, que se encuentran unidos como lista simple para inicializar
     * la lista de adyacencia de cada uno. La lista de vecinos de algunos nodos no esta 
     * "completa" porque se encuentran en las esquinas o lados del grafo.
     */
    public void poner_vecinos(){
        Nodo let_1 = new Nodo();
        Nodo let_2 = new Nodo();
        while(let_1.num <= 16){
            while(let_2.num <= 16){
                if(let_2.num == (let_1.num + 1)){
                    if(let_1.num != 4 || let_1.num != 8 || let_1.num != 12 || let_1.num != 16){
                        let_1.vecinos[0] = let_2;
                    }
                }
                if(let_2.num == (let_1.num + 3)){
                    if(let_1.num != 1 || let_1.num != 5 || let_1.num != 9 || let_1.num != 13){
                        if(let_1.num != 14 || let_1.num != 15 || let_1.num != 16){
                            let_1.vecinos[1] = let_2;
                        }
                    }
                }
                if(let_2.num == (let_1.num + 4)){
                    if(let_1.num != 13 || let_1.num != 14 || let_1.num != 15 || let_1.num != 16){
                        let_1.vecinos[2] = let_2;
                    }
                }
                if(let_2.num == (let_1.num + 5)){
                    if(let_1.num != 4 || let_1.num != 8 || let_1.num != 12 || let_1.num != 16){
                        if(let_1.num != 13 || let_1.num != 14 || let_1.num != 15){
                            let_1.vecinos[3] = let_2;
                        }
                    }
                }
                if(let_2.num == (let_1.num - 5)){
                    if(let_1.num != 1 || let_1.num != 2 || let_1.num != 3 || let_1.num != 4){
                        if(let_1.num != 5 || let_1.num != 9 || let_1.num != 13){
                            let_1.vecinos[4] = let_2;
                        }
                    }
                }
                if(let_2.num == (let_1.num - 4)){
                    if(let_1.num != 1 || let_1.num != 2 || let_1.num != 3 || let_1.num != 4){
                        let_1.vecinos[5] = let_2;
                    }
                }
                if(let_2.num == (let_1.num - 3)){
                    if(let_1.num != 1 || let_1.num != 2 || let_1.num != 3 || let_1.num != 4){
                        if(let_1.num != 8 || let_1.num != 12 || let_1.num != 16){
                            let_1.vecinos[6] = let_2;
                        }
                    }
                }
                if(let_2.num == (let_1.num - 1)){
                    if(let_1.num != 1 || let_1.num != 5 || let_1.num != 9 || let_1.num != 13){
                        let_1.vecinos[7] = let_2;
                    }
                }
                let_2 = let_2.sig;
            }
            let_1 = let_1.sig;
        }
    }
    
    /**Se genera una pantalla en la que se puede seleccionar el archivo y luego 
     * se van a generar palabras y nodos de acuerdo al archivo.
     * @return String[] se devuelve un arreglo con las letras
     */
    public String[] subir_dic(){
        Nodo let_2 = new Nodo();
        String[] texto = null;
        int aux_num = 0;
        int aux_num2 = 0;
        Palabra pal_2 = new Palabra();
        try{
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(this);
            File archivo = file.getSelectedFile();
            if(archivo != null){
                FileReader archivo_2 = new FileReader(archivo);
                BufferedReader lee = new BufferedReader (archivo_2);
                while(lee.readLine()!=null){
                    if(lee.readLine() == "dic"){
                        aux_num = aux_num + 1;
                    }
                    if(lee.readLine() == "tab"){
                        aux_num = aux_num + 1;
                    }
                    while((lee.readLine() != "/dic") || (lee.readLine() != "/tab")){
                        if(aux_num == 1){
                            Palabra word = new Palabra(lee.readLine());
                            if(this.p_first == null){
                                this.p_first = word;
                            }else{
                                pal_2 = this.p_first;
                                while(pal_2.sig!=null){
                                    pal_2 = pal_2.sig;
                                }
                                pal_2.sig = word;
                            }
                        }
                        if(aux_num == 2){
                            texto = lee.readLine().split(",");
                            for(int i = 0; i < texto.length; i ++){
                                Nodo letter = new Nodo(texto[i].charAt(0));
                                if(this.n_first == null){
                                    this.n_first = letter;
                                }else{
                                    let_2 = this.n_first;
                                    while(let_2.sig != null){
                                        aux_num2 = aux_num2 + 1;
                                        let_2.num = aux_num2;
                                        let_2 = let_2.sig;
                                    }
                                    let_2.sig = letter;
                                    aux_num = aux_num + 1;
                                }
                            }
                        }   
                    }
                    lee.close();
                }
            }
            this.poner_vecinos();
        }catch(IOException e){
            JOptionPane.showMessageDialog(parentComponent, size);
            
        }
        return texto;
    }
    
    /**Se revisa cada palabra del diccionario y se compara con la proporcionada.
     * 
     * @param word 
     */
    public void existe(Palabra word){
        boolean exist = false;
        Palabra aux = new Palabra();
        aux = this.p_first;
        while (aux.sig != null){
            if(aux.info.equals(word.info)){
                exist = true;
            }
            aux = aux.sig;
        }
        if(exist == false){
            while(aux.sig != null){
                aux = aux.sig;
            }
            word = aux.sig;
        }
    }
    
    /**Se recorre el diccionario y se agrega la palabra a un texto.
     * 
     * @return String texto con todas las palabras
     */
    public String mostrarPalabras(){
        Palabra aux = this.p_first;
        String text = "";
        while(aux.sig != null){
            if(aux == this.p_first){
                text = aux.info;
            }
            text = text + "/n" + aux.info;
        }
        return text;
    }
}
