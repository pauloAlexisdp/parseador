/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author pparr
 */
public class TareasEnJerarquias {
    
    private ArrayList<Tarea> tareas;
    private ArrayList<Artefacto> artefactos;
    private HashMap <String, ArrayList<String>> esOutput;
    //contendra todas las tareas que 
    private HashMap <String,  ArrayList<String>> TareasCompartidas;
    //contendra las tareas que presiden a ls posibles tareas que seran candidatas a generar sub gerarquias
    private HashMap <String, ArrayList<String>> subJerarquias;
    //recibe todas las tareas con su id
    private HashMap <String, Tarea> tareas_id;
    private ArrayList<String> pares;
     
    /**
     * Constructor de la clase
     * @param tareas corresponde al ArrayList que contiene a las tareas que son parte del proceso analizado
     * @param artefacto corresponde al ArrayList que contiene a la información de los artefactos que son parte del proceso analizado
     **/
    public TareasEnJerarquias(ArrayList<Tarea> tareas, ArrayList<Artefacto> artefacto){
        this.tareas=tareas;
        this.artefactos= artefacto;
        this.esOutput= new HashMap <String, ArrayList<String>>();
        
        // se inicializa en buscar tareas independientes.
        this.subJerarquias= new HashMap <String, ArrayList<String>>();
        //contendra todos los ids de las tareas del hashmap que tienen relación, son los ids que tienen los grafos de subgerarquias.
        this.TareasCompartidas=  new HashMap <String, ArrayList<String>>();
        this. tareas_id = new HashMap <String, Tarea>();
        this.pares= new ArrayList();

    }
    
    /**
     * Este metodo se encarga de verificar si las jerarquias preliminares identificadas
     * comparten tareas en comun, de ser asi hay que juntar en una sola jerarquia las jerarquias que comparten esas tareas.
     * para ello se guarda en un arraylist de string parejas de tareas padre (una tarea padre es aquella que es el inicio de la jerarquia de tareas, muy similar a un nodo raiz de un arbol)
     * posteriormente este arraylist sera utilizado para rearmar las sub-jerarquias encontradas de manera preliminaar
     **/
    public void repetidas(){
        for (Map.Entry<String, ArrayList<String>> entry : TareasCompartidas.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            if(value.size()>1){
                for (int i = 0; i < value.size(); i++) {
                    if(i>0){
                        String par= value.get(0)+","+value.get(i);
                        if(!this.pares.contains(par)){
                            this.pares.add(par);         
                        }   
                    }
                }      
            }        
        }
    }
    
    /**
     * Este metodo se encarga de rearmar las jerarquias identificadas de manera previa, si es que hay tareas en comun en las sub-jerarquias identificadas
     * 
     **/
    public void rearmarJerarquia (){
        String id1;
        String id2;
        ArrayList<String> delete = new ArrayList();
        for (int i = 0; i <this.pares.size() ; i++) {
            String [] par = this.pares.get(i).split(",");
            id1 = par[0];
            id2= par[1];
            delete.add(id2);
            
            for (int j = 0; j < this.subJerarquias.get(id2).size(); j++) {
                if(!this.subJerarquias.get(id1).contains(this.subJerarquias.get(id2).get(j))){
                    this.subJerarquias.get(id1).add(this.subJerarquias.get(id2).get(j));
                }
            }
        }
        for (int i = 0; i < delete.size(); i++) {
            this.subJerarquias.remove(delete.get(i));
        }     
    }
    /**
     * Imprime la información de las tareas que forman una sub-jearquia y despues muestra las tareas padre de cada jerarquia y una lista de tarea que las precede.
     **/
    public void imprimirJerarquias(){
        int cont =1;
        System.out.println("Patrón de jerarquías de tareas");
        System.out.println("En este patrón se detectaron "+subJerarquias.size()+" tareas que son responsables de armar sub-jerarquías.\nEstas son: ");
        for (Map.Entry<String, ArrayList<String>> entry : subJerarquias.entrySet()) {
            String key = entry.getKey();
            System.out.println("           "+cont+"- "+this.tareas_id.get(key).getPresentacionName());
            cont+=1;
        }
        System.out.println("\nA continuación se presentan las tareas responsables de armar jerarquías y la lista de tareas que forman parte de dicha sub-jerarquia");
        cont=1;
        for (Map.Entry<String, ArrayList<String>> entry : subJerarquias.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            System.out.println("   "+cont+"- Jerarquía de: "+this.tareas_id.get(key).getPresentacionName());
            
            for (int i = 0; i < value.size(); i++) {
                System.out.println("         "+this.tareas_id.get(value.get(i)).getPresentacionName());
            }
            System.out.print("\n");
            cont+=1;
        }
    
    }
    
