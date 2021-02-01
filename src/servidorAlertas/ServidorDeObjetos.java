package servidorAlertas;

import java.rmi.RemoteException;

import servidorAlertas.sop_rmi.GesAsintomaticosImpl;
import servidorAlertas.utilidades.UtilidadesConsola;
import servidorAlertas.utilidades.UtilidadesRegistroS;

public class ServidorDeObjetos {

    public static void main(String[] args) throws RemoteException {

        int numPuertoRMIRegistry = 0;
        String direccionIpRMIRegistry = "";

        System.out.println("Digite la IP del NS: ");
        direccionIpRMIRegistry = UtilidadesConsola.leerCadena();
        System.out.println("Digite el PUERTO del NS: ");
        numPuertoRMIRegistry = UtilidadesConsola.leerEntero();

        GesAsintomaticosImpl objRemoto_SA = new GesAsintomaticosImpl();
        objRemoto_SA.consultarReferenciaRemotaDeNotificacion(direccionIpRMIRegistry, numPuertoRMIRegistry);
        
        try {
            UtilidadesRegistroS.arrancarNS(numPuertoRMIRegistry);
            UtilidadesRegistroS.RegistrarObjetoRemoto(objRemoto_SA, direccionIpRMIRegistry, numPuertoRMIRegistry, "objGestionAsintomaticos");

        } catch (Exception e) {
            System.err.println("No fue posible Arrancar el NS o Registrar el objeto remoto" + e.getMessage());
        }

    }

}
