package servidorNotificaciones.dto;

import java.io.Serializable;
import java.util.Date;

public class ClsAlertaDTO implements Serializable {

    private String fecha;
    private String hora;
    private float puntuacion;

    public ClsAlertaDTO() {

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

}
