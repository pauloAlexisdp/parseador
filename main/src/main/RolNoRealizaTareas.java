/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author pparr
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RolNoRealizaTareas {
    
    private HashMap <String, Integer> rolesMasNumDeTareasQueRealiza;
    private ArrayList<Tarea> tareas;
    private ArrayList<Role> roles; 
    
    public RolNoRealizaTareas(ArrayList<Tarea> tareas, ArrayList<Role> roles){
        this.roles= roles;
        this.tareas= tareas;
        this.rolesMasNumDeTareasQueRealiza = new HashMap <String, Integer>();
        // llmar funciones
        iniciaComponente();
        contarTareasPorRol();
        determinarQueRolNoHaceTareas();
    }
    
    public void iniciaComponente(){
        for (Role rol : roles) {
            this.rolesMasNumDeTareasQueRealiza.put(rol.getId(), 0);
        }
    }
    
    public void contarTareasPorRol(){
        for (Tarea tarea : tareas) {
            String idRol="";
            for (int i = 0; i < tarea.getPerformedBy().size(); i++) {
                idRol= tarea.getPerformedBy().get(i);
                int aux =this.rolesMasNumDeTareasQueRealiza.get(idRol)+1;
                this.rolesMasNumDeTareasQueRealiza.put(idRol, aux);
            }
            
            for (int i = 0; i < tarea.getAdditionallyPerformedBy().size(); i++) {
                idRol= tarea.getAdditionallyPerformedBy().get(i);
                int aux =this.rolesMasNumDeTareasQueRealiza.get(idRol)+1;
                this.rolesMasNumDeTareasQueRealiza.put(idRol, aux);
            }
  
        }   
    }
    
    public void determinarQueRolNoHaceTareas(){
       for (Role rol : roles) {
           int cantidad= this.rolesMasNumDeTareasQueRealiza.get(rol.getId());
           if(cantidad==0){
               rol.setNoRealizaTarea(true);
               System.out.println(rol.getName()+" o "+rol.getPresentationName());
           }
       }
    }
}
