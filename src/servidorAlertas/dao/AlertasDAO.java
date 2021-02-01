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
import java.util.Calendar;
import java.util.GregorianCalendar;
import servidorAlertas.dto.ClsAsintomaticoDTO;
import servidorAlertas.dto.ClsIndicadoresDTO;
import servidorAlertas.sop_rmi.GesAsintomaticosImpl;
import servidorNotificaciones.dto.ClsAlertaDTO;
import servidorNotificaciones.dto.ClsNotificacionDTO;

/**
 *
 * @author javier
 */
public class AlertasDAO {
      
      private int[] copado=new int[1];
      private int[] copado2=new int[1];
      private GesAsintomaticosImpl gesAsintomaticosImpl=null;
      private AsintomaticoDAO asintomaticoDAO = null;
      
      public AlertasDAO(GesAsintomaticosImpl gesAsintomaticosImpl, AsintomaticoDAO asintomaticoDAO){
                this.gesAsintomaticosImpl = gesAsintomaticosImpl;
                this.asintomaticoDAO = asintomaticoDAO;
                copado[0]=0;
                copado2[0]=0;
      }
        
     public void escribirAlertaEnArchivo(ClsIndicadoresDTO objIndi){
        File archivo=null; 
        FileWriter fw = null;
        PrintWriter pw = null;
        
        try {
                /*COMPROBACION*/
                archivo = new File("HistorialDeAlertas/"+objIndi.getTipo_id()+""+objIndi.getId()+".txt");
                if (archivo.createNewFile()) {
                        System.out.println("       File created: " + archivo.getName());
                } else {
                        //System.out.println("File already exists.");
                }
                
                /*ESCRITURA*/                    
                 try {
                        fw = new FileWriter(archivo,true);
                        pw = new PrintWriter(fw);
                        ClsAsintomaticoDTO temp=asintomaticoDAO.existeAsintomatico(objIndi.getTipo_id(), objIndi.getId());
                        Calendar c = new GregorianCalendar();
                        if(temp != null){
                            pw.println(temp.getTipo_id()+" "+temp.getId()+" "+temp.getNombres()+" "+temp.getApellidos()+" "+c.get(Calendar.DATE)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+" "+gesAsintomaticosImpl.obtenerPuntuacion(objIndi));
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
        
    }    
     
     public void leerAlertasDesdeArchivo(ClsNotificacionDTO objNoti){
         File archivo=null; 
        FileReader fr = null;
        BufferedReader br = null;
        ClsAsintomaticoDTO objAsin=null;
        
        try {
                /*COMPROBACION*/
                archivo = new File("HistorialDeAlertas/"+objNoti.getAsintomatico().getTipo_id()+""+objNoti.getAsintomatico().getId()+".txt");
                if (archivo.createNewFile()) {
                        System.out.println("       File created: " + archivo.getName());
                } else {
                       // System.out.println("File already exists.");
                }
                
                /*LECTURA*/
                try {
                        fr = new FileReader (archivo);
                        br = new BufferedReader(fr);
  
                        String[][] arreglo={{".",".","."},{".",".","."},{".",".","."},{".",".","."},{".",".","."}}; /*Arreglo de 5 posiciones*/
                        String linea;
                        while((linea=br.readLine())!=null){
                                String[] parts = linea.split(" ");
				
		boolean permanecer = true;
		int i=-1;
		while(permanecer){	
                                        i++;
                                        if(arreglo[i][0].equalsIgnoreCase(".")){
                                                arreglo[i][0]=parts[6];
                                                arreglo[i][1]=parts[7];
                                                arreglo[i][2]=parts[8];
			permanecer = false;
                                        }
                                        if(i == 4){
			permanecer =false;
			if(copado2[0]==0){
                                                        copado[0]=1;
			}else{
			        copado[0]=0;	
			}
		        }	
		}
				
		if(copado[0]!=1 &&  !arreglo[4][0].equalsIgnoreCase(".") ){/*Toca desplazar los elementos*/
			arreglo[0][0] = arreglo[1][0];
                                                arreglo[0][1] = arreglo[1][1];
			arreglo[0][2] = arreglo[1][2];

			arreglo[1][0]  = arreglo[2][0] ;
                        		arreglo[1][1]  = arreglo[2][1] ;
			arreglo[1][2]  = arreglo[2][2] ;

                                                arreglo[2][0]  = arreglo[3][0] ;
                        		arreglo[2][1]  = arreglo[3][1] ;
			arreglo[2][2]  = arreglo[3][2] ;
                                                
                                                arreglo[3][0]  = arreglo[4][0] ;
                        		arreglo[3][1]  = arreglo[4][1] ;
			arreglo[3][2]  = arreglo[4][2] ;
                                                                        
                                                arreglo[4][0]=parts[6];
                                                arreglo[4][1]=parts[7];
                                                arreglo[4][2]=parts[8];
		}
				
		if(copado[0]==1){
		        copado2[0]=1;
                                }		
                        } //Cierre del While      
                        
                        
                        ClsAlertaDTO[] vector=new  ClsAlertaDTO[5];
                        
                        for(int i=0; i<5 ;i++){/*Pasando las 5 alertas al objeto notificacion*/
                             ClsAlertaDTO objAlerta=new ClsAlertaDTO();
                             objAlerta.setFecha(arreglo[i][0]);
                             objAlerta.setHora(arreglo[i][1]);
                             if(!arreglo[i][2].equalsIgnoreCase("."))
                                     objAlerta.setPuntuacion(Float.parseFloat(arreglo[i][2]));  
                             else
                                     objAlerta.setPuntuacion(0);  
                             vector[i] = objAlerta;
                        }                 
                        objNoti.setAlerta(vector);
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
        copado[0]=0;
        copado2[0]=0;
    }//Cierre del metodo
    
}
