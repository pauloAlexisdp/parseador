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

public class RolTrabajaCon {
    // tendra el id del rol y el array de id de los roles con los cuales trabaja
    private HashMap <String, ArrayList<String>> rolColaboraConRol;
    private HashMap <String, String> name;
    private ArrayList<Tarea> tareas;
    private ArrayList<Role> roles;
    
    
    
    public RolTrabajaCon( ArrayList<Tarea> tareas , ArrayList<Role> roles ){
        this.rolColaboraConRol= new HashMap <String, ArrayList<String>>();
        this.name = new HashMap <String, String>();
        this.roles= roles;
        this.tareas= tareas;
        
        //step1
        iniciarComponentes();
        //step2
        crearBolsaDeRoles_MandaARolConRol();
        transpasarInformacionDeTrabajoACadaRol();
        comprobar();
    }
    
    public void iniciarComponentes(){
        for (Role rol : roles) {
            this.rolColaboraConRol.put(rol.getId(), new ArrayList<String>() );
            this.name.put(rol.getId(), rol.getName());
        }      

    }
    
    public void crearBolsaDeRoles_MandaARolConRol(){
       /**  con la sentencia siguiente se puede clonar un arraylist.
        b=(ArrayList<Integer>)a.clone();**/
 
        for (Tarea tarea : tareas) {
            ArrayList<String> bolsaRoles= new ArrayList<String>();
            //tomo la lista performedBy de roles y la vuelco en bolsa de roles.
       
            bolsaRoles= (ArrayList<String>) tarea.getPerformedBy().clone();

            //extraigo los roles faltantes de additionallyPerformed by si es que existe
            for (int i = 0; i < tarea.getAdditionallyPerformedBy().size(); i++) {
               if( !bolsaRoles.contains( tarea.getAdditionallyPerformedBy().get(i) ) ){
                   bolsaRoles.add( tarea.getAdditionallyPerformedBy().get(i) );
               }
            }
            
            //una vez creada la bolsa de roles se debe empezar con el match de colaboración
            rolTrabajaConRol(bolsaRoles);
            
        }
    }
    
    public void rolTrabajaConRol(ArrayList<String> bolsaRoles){
        for (int i = 0; i < bolsaRoles.size(); i++) {
            
            String idRol= bolsaRoles.get(i);
            
            ArrayList<String> bolsaRolAux = new ArrayList();
            bolsaRolAux = (ArrayList<String>)bolsaRoles.clone();
            bolsaRolAux.remove(idRol);
            for (int j = 0; j < bolsaRolAux.size(); j++) {
                if(!this.rolColaboraConRol.get(idRol).contains(bolsaRolAux.get(j))){
                    this.rolColaboraConRol.get(idRol).add(bolsaRolAux.get(j));    
                }            
            }
           

        }
    }
    
    public void transpasarInformacionDeTrabajoACadaRol(){
        for (Role rol : roles) {
            rol.setTrabajaCon( rolColaboraConRol.get( rol.getId() ) ); 
        }
    }
    
    public void comprobar(){
        for (Role rol : roles) {
            System.out.println(rol.getName()+" trabaja con: "+rol.getTrabajaCon().size() );
            for (int i = 0; i < rol.getTrabajaCon().size() ; i++) {
               
                System.out.println("---"+this.name.get(rol.getTrabajaCon().get(i)));
            }
            System.out.println("");
            
        }
    }
    
    
}
