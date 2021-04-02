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
public class Role {
    private String presentationName;
    private String name;
    private String id;
    private String variabilityBasedOnElement;
    private String variabilityType;
    private ArrayList<String> trabajaCon;
    //guidance almacenara los id de los 7 tipos de guidance que acepta un rol
    private boolean guidance;
    private boolean colabora;
    private boolean noRealizaTarea;
    private int numTareasQueRealiza;
    private int numTareasQueRealiza2;
    private double distanciaAlPromedio;
     private double distanciaAlPromedio2;
    private boolean sobreCargado;
    private boolean sobreCargado2;
    public Role(){
        this.id="";
        this.name="";
        this.presentationName="";
        this.variabilityBasedOnElement=" ";
        this.variabilityType=" ";
        this.guidance= false;
        this.trabajaCon = new ArrayList<String>();
        this.colabora=true;
        this.noRealizaTarea= false;
        this.numTareasQueRealiza=0;
        this.distanciaAlPromedio=0;
        this.sobreCargado= false;
        
    }
    
    /** 
     *  Este metodo se encarga de mostrar la información de un rol
     **/
    public void informacion(){
        System.out.println("Presentación Name: "+this.presentationName+
                "\nId: "+this.id +
                "\n variabilityTipe: "+this.variabilityType+
                "\n variabilitiBasedOnElement: "+this.variabilityBasedOnElement+
                "\n posee guidance: "+this.isGuidance());
    }
    //nuevo

    public boolean isSobreCargado() {
        return sobreCargado;
    }

    public void setSobreCargado(boolean sobreCargado) {
        this.sobreCargado = sobreCargado;
    }

    public double getDistanciaAlPromedio() {
        return distanciaAlPromedio;
    }

    public void setDistanciaAlPromedio(double distanciaAlPromedio) {
        this.distanciaAlPromedio = distanciaAlPromedio;
    }

    public int getNumTareasQueRealiza() {
        return numTareasQueRealiza;
    }

    public void setNumTareasQueRealiza(int numTareasQueRealiza) {
        this.numTareasQueRealiza = numTareasQueRealiza;
    }

    public boolean isNoRealizaTarea() {
        return noRealizaTarea;
    }

    public void setNoRealizaTarea(boolean noRealizaTarea) {
        this.noRealizaTarea = noRealizaTarea;
    }

 

    public boolean isColabora() {
        return colabora;
    }

    public void setColabora(boolean colabora) {
        this.colabora = colabora;
    }

    public ArrayList<String> getTrabajaCon() {
        return trabajaCon;
    }

    public void setTrabajaCon(ArrayList<String> trabajaCon) {
        this.trabajaCon = trabajaCon;
    }
    //fin nuevo- para colabora con o trabaja con....System.out.println(this.name.get(idRol));
    public boolean isGuidance() {
        return guidance;
    }

    public void setGuidance(boolean guidance) {
        this.guidance = guidance;
    }


    
    

    public String getVariabilityBasedOnElement() {
        return variabilityBasedOnElement;
    }

    public void setVariabilityBasedOnElement(String variabilityBasedOnElement) {
        this.variabilityBasedOnElement = variabilityBasedOnElement;
    }

    public String getVariabilityType() {
        return variabilityType;
    }

    public void setVariabilityType(String variabilityType) {
        this.variabilityType = variabilityType;
    }
    
    
    public String getPresentationName() {
        return presentationName;
    }

    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
