package servidorNotificaciones.dto;

import java.io.Serializable;
import java.util.Date;

public class ClsUltimoIndicadorDTO implements Serializable {

    private float frecuenciaCardiaca;
    private float frecuenciaRespiratoria;
    private float temperatura;

    public ClsUltimoIndicadorDTO() {
    }

    public float getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(float frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public float getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(float frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

}
