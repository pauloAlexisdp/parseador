package main;


import java.util.ArrayList;
import java.util.HashMap;
import main.Artefacto;
import main.Tarea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pparr
 */
public class ArtefactosSinUso {
    private ArrayList<Tarea> tareas;
    private ArrayList<Artefacto> artefactos;
    private HashMap <String, Integer> cantidadDeUsoDeCadaArtefacto;
    
    public ArtefactosSinUso(ArrayList<Tarea> tareas, ArrayList<Artefacto> artefactos){
        this.artefactos=artefactos;
        this.tareas=tareas;
        this.cantidadDeUsoDeCadaArtefacto= new  HashMap <String, Integer>();
        
    }
    
    public void ejecutar (){
        this.iniciarComponentesIniciales();
        this.calcularElUsoDeCadaArtefacto();
        this.determinarArtefactosSinUso();
        this.mostrarArtefactosSinUso();
    }
    
    public void iniciarComponentesIniciales(){
        for (Artefacto art : artefactos) {
            this.cantidadDeUsoDeCadaArtefacto.put(art.getId(), 0);
        }    
    }
    
    // se buscara por todas las tareas la cantidad de artefactos, y se sumara uno al hash
    public void calcularElUsoDeCadaArtefacto(){
        for (Tarea tarea : tareas) {            
            for (int i = 0; i < tarea.getMandatoryImput().size(); i++) {
                String idArtefacto = tarea.getMandatoryImput().get(i);
                if(this.cantidadDeUsoDeCadaArtefacto.containsKey(idArtefacto)){
                    int cantidad =this.cantidadDeUsoDeCadaArtefacto.get(idArtefacto);
                    this.cantidadDeUsoDeCadaArtefacto.put(idArtefacto, cantidad+1);
                }

            }
            
            for (int i = 0; i < tarea.getOptionalImput().size(); i++) {
                String idArtefacto = tarea.getOptionalImput().get(i);
                if(this.cantidadDeUsoDeCadaArtefacto.containsKey(idArtefacto)){
                    int cantidad =this.cantidadDeUsoDeCadaArtefacto.get(idArtefacto);
                    this.cantidadDeUsoDeCadaArtefacto.put(idArtefacto, cantidad+1);
                }
                
            }
        }
    }
    
    public void determinarArtefactosSinUso(){
        for (Artefacto art : artefactos) {
            String id = art.getId();
            if( this.cantidadDeUsoDeCadaArtefacto.get(id) == 0 ){
                art.setSinUso(true);
            }
        }      
    }
    
    public void mostrarArtefactosSinUso(){
        int cont =1;
        for (Artefacto art : artefactos) {
            if(art.isSinUso()){
                System.out.println(cont+"- "+art.getPresentacionName());
                cont+=1;
            }
        }  
    }
    
}
