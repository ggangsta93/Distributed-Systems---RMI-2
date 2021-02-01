package servidorAlertas.sop_rmi;

import cliente.sop_rmi.ClienteCallbackInt;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import servidorAlertas.dao.AlertasDAO;
import servidorAlertas.dao.AsintomaticoDAO;

import servidorAlertas.dto.ClsAsintomaticoDTO;
import servidorAlertas.dto.ClsIndicadoresDTO;
import servidorAlertas.utilidades.UtilidadesRegistroC;
import servidorNotificaciones.dto.ClsNotificacionDTO;
import servidorNotificaciones.dto.ClsUltimoIndicadorDTO;
import servidorNotificaciones.sop_rmi.NotificacionInt;



public class GesAsintomaticosImpl extends UnicastRemoteObject implements GesAsintomaticosInt {
    private static NotificacionInt objRemoto_SN;
    private HashMap <String, ClienteCallbackInt> asintomaticos;
    private AlertasDAO alertasDAO=null;
    private AsintomaticoDAO asintomaticoDAO=null;
    private int cantidadRegPermitida;
    
    public GesAsintomaticosImpl() throws RemoteException {
        super();
        this.asintomaticoDAO =new  AsintomaticoDAO("Asintomaticos/registrados.txt");
        this.alertasDAO =new AlertasDAO(this, asintomaticoDAO);
        this.asintomaticos= new HashMap <String, ClienteCallbackInt> ();
        this.cantidadRegPermitida=0;
    }

    @Override
    public boolean registrarAsintomatico(ClsAsintomaticoDTO objAsin, ClienteCallbackInt objCliente) throws RemoteException {
        System.out.println("Desde registrarAsintomatico()");
        boolean bandera = false;

        if (asintomaticoDAO.obtenerCantidadRegistros() < cantidadRegPermitida) {
            if (asintomaticoDAO.existeAsintomatico(objAsin.getTipo_id(), objAsin.getId()) == null) {
                asintomaticoDAO.registrarAsintomaticoEnArchivo(objAsin);
                asintomaticos.put(objAsin.getTipo_id()+""+objAsin.getId(),objCliente);
                bandera = true;
                System.out.println("	Registrado.");
            } else {
                System.out.println("	No registrado.");
            }

        }
        System.out.println("Saliendo de registrarAsintomatico()");
        return bandera;
    }

    @Override
    public boolean enviarIndicadores(ClsIndicadoresDTO objIndi) throws RemoteException {
        System.out.println("Desde enviarIndicadores()");
        boolean estaRecibido = false;

        ClsAsintomaticoDTO objusuarioConsultado = asintomaticoDAO.existeAsintomatico(objIndi.getTipo_id(), objIndi.getId());

        if (objusuarioConsultado != null) {
            System.out.println("	Indicador procesado.");
            alertasDAO.escribirAlertaEnArchivo(objIndi);
            int puntuacion = obtenerPuntuacion(objIndi);

            if (puntuacion >= 3) {
                ClsNotificacionDTO objNoti = new ClsNotificacionDTO();
                objNoti.setAsintomatico(objusuarioConsultado);//Asignamos el asintomatico al objeto Notificaciones
                alertasDAO.leerAlertasDesdeArchivo(objNoti);//Asignamos las 5 ultimas alertas al objeto Notificaciones
                ClsUltimoIndicadorDTO objUltIndi=new ClsUltimoIndicadorDTO();
                objUltIndi.setFrecuenciaCardiaca(objIndi.getFrecuenciaCardiaca());
                objUltIndi.setFrecuenciaRespiratoria(objIndi.getFrecuenciaRespiratoria());
                objUltIndi.setTemperatura(objIndi.getTemperatura());
                objNoti.setUltimoIndicador(objUltIndi);//Asignamos el ultimo indicador al objeto notificaciones
                asintomaticos.get(objIndi.getTipo_id()+""+objIndi.getId()).notificar("El paciente "+objusuarioConsultado.getNombres()+" "+objusuarioConsultado.getApellidos()+"\ncon identificacion "+objusuarioConsultado.getTipo_id()+""+objusuarioConsultado.getId()+"\nesta fuera del rango normal.");
                objRemoto_SN.notificarRegistro(objNoti);
                System.out.println("	Se enviò una notificacion.");
            }
            estaRecibido = true;

        } else {
            estaRecibido = false;
            System.out.println("	Indicador no procesado.");
        }
        System.out.println("Saliendo de enviarIndicadores()");

        return estaRecibido;
    }

    public void consultarReferenciaRemotaDeNotificacion(String dir_Ip, int numPuerto) {
        System.out.println("	consultarReferenciaRemotaDeNotificacion()");
        objRemoto_SN = (NotificacionInt) UtilidadesRegistroC.obtenerObjRemoto(dir_Ip, numPuerto, "ObjetoRemotoNotificaciones");
    }

    public int obtenerPuntuacion(ClsIndicadoresDTO objIndi) {
        System.out.println("	obtenerPuntuacion()");
        int suma = 0;
        if (objIndi.getFrecuenciaCardiaca() >= 60 && objIndi.getFrecuenciaCardiaca() <= 80) {
            suma++;
        }
        if (objIndi.getFrecuenciaRespiratoria() >= 70 && objIndi.getFrecuenciaRespiratoria() <= 90) {
            suma++;
        }
        if (objIndi.getTemperatura() >= 36.2 && objIndi.getTemperatura() <= 37.2) {
            suma++;
        }
        return suma;
    }

    @Override
    public int consultarRegPermitidos() throws RemoteException {
        System.out.println("Desde consultarRegPermitidos()");
        System.out.println("Saliendo de consultarRegPermitidos()");
        return this.cantidadRegPermitida;
    }

    @Override
    public boolean cambiarRegPermitidos(int cantidad) throws RemoteException {
        System.out.println("Desde cambiarRegPermitidos()");
        System.out.println("Saliendo de cambiarRegPermitidos()");
        this.cantidadRegPermitida = cantidad;
        return true;
    }
    
}
