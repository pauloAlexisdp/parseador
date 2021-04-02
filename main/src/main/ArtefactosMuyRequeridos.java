/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author pparr
 */
public class ArtefactosMuyRequeridos {
    
    private ArrayList<Artefacto> artefacto;
    private ArrayList<Tarea> tareas;
    private HashMap<String, Integer> ContadorDeVeces;
    private double promedio;
    private double desviacionEstandar;
    public ArtefactosMuyRequeridos( ArrayList<Tarea> tareas, ArrayList<Artefacto> artefacto){ 
        this.artefacto= artefacto;
        this.tareas= tareas;
        this.ContadorDeVeces= new HashMap<String, Integer>();
        this.promedio=0;
        this.desviacionEstandar=0;
        
        iniciarComponentes();
        contarNumVecesRequeridio();
        transpasarInformacion();
        calcularPromedio();
        calcularDistanciaAlPromedio();
        calcularDesviacion();
        mostrar();
    }
    
    public void iniciarComponentes(){
        for (Artefacto art : artefacto) {
            this.ContadorDeVeces.put(art.getId(), 0);
        }
    }
    
    public void contarNumVecesRequeridio(){
        
        for (Tarea tarea : tareas) {
            
            for (int i = 0; i < tarea.getMandatoryImput().size(); i++) {
                String idartf= tarea.getMandatoryImput().get(i);
                int contAux = this.ContadorDeVeces.get(idartf);
                contAux+=1;
                this.ContadorDeVeces.put(idartf, contAux);
            }

            
             
        }
    }
    
   public void transpasarInformacion(){
       for (Artefacto artf : artefacto) {
           String idArtefacto= artf.getId();
           int numVecesRequerido= this.ContadorDeVeces.get(idArtefacto);
           artf.setNumVecesRequerido2(numVecesRequerido);
       }
   }
   
   public void calcularPromedio(){
       for (Artefacto artf : artefacto) {
          this.promedio+= artf.getNumVecesRequerido2();
       }
       this.promedio = this.promedio/this.artefacto.size();
   }
   
   public void calcularDistanciaAlPromedio(){
       for (Artefacto artf : artefacto) {
           
           double distancia= this.promedio-artf.getNumVecesRequerido2();
           if(distancia <0){
               distancia = distancia*-1;
           }
           
           artf.setDistanciaConRespectoAlPromedio2(distancia);
       }
   }
   
   public void calcularDesviacion(){
       for (Artefacto artf : artefacto) {
           this.desviacionEstandar+= pow(artf.getDistanciaConRespectoAlPromedio2(), 2);
       }
       this.desviacionEstandar= this.desviacionEstandar/this.artefacto.size();
       this.desviacionEstandar = Math.sqrt(this.desviacionEstandar);
   }
   
   public void mostrar(){
       System.out.println("desviación: "+this.desviacionEstandar);
       System.out.println("promedio: "+this.promedio);
       for (Artefacto artf : artefacto) {
           if(artf.getNumVecesRequerido2() > this.desviacionEstandar){
               System.out.println(artf.getName()+" "+artf.getNumVecesRequerido2());
           }
       }
   }
}

