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
public class Artefacto {
    private String id;
    private String presentacionName;
    private String name;
    private String variabilityBasedOnElement;
    private String variabilityType; 

    private boolean guidance;
    private String tipo;
    private boolean subArtefacto;
    private boolean sinUso;
    
        //nuevo
    private double numVecesRequerido;
    private double distanciaConRespectoAlPromedio;
    private boolean esMuyRequerido;
    public Artefacto(){
        this.tipo= "";
        this.id="";
        this.name="";
        this.presentacionName="";
        this.variabilityBasedOnElement=" ";
        this.variabilityType=" ";
        this.guidance= false;
        this.subArtefacto=false;
        this.sinUso=false;
        //nuevo
        this.distanciaConRespectoAlPromedio=0;
        this.numVecesRequerido=0;
        this.esMuyRequerido=false;
        
        
    }
    /** 
     * este metodo se encarga de mostrar por consola la información de un artefacto en particular
    **/
    public void informacion(){
        System.out.println("Nombre de presentacion: "+this.presentacionName+" \nname: "+this.name+
                "\n posee sub-artefactos: "+this.subArtefacto+"\n posee guidance: "+this.isGuidance()); /**+
                "\nId: "+this.id +
                "\n variabilityTipe: "+this.variabilityType+
                "\n variabilitiBasedOnElement: "+this.variabilityBasedOnElement+
                "\n posee guidance: "+this.isGuidance()+
                "\n posee sub-artefactos: "+this.subArtefacto);
                **/
    }

    public boolean isEsMuyRequerido() {
        return esMuyRequerido;
    }

    public void setEsMuyRequerido(boolean esMuyRequerido) {
        this.esMuyRequerido = esMuyRequerido;
    }
    
    public double getNumVecesRequerido() {
        return numVecesRequerido;
    }

    public void setNumVecesRequerido(double numVecesRequerido) {
        this.numVecesRequerido = numVecesRequerido;
    }

    public double getDistanciaConRespectoAlPromedio() {
        return distanciaConRespectoAlPromedio;
    }

    public void setDistanciaConRespectoAlPromedio(double distanciaConRespectoAlPromedio) {
        this.distanciaConRespectoAlPromedio = distanciaConRespectoAlPromedio;
    }

    
    
    public boolean isSinUso() {
        return sinUso;
    }

    public void setSinUso(boolean sinUso) {
        this.sinUso = sinUso;
    }
    
    

    public boolean isSubArtefacto() {
        return subArtefacto;
    }

    public void setSubArtefacto(boolean subArtefacto) {
        this.subArtefacto = subArtefacto;
    }
  
    public boolean isGuidance() {
        return guidance;
    }

    public void setGuidance(boolean guidance) {
        this.guidance = guidance;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresentacionName() {
        return presentacionName;
    }

    public void setPresentacionName(String presentacionName) {
        this.presentacionName = presentacionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
