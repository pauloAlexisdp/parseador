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
public class Tarea {
    
    private ArrayList<String> output;
    private ArrayList<String> performedBy;
    private ArrayList<String> additionallyPerformedBy; 
    private ArrayList<String> mandatoryImput;
    private ArrayList<String> optionalImput;
    private String presentacionName;
    private String name;
    private String id;
    private String variabilityBasedOnElement;
    private String variabilityType;    
    // guidance, se almacenara uno general por mientras
    private boolean guidance;
    //parametros para implementar el patron de tareas multiproposito
    private boolean esMultiproposito;
    private double distanciaConRespectoAlPromedio;
    
    public Tarea(){
        this.distanciaConRespectoAlPromedio=0.0;
        this.esMultiproposito=false;
        
        this.guidance= false;
        this.optionalImput= new ArrayList();
        this.additionallyPerformedBy= new ArrayList();
        this.id= "";
        this.mandatoryImput=new ArrayList();
        this.name="";
        this.output=new ArrayList();
        this.performedBy=new ArrayList();
        this.presentacionName= "";
        this.variabilityType=" ";
        this.variabilityBasedOnElement=" ";
    }
//nuevo
    public boolean isEsMultiproposito() {
        return esMultiproposito;
    }

    public void setEsMultiproposito(boolean esMultiproposito) {
        this.esMultiproposito = esMultiproposito;
    }

    public Double getDistanciaConRespectoAlPromedio() {
        return distanciaConRespectoAlPromedio;
    }
    
    public void setDistanciaConRespectoAlPromedio(Double distancia) {
        this.distanciaConRespectoAlPromedio = distancia;
    }
    


   //nuevo fin

    public ArrayList<String> getOptionalImput() {
        return optionalImput;
    }

    public void setOptionalImput(ArrayList<String> optionalImput) {
        this.optionalImput = optionalImput;
    }
    
    
    
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
    
    
    /** 
     * Este metodo se encarga de mostrar la información de una tarea.
     **/
    public void información(){
        /**
        System.out.println("Tarea: "+this.presentacionName+
                "\nId: "+this.id+
                "\nCantidad de Inputs: "+this.mandatoryImput.size()+
                "\nNumero de Roles involucrados"+(this.additionallyPerformedBy.size()+this.performedBy.size())+
                "\nId de Roles principales: ");
                for (int i = 0; i < this.performedBy.size(); i++) {
                       System.out.println("        "+this.performedBy.get(i));
                }
                if (this.additionallyPerformedBy.size()>0) {
                    System.out.println("Id de Roles Secundarios: ");
                    for (int i = 0; i < this.additionallyPerformedBy.size(); i++) {
                        System.out.println("        "+this.additionallyPerformedBy.get(i));
                    }        
                }
                if(this.output.size()>0){
                    System.out.println("Cantidad de Outpus: "+this.output.size());
                    System.out.println("Output"+this.output.get(0));
                }
                **/
        System.out.print("\nTarea: "+this.presentacionName +" Gui: "+guidance);//+
               // "\n varibilityTipe: "+ this.variabilityType +
                //"\n variabilityBasedOnElement "+ this.variabilityBasedOnElement+
                //"\n posee guidance<: "+this.isGuidance());
      /**  if(this.optionalImput.size()> 0){
            System.out.print("\nEsta Tarea  posee optional input ");
        
        }        
         **/       
    }
    
    
    
    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }

    public ArrayList<String> getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(ArrayList<String> performedBy) {
        this.performedBy = performedBy;
    }

    public ArrayList<String> getAdditionallyPerformedBy() {
        return additionallyPerformedBy;
    }

    public void setAdditionallyPerformedBy(ArrayList<String> additionallyPerformedBy) {
        this.additionallyPerformedBy = additionallyPerformedBy;
    }

    public ArrayList<String> getMandatoryImput() {
        return mandatoryImput;
    }

    public void setMandatoryImput(ArrayList<String> mandatoryImput) {
        this.mandatoryImput = mandatoryImput;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    
}
