package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: status_usuario
 *
 * CREATE TABLE status_usuario (
 *   id_status_usuario INT NOT NULL AUTO_INCREMENT,
 *   status            VARCHAR(45),
 *   PRIMARY KEY (id_status_usuario)
 * );
 */
public class StatusUsuario {

    private int    idStatusUsuario;
    private String status;

    public StatusUsuario() {}

    public StatusUsuario(int idStatusUsuario, String status) {
        this.idStatusUsuario = idStatusUsuario;
        this.status          = status;
    }

    /** Constructor para INSERT (sin id, lo genera la BD) */
    public StatusUsuario(String status) {
        this.status = status;
    }

    public int    getIdStatusUsuario()                       { return idStatusUsuario; }
    public void   setIdStatusUsuario(int idStatusUsuario)    { this.idStatusUsuario = idStatusUsuario; }

    public String getStatus()                { return status; }
    public void   setStatus(String status)   { this.status = status; }

    @Override
    public String toString() {
        return "StatusUsuario{idStatusUsuario=" + idStatusUsuario + ", status='" + status + "'}";
    }
}