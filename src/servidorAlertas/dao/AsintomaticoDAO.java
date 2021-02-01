/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import servidorAlertas.dto.ClsAsintomaticoDTO;

/**
 *
 * @author javier
 */
public class AsintomaticoDAO {
    
    private String rutaArchivo="";
    
    public AsintomaticoDAO(String rutaArchivo){
        this.rutaArchivo = rutaArchivo;
    }    
    public ClsAsintomaticoDTO existeAsintomatico(String tipo_id, int id){
        File archivo=null; 
        FileReader fr = null;
        BufferedReader br = null;
        ClsAsintomaticoDTO objAsin=null;
        boolean estaRegistrado=false;
        
        try {
                /*COMPROBACION*/
                archivo = new File(rutaArchivo);
                if (archivo.createNewFile()) {
                        System.out.println("       File created: " + archivo.getName());
                } else {
                       // System.out.println("File already exists.");
                }
                
                /*LECTURA*/
                try {
                        fr = new FileReader (archivo);
                        br = new BufferedReader(fr);

                        String linea;

                        while((linea=br.readLine())!=null){
                                String[] parts = linea.split(" ");
                                if(parts[0].equalsIgnoreCase(tipo_id) &&  Integer.parseInt(parts[1]) == id ){
                                        String direccion="";
                                        for(int i=0;  i<parts.length;i++){
                                            if(i>=6){
                                                direccion += parts[i]+" ";                                               
                                            }
                                        }
                                        objAsin = new ClsAsintomaticoDTO(parts[2]+" "+parts[3],parts[4]+" "+parts[5],parts[0], Integer.parseInt(parts[1]), direccion);
                                        break;
                                }
                        }    
                }
                catch (IOException | NumberFormatException e) {
                }finally{/*Cerrando archivo*/
                        try{                    
                           if( null != fr ){fr.close();}                  
                        }catch (IOException e2){ 
                        }
                }
                               
        } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
        }
        
         return objAsin;
    }
    public boolean registrarAsintomaticoEnArchivo(ClsAsintomaticoDTO objAsin){
        File archivo=null; 
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        PrintWriter pw = null;
        boolean estaRegistrado=false;
        
        try {
                /*COMPROBACION*/
                archivo = new File(rutaArchivo);
                if (archivo.createNewFile()) {
                        System.out.println("       File created: " + archivo.getName());
                } else {
                        //System.out.println("File already exists.");
                }
                
                /*LECTURA*/
                try {
                        fr = new FileReader (archivo);
                        br = new BufferedReader(fr);

                        String linea;

                        while((linea=br.readLine())!=null){
                                String[] parts = linea.split(" ");
                                if(parts[0].equalsIgnoreCase(objAsin.getTipo_id()) &&  Integer.parseInt(parts[1]) == objAsin.getId()  ){
                                        estaRegistrado=true;
                                        break;
                                }
                        }    
                }
                catch (IOException | NumberFormatException e) {
                }finally{/*Cerrando archivo*/
                        try{                    
                           if( null != fr ){fr.close();}                  
                        }catch (IOException e2){ 
                        }
                }
                
                /*ESCRITURA*/                    
                 try {
                        fw = new FileWriter(archivo,true);
                        pw = new PrintWriter(fw);
                        if(!estaRegistrado){
                             pw.println(objAsin.getTipo_id()+" "+objAsin.getId()+" "+objAsin.getNombres()+" "+objAsin.getApellidos()+" "+objAsin.getDireccion());
                             estaRegistrado=true;
                        }
                }
                catch(IOException e){
                }finally{
                        try{                    
                                if( null != fw ){fw.close();}     
                        }catch (IOException e2){ 
                        }
                }    
                
        } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
        }
        
         return estaRegistrado;
    }
    public int obtenerCantidadRegistros(){
        int cantidad = 0;
        File archivo=null; 
        FileReader fr = null;
        BufferedReader br = null;
        
        try {
                /*COMPROBACION*/
                archivo = new File(rutaArchivo);
                if (archivo.createNewFile()) {
                        System.out.println("       File created: " + archivo.getName());
                } else {
                       // System.out.println("File already exists.");
                }
                
                /*LECTURA*/
                try {
                        fr = new FileReader (archivo);
                        br = new BufferedReader(fr);

                        String linea;
                        while((linea=br.readLine())!=null){
                              cantidad++;
                        }    
                }
                catch (IOException | NumberFormatException e) {
                }finally{/*Cerrando archivo*/
                        try{                    
                           if( null != fr ){fr.close();}                  
                        }catch (IOException e2){ 
                        }
                }
                               
        } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
        }
         return cantidad;
    }
}
