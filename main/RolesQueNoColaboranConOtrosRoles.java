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
public class RolesQueNoColaboranConOtrosRoles {
    private ArrayList <Role> roles;
    
    public RolesQueNoColaboranConOtrosRoles(ArrayList<Role> roles){
        this.roles= roles;
        determinarQueRolesNoColaboran();
        mostrar();
    }
    
    public void determinarQueRolesNoColaboran(){
        for (Role role : roles) {
            if(! (role.getTrabajaCon().size()>0)){
                role.setColabora(false);
            }
        }
    }
    
    public void mostrar(){
        for (Role role : roles) {
            if (!role.isColabora()) {
                System.out.println(role.getName());
                
            }
        }
    }
}
