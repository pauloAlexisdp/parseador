/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pparr
 */
public class ArtefactoRelacion {
    
    private HashMap <String, ArrayList<String>> artfRelacion;
    private HashMap <String, String> nombres;
    private ArrayList<Tarea> tareas;
    private ArrayList<Artefacto> artefactos;

    
    public ArtefactoRelacion( ArrayList<Tarea> tareas , ArrayList<Artefacto> artefacto ){
        this.artefactos=artefacto;
        this.tareas=tareas;
        this.artfRelacion = new HashMap <String, ArrayList<String>>();
        this.nombres = new  HashMap <String, String> ();
        iniciarComponentes();
        armarRelacionArtefacto();
        comprobar();
    }
    
    public void iniciarComponentes(){
        for (Artefacto artf : artefactos) {
            String idArtefacto = artf.getId();
            String nombre ="";
            if(!artf.getName().equals("")){
                nombre = artf.getName();
            }
            else{
                nombre = artf.getPresentacionName();
            }
            
            this.artfRelacion.put(idArtefacto, new ArrayList<String>() );
            this.nombres.put(idArtefacto, nombre);
        }
    }
    
    public void armarRelacionArtefacto(){
        
        for (Tarea tarea : tareas) {
            ArrayList<String> input = tarea.getMandatoryImput();
            ArrayList<String> optionalInput= tarea.getOptionalImput();
            ArrayList<String> output= tarea.getOutput();
           
            for (int i = 0; i < input.size(); i++) {
                String idInput = input.get(i);
                
                for (int j = 0; j < output.size(); j++) {
                    if(!idInput.equals(output.get(j)) && !this.artfRelacion.get(idInput).contains(output.get(j)))
                    this.artfRelacion.get(idInput).add(output.get(j));
                }
                
            }
            for (int i = 0; i < optionalInput.size(); i++) {
                String idInput = optionalInput.get(i);
                
                for (int j = 0; j < output.size(); j++) {
                    if(!idInput.equals(output.get(j)) && !this.artfRelacion.get(idInput).contains(output.get(j))){
                        this.artfRelacion.get(idInput).add(output.get(j));
                    }
                }
                
            }  
        }
    }
    
    public void comprobar(){
        int cont=0;
        for (Map.Entry<String, ArrayList<String>> entry : artfRelacion.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            
            System.out.println(this.nombres.get(key)+" genera a: ");
            for (int i = 0; i <value.size(); i++) {
                 System.out.println("--"+this.nombres.get(value.get(i)));
            }
            System.out.println("\n");
            
            if(value.size()==0){
                cont +=1;
            }
        }
        System.out.println(this.artfRelacion.size());
        System.out.println("total de artf que no generan nada: "+cont);
    }
    
}
