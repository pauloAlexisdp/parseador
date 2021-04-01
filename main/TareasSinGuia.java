/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author pparr
 */
public class TareasSinGuia {
    
    private ArrayList<Tarea> tareas;
    
    /**
     * El constructor de TareasSinGuia requiere de un arreglo de tareas para ser sometidas análisis
     * @param tareas corresponde a un ArrayList del tipo tarea, el cual contiene la información de las tareas recopiladas del archivo JSON
    **/
    public TareasSinGuia(ArrayList<Tarea> tareas){
        this.tareas= tareas;
    
    }
    
    /**
     * Este metodo se encaga buscar y mostrar por consola todas las tareas que no tienen asociada una guia en su especificación
     **/
    public void buscarTareasSinGuia(){
        int cont =1;
        for (Tarea tarea : tareas) {
            if(!tarea.isGuidance()){
                if(tarea.getPresentacionName().equals("")){
                    System.out.println("  "+cont+"- "+tarea.getName());
                }
                else{
                    System.out.println("  "+cont+"- "+tarea.getPresentacionName());
                }
                cont+=1;
            }
        }
    
    }
    
    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    
    
    
}
