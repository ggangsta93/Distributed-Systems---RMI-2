package cliente;

import static cliente.ClienteDeObjetos.lecturaSensores;
import static cliente.ClienteDeObjetos.obtenerPuntuacion;
import cliente.vistas.ClienteInicio;
import cliente.sop_rmi.ClienteCallbackImpl;
import java.rmi.RemoteException;

import cliente.utilidades.UtilidadesRegistroC;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidorAlertas.dto.ClsAsintomaticoDTO;
import servidorAlertas.dto.ClsIndicadoresDTO;
import servidorAlertas.sop_rmi.GesAsintomaticosInt;


public class ClienteDeObjetos {
    private static GesAsintomaticosInt objRemoto=null;
    private static ClsAsintomaticoDTO objAsintomatico = new ClsAsintomaticoDTO();
    private static ClsIndicadoresDTO objIndicadores = new ClsIndicadoresDTO();
    private static boolean asintomaticoRegistrado = false;
    private static ClienteInicio guiCliente = null;
    private hiloEnviarNotificaciones hilo = null;

    public static void main(String[] args) {
        ClienteDeObjetos clienteDeObjetos=new ClienteDeObjetos();
        guiCliente=new ClienteInicio(clienteDeObjetos);
        guiCliente.setVisible(true);
    }
    
    
    public boolean obtenerObjetoRemoto(int numPuertoRMIRegistry, String direccionIpRMIRegistry){
        boolean seObtuvo = false;
        objRemoto = (GesAsintomaticosInt) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, numPuertoRMIRegistry, "objGestionAsintomaticos");
        if(objRemoto != null)
             seObtuvo = true;
        return seObtuvo;
    }

    public boolean registrarAsintomatico(String nombres, String apellidos, String tipo_id, int id, String direccion){
        try {            
                objAsintomatico.setNombres(nombres);
                objAsintomatico.setApellidos(apellidos);
                objAsintomatico.setTipo_id(tipo_id);
                objAsintomatico.setId(id);
                objAsintomatico.setDireccion(direccion);

                ClienteCallbackImpl usuario=new ClienteCallbackImpl(guiCliente);
                if (objRemoto.registrarAsintomatico(objAsintomatico,usuario)) {
                        System.out.println("Se registrò con èxito. ");
                        objIndicadores.setTipo_id(objAsintomatico.getTipo_id());
                        objIndicadores.setId(objAsintomatico.getId());
                        asintomaticoRegistrado = true;
                } else {
                        System.out.println("No se pudo registrar. ");
                }
    
        } catch (RemoteException ex) {
                Logger.getLogger(ClienteDeObjetos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return asintomaticoRegistrado;
    }
  
     public int consultarRegPermitidos(){
        int valor=0;
        try {
            valor = objRemoto.consultarRegPermitidos();
           
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteDeObjetos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
      }

    public boolean cambiarRegPermitidos(int cantidad){
        boolean cambiado =false;
        try {
            cambiado = objRemoto.cambiarRegPermitidos(cantidad);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteDeObjetos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cambiado;
    }
    
    public void desconectar(){
        if(hilo != null){
            hilo.stop();
        }
        objRemoto = null;
    }

    public void enviarIndicadores(){
        if (asintomaticoRegistrado){
                hilo = new hiloEnviarNotificaciones(objRemoto, objIndicadores, guiCliente);
                hilo.start();            
        }            
    }

    private static float IndicadoresRandom() {
        return (float) (Math.random() * 100);
    }

    public static void lecturaSensores() {
        float fecuenciaCardiaca = IndicadoresRandom();
        float frecuenciaRespiratoria = IndicadoresRandom();
        float temperatura = IndicadoresRandom();

        objIndicadores.setFrecuenciaCardiaca(fecuenciaCardiaca);
        objIndicadores.setFrecuenciaRespiratoria(frecuenciaRespiratoria);
        objIndicadores.setTemperatura(temperatura);
        
        guiCliente.escribirEnFreCardiaca(String.format("%.2f",fecuenciaCardiaca));
        guiCliente.escribirEnFreRespiratoria(String.format("%.2f",frecuenciaRespiratoria));
        guiCliente.escribirEnTemperatura(String.format("%.2f",temperatura)+"° C");
    }

    public static int obtenerPuntuacion() {
        int suma = 0;
        if (objIndicadores.getFrecuenciaCardiaca() >= 60 && objIndicadores.getFrecuenciaCardiaca() <= 80){
            suma++;
        }
        if (objIndicadores.getFrecuenciaRespiratoria() >= 70 && objIndicadores.getFrecuenciaRespiratoria() <= 90){
            suma++;
        }
        if (objIndicadores.getTemperatura() >= 36.2 && objIndicadores.getTemperatura() <= 37.2){
            suma++;
        }
        return suma;
    }
    
}


class hiloEnviarNotificaciones extends Thread
{  
    private  GesAsintomaticosInt objRemoto=null;
    private  ClsIndicadoresDTO objIndicadores =null;
    private  ClienteInicio guiCliente=null;
    
    public hiloEnviarNotificaciones(GesAsintomaticosInt objRemoto,ClsIndicadoresDTO objIndicadores,ClienteInicio guiCliente){
        this.objRemoto=objRemoto;
        this.objIndicadores=objIndicadores;
        this.guiCliente=guiCliente;
    }
    
   @Override
   public void run()
   {
        while (true) {
                try {
                        for(int i=0; i<8 ; i++){
                             guiCliente.sincronizarBarra(i);
                             Thread.sleep(1000); 
                        }
                        lecturaSensores();
                        int puntuacion = obtenerPuntuacion();

                        if (puntuacion >= 2) {
                             guiCliente.escribirEnConsola("Enviando indicadores...\nFrecuencia cardiaca: " + objIndicadores.getFrecuenciaCardiaca()+"\nFrecuencia respiratoria: " + objIndicadores.getFrecuenciaRespiratoria()+"\nTemperatura: " + objIndicadores.getTemperatura());
                             objRemoto.enviarIndicadores(objIndicadores);
                        }
                } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                }
        }     
   }
   
}
