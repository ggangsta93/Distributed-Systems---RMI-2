package servidorAlertas.sop_rmi;

import cliente.sop_rmi.ClienteCallbackInt;
import java.rmi.Remote;
import java.rmi.RemoteException;

import servidorAlertas.dto.ClsAsintomaticoDTO;
import servidorAlertas.dto.ClsIndicadoresDTO;

public interface GesAsintomaticosInt extends Remote {
	public boolean registrarAsintomatico(ClsAsintomaticoDTO objAsin,ClienteCallbackInt objCliente)throws RemoteException;
	public boolean enviarIndicadores(ClsIndicadoresDTO objIndi)throws RemoteException;
        	public int consultarRegPermitidos()throws RemoteException;
                public boolean cambiarRegPermitidos(int cantidad)throws RemoteException;
}
