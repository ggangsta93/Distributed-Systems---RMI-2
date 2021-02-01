package servidorNotificaciones.sop_rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;

import servidorNotificaciones.dto.ClsNotificacionDTO;
import servidorNotificaciones.vistas.servidorNotificacionesVista;

public class ClsNotificacion extends UnicastRemoteObject implements NotificacionInt {

    
    private servidorNotificacionesVista gui=null;
    public ClsNotificacion(servidorNotificacionesVista gui) throws RemoteException {
        super();
        this.gui=gui;
    }

    @Override
    public void notificarRegistro(ClsNotificacionDTO objNotificacion) throws RemoteException {
        System.out.println("Desde notificarRegistro()");
                 gui.escribirEnNotificaciones("                                                          ALERTA GENERADA                        ");
                 gui.escribirEnNotificaciones("                        No de id: "+objNotificacion.getAsintomatico().getTipo_id()+" "+objNotificacion.getAsintomatico().getId());
	 gui.escribirEnNotificaciones("                        Nombres y Apellidos: "+objNotificacion.getAsintomatico().getNombres()+" "+objNotificacion.getAsintomatico().getApellidos());
	 gui.escribirEnNotificaciones("                        Direccion: "+objNotificacion.getAsintomatico().getDireccion());
	 gui.escribirEnNotificaciones("                        Fecha de alerta: "+objNotificacion.getAlerta()[4].getFecha());
	 gui.escribirEnNotificaciones("                        Hora de alerta: "+objNotificacion.getAlerta()[4].getHora());
	 gui.escribirEnNotificaciones("                                                                                                                 ");
	 gui.escribirEnNotificaciones("                                           Indicadores que generaron la alerta             ");
	 gui.escribirEnNotificaciones("                         ______________________________________________________________");
	 gui.escribirEnNotificaciones("                        |                                                           |                                 |");
	 gui.escribirEnNotificaciones("                        |         Nombre del indicador                  |           Valor              |");
	 gui.escribirEnNotificaciones("                        |_______________________________________|______________________|");
	 gui.escribirEnNotificaciones("                        |       Frecuencia cardiaca:                      |           "+String.format("%.2f",objNotificacion.getUltimoIndicador().getFrecuenciaCardiaca())+"             |");
	 gui.escribirEnNotificaciones("                        |       Frecuencia respiratoria:                 |           "+String.format("%.2f",objNotificacion.getUltimoIndicador().getFrecuenciaRespiratoria())+"             |");
	 gui.escribirEnNotificaciones("                        |       Temperatura:                               |           "+String.format("%.2f",objNotificacion.getUltimoIndicador().getTemperatura())+"              |");
	 gui.escribirEnNotificaciones("                        |_______________________________________|______________________|");
	 gui.escribirEnNotificaciones("                                                                                                                         ");
	 gui.escribirEnNotificaciones("                                    La enfermera o el medico deben revisar al paciente:        ");
	 gui.escribirEnNotificaciones("                                       "+objNotificacion.getAsintomatico().getNombres()+" "+objNotificacion.getAsintomatico().getApellidos()+" con "+objNotificacion.getAsintomatico().getTipo_id()+" "+objNotificacion.getAsintomatico().getId());
	 gui.escribirEnNotificaciones("                                                                                                                         ");
	 gui.escribirEnNotificaciones("                                                             Ultimas 5 alertas                       ");
	 gui.escribirEnNotificaciones("                         _______________________________________________________________");
	 gui.escribirEnNotificaciones("                        |                                |                                 |                           |");
	 gui.escribirEnNotificaciones("                        |     Fecha alerta         |    Hora de alerta       |    Puntuacion       |");
	 gui.escribirEnNotificaciones("                        |_____________________|______________________|__________________|");
	for(int i=0 ; i< 5 ;i++){
		if(!objNotificacion.getAlerta()[i].getFecha().equalsIgnoreCase(".")    ){
                                                int longitudFecha = objNotificacion.getAlerta()[i].getFecha().length();
                                                int longitudHora = objNotificacion.getAlerta()[i].getHora().length();
                                                int diferencia = 10-longitudFecha;
                                                String espacioFecha,espacioHora; 
                                                
                                                String aux="", aux2= "  ";
                                                for(int j=0; j<diferencia-1; j++)
                                                        aux2 +="  ";                                                
                                                espacioFecha = (diferencia==0)?aux:aux2;
                                                
                                                diferencia = 8-longitudHora;
                                                aux="";
                                                aux2= "  ";
                                                for(int k=0; k<diferencia-1; k++)
                                                        aux2 +="  ";                                                
                                                espacioHora = (diferencia==0)?aux:aux2;
                                                    
			 gui.escribirEnNotificaciones("                        |     "+espacioFecha+""+objNotificacion.getAlerta()[i].getFecha()+"          |       "+espacioHora+""+objNotificacion.getAlerta()[i].getHora()+"             |          "+objNotificacion.getAlerta()[i].getPuntuacion()+"            |");
		}
	}
	gui.escribirEnNotificaciones("                        |_____________________|______________________|__________________|");
                gui.escribirEnNotificaciones("****************************************************************************************************************************************");
        System.out.println("Saliendo de notificarRegistro()");
    }

}


