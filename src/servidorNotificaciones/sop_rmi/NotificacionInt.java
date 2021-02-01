package servidorNotificaciones.sop_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import servidorNotificaciones.dto.ClsNotificacionDTO;

public interface NotificacionInt extends Remote {
	public void notificarRegistro(ClsNotificacionDTO objNotificacion)throws RemoteException;
}
