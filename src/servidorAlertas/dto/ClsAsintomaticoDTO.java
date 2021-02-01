package servidorAlertas.dto;

import java.io.Serializable;

public class ClsAsintomaticoDTO implements Serializable {

    private String nombres;
    private String apellidos;
    private String tipo_id;
    private int id;
    private String direccion;

    public ClsAsintomaticoDTO() {

    }

    public ClsAsintomaticoDTO(String pNom, String pApell, String pTipoId, int pId, String pDir) {
        nombres = pNom;
        apellidos = pApell;
        tipo_id = pTipoId;
        id = pId;
        direccion = pDir;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
