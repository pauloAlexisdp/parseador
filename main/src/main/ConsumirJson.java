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
import java.io.File;
import java.io.StringWriter;

import static java.lang.Math.pow;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class ConsumirJson {
    public int  PRETTY_PRINT_INDENT_FACTOR = 4;
    public String ARCHIVOXML;   
    public ArrayList<Tarea> tareas;
    public ArrayList<Role> roles ;
    public ArrayList<Artefacto> artefactos ;
    public double meanOutWP;
    public double sigmaOut;
    public static ArrayList<JSONArray> methodPackageArray;
    public ArrayList<JSONArray> contentElementArray;
    public JSONArray contentElement;
    public ArrayList<JSONObject> methodpackage;
    private JSONObject procesoJson;
    
    public ConsumirJson(JSONObject proceso){
        this.artefactos = new ArrayList();
        this.roles = new ArrayList();
        this.tareas = new ArrayList();
        this.meanOutWP=0.0;
        this.sigmaOut=0.0;
        this.methodPackageArray=  new ArrayList();
        this.methodpackage = new ArrayList();
        this.contentElementArray= new ArrayList();
        this.contentElement= new  JSONArray();
        
        this.procesoJson= proceso;
    }
 
  
    /** 
     * Este metodo imprime el contenido del archivo JSON que contiene la informacion del proceso
     **/
    public void imprimeJSON() throws JSONException{
        String jsonPrettyPrintString = this.procesoJson.toString(PRETTY_PRINT_INDENT_FACTOR);
        System.out.println(jsonPrettyPrintString);
    }
    
    /** 
     * Este metodo se encarga de recorrer los niveles estaticos del JSON, para poder llegar al primer MethodPackage del proceso, el cual puede contener
     * N sub MethodPackages en su interior.
     **/
    public void recorreJSON( ) throws JSONException{
            JSONObject json= this.procesoJson;
            json= json.getJSONObject("uma:MethodLibrary");
            json= json.getJSONObject("MethodPlugin");
            JSONArray methodPackage =json.getJSONArray("MethodPackage");
            // atributo de prueba que contendria todos los metohd packages existentes en el proceso.. solo para probar
           
            this.methodPackageArray.add(methodPackage);
            this.buscaMethodPackage(methodPackageArray);
         
    }
    
    /**
     *metodo encargado de encontrar todos los methodpackages, ya sea de tipo JSONArray o JSONObject presentes en el archivo JSON que contiene el proceso
     */
    public void buscaMethodPackage(ArrayList<JSONArray> methodPackages) throws JSONException{
        for (int i = 0; i < methodPackages.size(); i++) {
            
            JSONArray mp = methodPackages.get(i);
            
            for (int j = 0; j < mp.length(); j++) {
                if(mp.getJSONObject(j).has("MethodPackage") ){
                    if(mp.getJSONObject(j).get("MethodPackage").getClass().toString().equals(("class org.json.JSONArray"))){
                        methodPackages.add(mp.getJSONObject(j).getJSONArray("MethodPackage"));
                    }
                    else{
                        this.methodpackage.add(mp.getJSONObject(j).getJSONObject("MethodPackage"));
                        //mejora
                        JSONObject aux = mp.getJSONObject(j).getJSONObject("MethodPackage");
                        if(aux.has("MethodPackage")){
                            if(mp.getJSONObject(j).get("MethodPackage").getClass().toString().equals(("class org.json.JSONArray"))){
                                methodPackages.add(mp.getJSONObject(j).getJSONArray("MethodPackage"));
                            }                    
                        }
                    }
                }
            }
        }
        
       int total = methodPackages.size()+this.methodpackage.size();
        System.out.println("total methodPackages :" + total);
    }
    
    /** 
     * Este metodo se encarga de buscar los ContentElement en cada uno de los methodPackages identificados
     **/
    public void buscaContentElement() throws JSONException{
        int cont=0;
        for (int i = 0; i < this.methodPackageArray.size(); i++) {
            JSONArray mp= this.methodPackageArray.get(i);
            for (int j = 0; j < mp.length(); j++) {
                if(mp.getJSONObject(j).has("ContentElement")){
                    if(mp.getJSONObject(j).get("ContentElement").getClass().toString().equals("class org.json.JSONArray")){
                        this.contentElementArray.add(mp.getJSONObject(j).getJSONArray("ContentElement"));
                    }
                    else{
                        this.contentElement.put(mp.getJSONObject(j).getJSONObject("ContentElement"));
                    }
                }
            } 
        }
        
        for (int i = 0; i < this.methodpackage.size(); i++) {
            if(this.methodpackage.get(i).has("ContentElement")){
               
                    if(this.methodpackage.get(i).get("ContentElement").getClass().toString().equals("class org.json.JSONArray")){
                        this.contentElementArray.add(this.methodpackage.get(i).getJSONArray("ContentElement"));
                    }
                    else{
                        this.contentElement.put(this.methodpackage.get(i).getJSONObject("ContentElement"));
                    }
                
            }
        }    
    }
    
    /** 
     * Este metodo se encarga de mandar cada uno de los contentElement al metodo extraer contenido, para obtener la informacion correspondiente a los elementos principales del proceso
     * es decir roles, tareas y artefactos
     **/
    public void mandarContentElementAExtraerContenido () throws JSONException{
        this.extraerContendio(contentElement);
        
        for (int i = 0; i < this.contentElementArray.size(); i++) {
            this.extraerContendio(this.contentElementArray.get(i));
        }
    }
     
    /** 
     * Este metodo se encarga de extraer cada uno de los elementos del proceso (roles, tareas y artefactos)
     * con sus respectivos atributos
     **/
    public void extraerContendio(JSONArray contentElement) throws JSONException{

        String variabilityBasedOnElement=" ";
        String variabilityType=" ";

        //estamos dentro de ContentElement, aquí esta todo, incluido los breakdown con las fases. filtraremos las tareas solamente.
            for (int i = 0; i < contentElement.length(); i++) {
                //entidad sera el objeto json que posiblemente sea del tipo tarea.
                JSONObject entidad = contentElement.getJSONObject(i);
                if( entidad.get("xsi:type").equals("uma:Task")){
                     Tarea tarea = new Tarea();
                    //comenzando con la extracción de datos.
                    tarea.setPresentacionName(entidad.get("presentationName").toString());
                    tarea.setName(entidad.get("name").toString());
                    tarea.setId(entidad.getString("id").toString());

                    //extrayendo la variabilidad
                    tarea.setVariabilityType(entidad.getString("variabilityType").toString());
                    if(entidad.has("variabilityBasedOnElement")){
                        tarea.setVariabilityBasedOnElement(entidad.getString("variabilityBasedOnElement").toString());
                    }

                    //mandatory Input
                    if(entidad.has("MandatoryInput")){
                        String auxInput;
                        //preguntamos si mandatory input es del tipo array. porque se puede dar el caso.
                        if(entidad.get("MandatoryInput").getClass().toString().equalsIgnoreCase("class org.json.JSONArray")){
                            
                            JSONArray mInputA =entidad.getJSONArray("MandatoryInput");
                            for (int j = 0; j < mInputA.length(); j++) {
                                auxInput=mInputA.get(j).toString();
                                tarea.getMandatoryImput().add(auxInput);
                            }                            
                        }else{
                            auxInput=entidad.get("MandatoryInput").toString();
                            tarea.getMandatoryImput().add(auxInput);
                        }                       
                    }
                    if(entidad.has("OptionalInput")){
                      
                        String auxOptional;
                        //preguntamos si mandatory input es del tipo array. porque se puede dar el caso.
                        if(entidad.get("OptionalInput").getClass().toString().equalsIgnoreCase("class org.json.JSONArray")){
                            JSONArray opInputA =entidad.getJSONArray("OptionalInput"); 
                            for (int j = 0; j < opInputA.length(); j++) {
                                auxOptional=opInputA.get(j).toString();
                                tarea.getOptionalImput().add(auxOptional);
                            }                            
                        }else{

                            auxOptional=entidad.get("OptionalInput").toString();
                            tarea.getOptionalImput().add(auxOptional);
                        }                       
                    }                        
                    //consultaremos si tiene performed by
                    if(entidad.has("PerformedBy")){
                        String auxPerformed;
                        if(entidad.get("PerformedBy").getClass().toString().equals("class org.json.JSONArray")){
                             JSONArray pFormedBy= entidad.getJSONArray("PerformedBy");
                             for (int j = 0; j < pFormedBy.length(); j++) {
                                auxPerformed = pFormedBy.getString(j);
                                tarea.getPerformedBy().add(auxPerformed);
                            }
                        }else{
                            auxPerformed= entidad.getString("PerformedBy");
                            tarea.getPerformedBy().add(auxPerformed);
                        }    
                    }

                    if(entidad.has("AdditionallyPerformedBy")){
                        String auxAPB;
                        if(entidad.get("AdditionallyPerformedBy").getClass().toString().equals("class org.json.JSONArray")){
                            JSONArray aPB = entidad.getJSONArray("AdditionallyPerformedBy");
                            for (int j = 0; j < aPB.length(); j++) {
                                auxAPB = aPB.getString(j);
                                tarea.getAdditionallyPerformedBy().add(auxAPB);
                            }
                        }else{

                            auxAPB= entidad.getString("AdditionallyPerformedBy");
                            tarea.getAdditionallyPerformedBy().add(auxAPB);
                        }
                    }
                    if(entidad.has("Output")){
                        String auxOut;
                        if(entidad.get("Output").getClass().toString().equals("class org.json.JSONArray")){
                            JSONArray oUT = entidad.getJSONArray("Output");
                            for (int j = 0; j < oUT.length(); j++) {
                                auxOut = oUT.get(j).toString();
                                tarea.getOutput().add(auxOut);
                            }
                        }else{
                             auxOut = entidad.getString("Output");
                             tarea.getOutput().add(auxOut);
                        }
                    }
                    //comenzando a extraer los tipos de Guidance que se pueden aplicar a una tarea.
                    if(entidad.has("Concept")){
                        tarea.setGuidance(true); 
                    }

                    if(entidad.has("Whitepaper")){
                        tarea.setGuidance(true);
                    }                         

                    if(entidad.has("Guideline")){
                        tarea.setGuidance(true);
                    }      

                    if(entidad.has("ToolMentor")){
                        tarea.setGuidance(true);
                    }

                    if(entidad.has("EstimationConsiderations")){
                        tarea.setGuidance(true);
                    }  

                    if(entidad.has("ReusableAsset")){
                        tarea.setGuidance(true);
                    }                        

                    if(entidad.has("Example")){
                        tarea.setGuidance(true);
                    }  

                    if(entidad.has("Checklist")){
                        tarea.setGuidance(true);
                    }

                    if(entidad.has("SupportingMaterial")){
                        tarea.setGuidance(true);
                    }                        

                     tareas.add(tarea);
                }
                if( entidad.get("xsi:type").equals("uma:Role")){
                    Role rol = new Role();
                    rol.setId(entidad.getString("id"));
                    rol.setName(entidad.getString("name"));
                    rol.setPresentationName(entidad.getString("presentationName"));
                    //extrayendo la variabilidad
                    rol.setVariabilityType(entidad.getString("variabilityType").toString());

                    if(entidad.has("variabilityBasedOnElement")){
                        rol.setVariabilityBasedOnElement(entidad.getString("variabilityBasedOnElement").toString());
                    }
                    if(entidad.has("Checklist")){
                        rol.setGuidance(true);
                    }                        
                    if(entidad.has("Concept")){
                        rol.setGuidance(true);
                    }                        

                    if(entidad.has("Guideline")){
                        rol.setGuidance(true);
                    }                        

                    if(entidad.has("Whitepaper")){
                        rol.setGuidance(true);
                    }  

                    if(entidad.has("ReusableAsset")){
                        rol.setGuidance(true);
                    }

                    if(entidad.has("Example")){
                        rol.setGuidance(true);
                    } 

                    if(entidad.has("SupportingMaterial")){
                        rol.setGuidance(true);
                    }                         

                    roles.add(rol);

                }
                if(entidad.getString("xsi:type").equals("uma:Artifact") ||entidad.getString("xsi:type").equals("uma:Deliverable") || entidad.getString("xsi:type").equals("uma:Outcome")  ){
                    Artefacto artef= new Artefacto();
                    artef.setId(entidad.getString("id"));
                    artef.setName(entidad.getString("name"));
                    artef.setTipo(entidad.getString("xsi:type"));
                    artef.setPresentacionName(entidad.getString("presentationName"));
                    //extrayendo la variabilidad
                    artef.setVariabilityType(entidad.getString("variabilityType").toString());
                    if(entidad.has("variabilityBasedOnElement")){
                        artef.setVariabilityBasedOnElement(entidad.getString("variabilityBasedOnElement").toString());
                    }
                    if(entidad.has("ContainedArtifact")){
                        artef.setSubArtefacto(true);
                    }

                    if(entidad.has("Concept")){
                        artef.setGuidance(true);                        
                    }                         
                    if(entidad.has("EstimationConsiderations")){
                        artef.setGuidance(true);
                    }
                    if(entidad.has("Whitepaper")){
                        artef.setGuidance(true);
                    } 
                    if(entidad.has("Guideline")){
                        artef.setGuidance(true);
                    }
                    if(entidad.has("ReusableAsset")){
                        artef.setGuidance(true);
                    }                  
                    if(entidad.has("Example")){
                        artef.setGuidance(true);
                    }

                    if(entidad.has("Checklist")){
                        artef.setGuidance(true);
                    }
                    if(entidad.has("SupportingMaterial")){
                        artef.setGuidance(true);
                    }
                    if(entidad.has("ToolMentor")){
                        artef.setGuidance(true);
                    }
                    if(entidad.has("Report")){
                       artef.setGuidance(true);
                    }
                    if(entidad.has("Template")){
                        artef.setGuidance(true);
                    }                     

                    artefactos.add(artef);
                }

            }
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Artefacto> getArtefactos() {
        return artefactos;
    }

    public void setArtefactos(ArrayList<Artefacto> artefactos) {
        this.artefactos = artefactos;
    }
    
    /** 
     * Este metodo se encarga de mostrar la información estadistica de los datos recopilados, como
     * numero de taras, numero de artefactos y numero de roles del proceso
     **/
    public void imprimirInfo(){
      /**
        System.out.println("********** Tareas definidas **********");
           for (int i = 0; i < tareas.size(); i++) {
                tareas.get(i).información();
 
            }
           
       
            System.out.println("********** Roles definidos **********");
            for (int i = 0; i < roles.size(); i++) {
                roles.get(i).informacion();
                System.out.println(" ");
                System.out.println(" ");
            }
      
   
            System.out.println("********** Artefactos definidos **********");
            for (int i = 0; i < artefactos.size(); i++) {
                artefactos.get(i).informacion();
                System.out.println("");
            }      
         **/     
        System.out.println("tareas: "+tareas.size());
        System.out.println("artefactos: "+artefactos.size());
        System.out.println("roles: "+roles.size());
    }
    
    
    
    
    
}
