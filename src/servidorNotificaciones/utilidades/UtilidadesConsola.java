package servidorNotificaciones.utilidades;

import java.util.Scanner;

public class UtilidadesConsola {

    public static int leerEntero() {
        String linea = "";
        int opcion = 0;
        boolean valido = false;
        do {
            try {
                //System.out.println("Ingrese la opcion: ");
                java.util.Scanner br = new Scanner(System.in);
                opcion = Integer.parseInt(br.nextLine());
                valido = true;
            } catch (Exception e) {
                System.out.println("Error intente nuevamente...");
                valido = false;
            }
        } while (!valido);

        return opcion;

    }

    public static String leerCadena() {
        String linea = "";
        boolean valido = false;
        do {
            try {
                //System.out.println("Ingrese la opcion: ");
                java.util.Scanner br = new Scanner(System.in);
                linea = br.nextLine();
                valido = true;
                valido = true;
            } catch (Exception e) {
                System.out.println("Error intente nuevamente...");
                valido = false;
            }
        } while (!valido);

        return linea;

    }
}
