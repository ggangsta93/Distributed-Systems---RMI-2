package servidorNotificaciones.dto;

import java.io.Serializable;
import servidorAlertas.dto.ClsAsintomaticoDTO;

public class ClsNotificacionDTO implements Serializable {

    private ClsAsintomaticoDTO asintomatico;
    private ClsUltimoIndicadorDTO ultimoIndicador;
    private ClsAlertaDTO[] alerta;

    public ClsNotificacionDTO() {
        this.setAlerta(new ClsAlertaDTO[5]);
    }

    public ClsAsintomaticoDTO getAsintomatico() {
        return asintomatico;
    }

    public void setAsintomatico(ClsAsintomaticoDTO asintomatico) {
        this.asintomatico = asintomatico;
    }

    public ClsUltimoIndicadorDTO getUltimoIndicador() {
        return ultimoIndicador;
    }

    public void setUltimoIndicador(ClsUltimoIndicadorDTO ultimoIndicador) {
        this.ultimoIndicador = ultimoIndicador;
    }

    public ClsAlertaDTO[] getAlerta() {
        return alerta;
    }

    public void setAlerta(ClsAlertaDTO[] alerta) {
        this.alerta = alerta;
    }

}
