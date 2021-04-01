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
public class RolesConDemasiadoTrabajo {
    
    private ArrayList<Tarea> tareas;
    private ArrayList<Role> roles;
    private HashMap <String, Integer> rolesMasNumDeTareasQueRealiza;
    private double promedioTareasPorRol;
    private double desviacionEstandar;
    public RolesConDemasiadoTrabajo(ArrayList<Tarea> tareas, ArrayList<Role> roles){
        this.rolesMasNumDeTareasQueRealiza= new HashMap <String, Integer>();
        this.roles= roles;
        this.tareas= tareas;
        this.promedioTareasPorRol=0;
        this.desviacionEstandar=0;
        iniciaComponente();
        contarTareasPorRol();
        calcularPromedioDeTareasQueRealizaCadaRol();
        calcularDistanciaAlPromedio();
        calcularDesviacionEstandar();
        determinarRolesConDemasiadoTrabajo();
        
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
    
    public void calcularPromedioDeTareasQueRealizaCadaRol(){
        
        for (Role rol : roles) {
            rol.setNumTareasQueRealiza(this.rolesMasNumDeTareasQueRealiza.get(rol.getId()));
            this.promedioTareasPorRol+=  rol.getNumTareasQueRealiza(); 
        }
        this.promedioTareasPorRol= this.promedioTareasPorRol/this.roles.size();
    }
    
    public void calcularDistanciaAlPromedio(){
        for (Role rol : roles) {
            double distancia = this.promedioTareasPorRol - rol.getNumTareasQueRealiza();
            if(distancia <0 ){
                distancia = distancia*-1;
            }
            rol.setDistanciaAlPromedio(distancia);
        }
    }
   
    public void calcularDesviacionEstandar(){
        for (Role rol : roles) {
            this.desviacionEstandar+= pow(rol.getDistanciaAlPromedio(),2);
        }
        this.desviacionEstandar= this.desviacionEstandar/this.roles.size();
        this.desviacionEstandar= Math.sqrt(this.desviacionEstandar);
    }
    
    public void determinarRolesConDemasiadoTrabajo(){
        for (Role rol : roles) {
            if( rol.getNumTareasQueRealiza() > this.desviacionEstandar){
                rol.setSobreCargado(true);
                System.out.println(rol.getName()+" o "+rol.getPresentationName());
            }
        }
        /**
        for (Role rol : roles) {
            System.out.println(rol.getName()+"--> "+ rol.getNumTareasQueRealiza());
         }
        System.out.println("desviacion "+this.desviacionEstandar);
        System.out.println("promedio "+this.promedioTareasPorRol);**/
    }
}