    /** 
     *  Este metodo se encarga de armar las sub-jerarquias, para ello busca las tarea padre, identificadas previamente en el metodo
  buscarTareaQueGeneraElArtefacto y busca las tareas que preceden a dichas tareas padre. la manera de proceder en esta busqueda es tomar una tarea padre
     *  extraer cada uno de sus artefactos de salida y revisar cada una de las tareas del proceso y ver si contiene en sus artefactos de entrada, uno de los artefactos de salida. 
     *  este proceso se repite para cada tarea que se agrega a la jerarquia.
     **/
    public void armarJerarquia(){
        int cont =0;
        for (Map.Entry<String, ArrayList<String>> entry : subJerarquias.entrySet()) {
            
            String t_inicial = entry.getKey();
            ArrayList<String> subgerar = entry.getValue();
            
            subgerar.add(t_inicial);
            this.TareasCompartidas.get(t_inicial).add(t_inicial);
            
            for (int i = 0; i < subgerar.size(); i++) {
                String id = subgerar.get(i);
                Tarea actual= this.tareas_id.get(id);
                
                for (int j = 0; j < actual.getOutput().size(); j++) {
                    String idOutput = actual.getOutput().get(j);
                    
                    for (int k = 0; k < this.tareas.size(); k++) {
                        
                        ArrayList<String> aux= this.tareas.get(k).getMandatoryImput();
                        if(aux.contains(idOutput)){
                            
                            if(!subgerar.contains(this.tareas.get(k).getId())){
                                subgerar.add(this.tareas.get(k).getId());
                                this.TareasCompartidas.get(this.tareas.get(k).getId()).add(t_inicial);
                            }
                            
                        }
                    }
                }   
            }        
        }        
    }
    
   /** 
    *  Este metodo se encarga de asociar a los artefactos con todas las tareas que lo poseen como artefacto de salida
    **/
    public void buscarTareaQueGeneraElArtefacto(){
        for (int i = 0; i < this.artefactos.size(); i++) {
            this.esOutput.put(this.artefactos.get(i).getId(), new ArrayList());
        }

        for (int i = 0; i < this.tareas.size(); i++) {
            Tarea tmp = this.tareas.get(i);
            
            for (int j = 0; j < tmp.getOutput().size(); j++) {
                if(this.esOutput.containsKey(tmp.getOutput().get(j))){
                    this.esOutput.get(tmp.getOutput().get(j)).add(tmp.getId());
                }
            }
        }
    }
    
    /**
     * Este metodo inicializa componentes iniciales, los cuales son utilizados por otros metodos de la clase.
    **/
    public void inicializarComponentesGenerales(){
        // creo el hash que tendra las tareas vinculadas por su id1
        for (int i = 0; i < this.tareas.size(); i++) {
            this.tareas_id.put(tareas.get(i).getId(), tareas.get(i));
            this.TareasCompartidas.put(tareas.get(i).getId(), new ArrayList<String>());
        }
    
    }
    
     /**
     *  Metodo encargado de buscar las tareas que pueden ser una tarea padre de una subjerarquia.
     * existen 3 casos. El primero de ellos son aquellas tareas que no poseen un artefacto de entrada
     * el segundo caso son todas aquellas tareas que tienen un artefacto de entrada, pero dicho artefacto no es producido por ninguna tarea(no es el artefacto de salida de ninguna tarea)
     * el tercer y ultimo caso es aquel en que la tarea tiene un artefacto de entrada, el cual tambien es el artefacto de salida de dicha tarea y no es producido por ninguna otra tarea.
     **/
    public void buscaTareasIndependientes(){
        int cont=1;
        for (int i = 0; i < this.tareas.size(); i++) {
            Tarea tmp= this.tareas.get(i);
            String id = tmp.getId();
            
            if(tmp.getMandatoryImput().size()==0){
                this.subJerarquias.put(tmp.getId(), new ArrayList());
            }
            
            else{
                if(tmp.getMandatoryImput().size()==1){
                   String mI=  tmp.getMandatoryImput().get(0);
                   // significa que hay una tarea que lo genera, ahora hay que preguntar si es distinto a la tarea que estamos parados
                   if(this.esOutput.get(mI).size()==1){
                        if(this.esOutput.get(mI).contains(id)){
                            this.subJerarquias.put(tmp.getId(), new ArrayList());
                        }
                   }
                   else{
                       if(this.esOutput.get(mI).size()==0){ 
                            this.subJerarquias.put(tmp.getId(), new ArrayList());
                       }
                   }
                    
                }
            }
           
            
        }
    }

}
