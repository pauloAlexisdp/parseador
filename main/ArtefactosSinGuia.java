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
public class ArtefactosSinGuia {
    private ArrayList<Artefacto> artefactos;
    
    public ArtefactosSinGuia( ArrayList<Artefacto> artefactos){
        this.artefactos=artefactos;
    }
    
        public void buscarTArtefactosSinGuia(){
        int cont =1;
        for (Artefacto art : artefactos) {
            if(!art.isGuidance()){
                if(art.getPresentacionName().equals("")){
                    System.out.println("  "+cont+"- "+art.getName());
                }
                else{
                    System.out.println("  "+cont+"- "+art.getPresentacionName());
                }
                cont+=1;
            }
        }
    
    }
    
}
