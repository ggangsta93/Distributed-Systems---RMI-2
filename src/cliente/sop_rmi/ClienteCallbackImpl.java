/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.sop_rmi;

import cliente.vistas.ClienteInicio;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author javier
 */
public class ClienteCallbackImpl  extends UnicastRemoteObject implements ClienteCallbackInt {
    private  ClienteInicio guiCliente=null;
    public ClienteCallbackImpl(ClienteInicio guiCliente) throws RemoteException{
        super();
        this.guiCliente=guiCliente;
    }
    @Override
    public void notificar(String mensaje) throws RemoteException {
        guiCliente.escribirEnNotificaciones(mensaje);
    }   
}
