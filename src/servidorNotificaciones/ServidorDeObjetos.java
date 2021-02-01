package servidorNotificaciones;

import java.rmi.RemoteException;
import servidorNotificaciones.vistas.servidorNotificacionesVista;

import servidorNotificaciones.sop_rmi.ClsNotificacion;
import servidorNotificaciones.utilidades.UtilidadesRegistroS;

public class ServidorDeObjetos {

    private static servidorNotificacionesVista vistaNotificaciones=null;
    private static ClsNotificacion objRemoto =null;
    
    public static void main(String[] args) throws RemoteException {
        ServidorDeObjetos servidorDeObjetos=new ServidorDeObjetos();
        vistaNotificaciones=new servidorNotificacionesVista(servidorDeObjetos);
        vistaNotificaciones.setVisible(true);
        objRemoto = new ClsNotificacion(vistaNotificaciones);
    }
    
    public boolean encenderServicio( int numPuertoRMIRegistry,String direccionIpRMIRegistry ){
        boolean encendido=false;
        try {
            UtilidadesRegistroS.arrancarNS(numPuertoRMIRegistry);
            UtilidadesRegistroS.RegistrarObjetoRemoto(objRemoto, direccionIpRMIRegistry, numPuertoRMIRegistry, "ObjetoRemotoNotificaciones");
            encendido=true;
        } catch (Exception e) {
            System.err.println("No fue posible Arrancar el NS o Registrar el objeto remoto" + e.getMessage());
        }
        return encendido;
    }
    

}
