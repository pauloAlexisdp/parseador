/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.StringWriter;
import java.util.Scanner;

/**
 *
 * @author pparr
 */
public class Main {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static String ARCHIVOXML, PLANI;   
    
    public static void main(String[] args) {
        boolean cargado=false;
        File file = new File("C:/Users/pparr/OneDrive/Escritorio/Hoy/procesos EPF/prueba3.xml"); //ProcesoMobius4.xml rhiscom-proccess
        File file2 = new File("C:/Users/pparr/OneDrive/Escritorio/plani.xml");
        try {
            //cargar el archivo xml  y convertirlo a doc
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc =   dBuilder.parse(file);
            
            org.w3c.dom.Document plani =   dBuilder.parse(file);
            
            DOMSource plani1 = new DOMSource(plani);
            StringWriter writer1 = new StringWriter();
            StreamResult result1 = new StreamResult(writer1);
            TransformerFactory tf1 = TransformerFactory.newInstance();
            Transformer transformer1 = tf1.newTransformer();
            transformer1.transform(plani1, result1);
            
            
            PLANI=writer1.toString();   
            PLANI=PLANI.replaceAll("\n", "");
            PLANI= PLANI.replaceAll("\r", "");
            JSONObject planificacion = XML.toJSONObject(PLANI);
            
            
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            ARCHIVOXML=writer.toString();   
            
            ARCHIVOXML= ARCHIVOXML.replaceAll("\n", "");
            ARCHIVOXML= ARCHIVOXML.replaceAll("\r", "");
            
         
            JSONObject json = XML.toJSONObject(ARCHIVOXML);
       
            
            ConsumirJson consumirArchivo= new ConsumirJson(json);
            TareasEnJerarquias tmp;
            String op="1";

            
            System.out.println(" Bienvenido a la primera Versión Con Menu Del Parseador ");
            
            while(!op.equals("0")){
           
                System.out.println("[1] Para imprimir el archivo JSON del proceso ");
                System.out.println("[2] Para extraer y mostrar en pantalla la información del proceso ");
                System.out.println("[3] Para visualizar tareas detecta el patrón Fan Out ");
                System.out.println("[4] Para visualizar las tareas que no tienen guia");
                System.out.println("[5] Para visualizar el patron de jerarquías" );
                System.out.println("[6] Para visualizar el patron de artefactos sin uso");
                System.out.println("[7] Para visualizar el patron de artefactos sin guia");
                System.out.println("[8] Para imprimir el archivo JSON de la planificación");
                System.out.println("[0] Para salir\n");
                
                Scanner sc = new Scanner (System.in); //Creación de un objeto Scanner
                op= sc.nextLine();    
                
                 if (op.equals("14")) {
                     ArtefactosMuyRequeridos aux = new ArtefactosMuyRequeridos(consumirArchivo.getTareas(), consumirArchivo.getArtefactos());
                 }
                 if (op.equals("13")) {
                     ArtefactoRelacion aux = new ArtefactoRelacion(consumirArchivo.getTareas(), consumirArchivo.getArtefactos());
                 }
                
                if (op.equals("12")) {
                    RolesConDemasiadoTrabajo aux = new RolesConDemasiadoTrabajo(consumirArchivo.getTareas(), consumirArchivo.getRoles());
                }
                if (op.equals("11")) {
                    RolNoRealizaTareas rolnotareas= new RolNoRealizaTareas(consumirArchivo.getTareas(), consumirArchivo.getRoles());
                }
                if (op.equals("10")) {
                    RolesQueNoColaboranConOtrosRoles aux = new RolesQueNoColaboranConOtrosRoles( consumirArchivo.getRoles());
                }
                if (op.equals("9")) {
                    ArtefactosRequeridosPorMuchasTareas arpm= new ArtefactosRequeridosPorMuchasTareas(consumirArchivo.getTareas(), consumirArchivo.getArtefactos());
                    arpm.buscarNumeroDeVecesQueEsRequeridoCadaArtefacto();
                    arpm.calcularNumArtefactosRequeridosPromedio();
                    arpm.calcularDistanciaDeCadaTareaConRespectoAlPromedio();
                    arpm.calcularDesviacionEstandarDeArtefactosRequeridos();
                    arpm.buscarArtefactosMuyRequeridos();
                    arpm.MostrarArtefactosMuyRequeridos();
               
                    System.out.println(" \n");
                }
                
                if (op.equals("1")) {
                    consumirArchivo.imprimeJSON();
                    System.out.println(" \n");
                }
                if(op.equals("2")){
                    if(cargado==false){
                        consumirArchivo.recorreJSON();
                        consumirArchivo.buscaContentElement();
                        consumirArchivo.mandarContentElementAExtraerContenido();
                        consumirArchivo.imprimirInfo();
                        System.out.println(" \n");
                        cargado=true;
                        RolTrabajaCon a = new RolTrabajaCon(consumirArchivo.getTareas(), consumirArchivo.getRoles());
                    }
                    else{
                        consumirArchivo.imprimirInfo();
                    }
                    
                }
                
                if(op.equals("3")){
                    if(consumirArchivo.contentElementArray.size()>0 || consumirArchivo.contentElement.length()>0){
                        TareasFanOut tfo = new TareasFanOut(consumirArchivo.getTareas());
                     
                        tfo.calculaPromedioDeArtefactosDeSalida();
                        tfo.calcularDistanciaDeCadaTareaConRespectoAlPromedio();
                        tfo.calculaDesviacionEstandarDeArtefactosDeSalida();
                        tfo.buscarTareaMultiproposito();
                        tfo.mostrarTareasMutiproposito();
                        System.out.println("\n");                         
                    }
                    else{
                        System.out.println(" para visualizar esta opción primero debe de etraer el contenido de un proceso");
                         System.out.println(" \n");
                    }                
                }
                if(op.equals("4")){
                    TareasSinGuia tsg= new TareasSinGuia (consumirArchivo.getTareas());
                    tsg.buscarTareasSinGuia();
                    System.out.println("\n");
                }
                if(op.equals("5")){
                    if(consumirArchivo.contentElementArray.size()>0 || consumirArchivo.contentElement.length()>0){
                        tmp= new TareasEnJerarquias(consumirArchivo.getTareas(),consumirArchivo.getArtefactos());
                       // 
                        tmp.buscarTareaQueGeneraElArtefacto();
                        tmp.buscaTareasIndependientes();
                        tmp.inicializarComponentesGenerales();
                        tmp.armarJerarquia();
                        tmp.repetidas();
                        tmp.rearmarJerarquia();
                        tmp.imprimirJerarquias();
                        
                    }
                    else{
                        System.out.println(" para visualizar esta opción primero debe de etraer el contenido de un proceso");
                    }
                }
                if(op.equals("6")){
                    ArtefactosSinUso artSU= new  ArtefactosSinUso(consumirArchivo.tareas, consumirArchivo.getArtefactos());
                    artSU.ejecutar();
                    System.out.println("");
                }
                
                if(op.equals("7")){
                    ArtefactosSinGuia artSG = new  ArtefactosSinGuia(consumirArchivo.getArtefactos());
                    artSG.buscarTArtefactosSinGuia();
                    System.out.println("");
                }
                
                if(op.equals("8")){
                    String jsonPrettyPrintString = planificacion.toString(PRETTY_PRINT_INDENT_FACTOR);
                    System.out.println(jsonPrettyPrintString);
                }
            }
          } catch(Exception e) {
            e.printStackTrace();
          }
    }
}
