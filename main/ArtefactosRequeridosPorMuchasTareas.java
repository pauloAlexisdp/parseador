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
public class ArtefactosRequeridosPorMuchasTareas {
    
    private ArrayList<Tarea> tareas;
    private ArrayList<Artefacto> artefactos;
    private double promUsoArtefactos;
    private double desviacionEstandarArtefactosRequeridos;
    
    public ArtefactosRequeridosPorMuchasTareas(ArrayList<Tarea> tareas, ArrayList<Artefacto> artefactos){
        this.artefactos= artefactos;
        this.tareas=tareas;
        this.desviacionEstandarArtefactosRequeridos=0;
        this.promUsoArtefactos=0;
    }
    
    
    public void buscarNumeroDeVecesQueEsRequeridoCadaArtefacto(){
       
         
        for (Artefacto artf : artefactos) {
            double numRequerido=0;
            String idArtf = artf.getId();
            
            for (Tarea tarea : tareas) {
                if (tarea.getMandatoryImput().contains(idArtf)) {
                   numRequerido+=1;
                }
                if (tarea.getOptionalImput().contains(idArtf)) {
                   numRequerido+=1;
                }
                
            }        
            artf.setNumVecesRequerido(numRequerido);
            System.out.println(artf.getName()+" o "+artf.getPresentacionName()+"--> "+numRequerido);
        }  
        
    }
    
    
    //si da incongruencias, cambiar aqui artefactos.size por tareas.size
    public void calcularNumArtefactosRequeridosPromedio(){
        for (Artefacto artf : artefactos){
           this.promUsoArtefactos+=artf.getNumVecesRequerido();
          
        }
        this.promUsoArtefactos= this.promUsoArtefactos/artefactos.size();
       
      
       
    }
    
    public void calcularDistanciaDeCadaTareaConRespectoAlPromedio(){
        for (Artefacto artf : artefactos) {
            double distancia =0;
            distancia = artf.getNumVecesRequerido()-this.promUsoArtefactos;
            if( distancia <0 ){
                distancia = distancia*-1;
            }
            
            artf.setDistanciaConRespectoAlPromedio(distancia);
        }
    }
    
    public void calcularDesviacionEstandarDeArtefactosRequeridos(){
        for (Artefacto artf : artefactos) {
            this.desviacionEstandarArtefactosRequeridos+= pow(artf.getDistanciaConRespectoAlPromedio(),2);
            
        }
        
        this.desviacionEstandarArtefactosRequeridos= this.desviacionEstandarArtefactosRequeridos/this.artefactos.size();
        this.desviacionEstandarArtefactosRequeridos= Math.sqrt(this.desviacionEstandarArtefactosRequeridos);  
        System.out.println("desviación "+this.desviacionEstandarArtefactosRequeridos);
    }
    
    public void buscarArtefactosMuyRequeridos(){
        for (Artefacto artf : artefactos) {
            if( artf.getDistanciaConRespectoAlPromedio() > this.desviacionEstandarArtefactosRequeridos && artf.getNumVecesRequerido()>0){
                artf.setEsMuyRequerido(true);
            }
        }
    }
    
    public void MostrarArtefactosMuyRequeridos(){
         for (Artefacto artf : artefactos) {
             if(artf.isEsMuyRequerido() ){
                 System.out.println(artf.getName());
             }
             //System.out.println(artf.getName()+" "+artf.getNumVecesRequerido());
         }
    }
    
}
