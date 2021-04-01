/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static java.lang.Math.pow;
import java.util.ArrayList;

/**
 *
 * @author pparr
 */
public class TareasFanOut {
    private ArrayList<Tarea> tareas;
    private double promedioDeArtefactosDeSalida;
    private double desviacionEstandarDeArtefactosDeSalida;
    
    /**
     * Constructor de TareasFanOut
     * @param tareas corresponde al ArrayList que contiene a las tareas que son parte del proceso analizado
     **/
    public TareasFanOut(ArrayList<Tarea> tareas){
        this.tareas= tareas;
        this.promedioDeArtefactosDeSalida=0;
        this.desviacionEstandarDeArtefactosDeSalida=0;
    }
  
    /**
     * Este metodo se encarga de calcular el numero promedio de artefactos de salida por cada tarea
     **/
    public void calculaPromedioDeArtefactosDeSalida(){
        
        for (Tarea tarea : tareas) {
            this.promedioDeArtefactosDeSalida+=tarea.getOutput().size();
        }
        this.promedioDeArtefactosDeSalida= this.promedioDeArtefactosDeSalida/this.tareas.size();
        
    }
    /**
     * Este metodo toma el numero de artefactos de cada tarea y calcula la distancia de este numero con 
     * respecto al numero de artefactos promedio, el resultado es guardado dentro de cada tarea, para su posterior uso.
     **/
    public void calcularDistanciaDeCadaTareaConRespectoAlPromedio(){
      
        for (Tarea tarea : tareas) {
            double distancia=0;
            distancia= tarea.getOutput().size()-this.promedioDeArtefactosDeSalida;
            if(distancia<0){
                distancia = distancia*-1;
            }
            
            tarea.setDistanciaConRespectoAlPromedio(distancia);
        }
    }
    /**
     * Este metodo calcula la desviación estandar de los artefactos de salida
     * para ello utiliza  la distancia de cada tarea con respecto al promedio de artefactos de salida de las tareas
     * 
     **/
    public void calculaDesviacionEstandarDeArtefactosDeSalida(){
        //saco el distomeanout de cadaa tarea y lo elevo al cudrado y lo sumo
        for (int i = 0; i < this.tareas.size(); i++) {
            this.desviacionEstandarDeArtefactosDeSalida+= pow( this.tareas.get(i).getDistanciaConRespectoAlPromedio() , 2 );
        }
        //el resultado anterior lo divido por la cantidad de tareas.
        this.desviacionEstandarDeArtefactosDeSalida=this.desviacionEstandarDeArtefactosDeSalida/this.tareas.size();
        //le saco la raiz al resultado anterior
        this.desviacionEstandarDeArtefactosDeSalida=  Math.sqrt(this.desviacionEstandarDeArtefactosDeSalida);   
    }
    
    /**
     * Este metodo se encarga de identificar que tareas son fan out.
     * Las tareas fan out son aquellas que tienen mas de una meta (realizan mas de una cosa) o 
     * aquellas tareas que no producen artefactos de salida
     **/
    public void buscarTareaMultiproposito(){
        for (int i = 0; i < this.tareas.size(); i++) {
            if(this.tareas.get(i).getDistanciaConRespectoAlPromedio() > this.desviacionEstandarDeArtefactosDeSalida){
                this.tareas.get(i).setEsMultiproposito(true);
            }
        }
    }
    
    /**
     * Este metodo muestra la informacion como:  el promedio de artefactos de salida, la desviacion estandar de los artefactos de salida y el nombre de las tareas identificadas como fan out con su respectivo numero de artefactos y su distancia al promedio.
     * 
     **/
    public void mostrarTareasMutiproposito(){
        System.out.println(" ");
        System.out.println("Promedio de artefactos de salida:"+this.promedioDeArtefactosDeSalida);
        System.out.println("Desviacion estandar: "+this.desviacionEstandarDeArtefactosDeSalida);
        for (int i = 0; i < this.tareas.size(); i++) {
           if(this.tareas.get(i).isEsMultiproposito()){
               System.out.println("          "+this.tareas.get(i).getName());
               System.out.println("          Distancia al numero de tareas promedio: "+this.tareas.get(i).getDistanciaConRespectoAlPromedio());
               System.out.println("          Numero de Artefactos: "+this.tareas.get(i).getOutput().size());
               System.out.print("\n");
           }
        }
    
    }
    
}
