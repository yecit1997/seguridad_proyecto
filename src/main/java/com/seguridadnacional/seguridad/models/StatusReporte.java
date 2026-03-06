package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: status_reporte
 *
 * CREATE TABLE status_reporte (
 *   id_status_reporte INT NOT NULL AUTO_INCREMENT,
 *   status            VARCHAR(45) NOT NULL,
 *   PRIMARY KEY (id_status_reporte)
 * );
 */
public class StatusReporte {

    private int    idStatusReporte;
    private String status;           // NOT NULL

    public StatusReporte() {}

    public StatusReporte(int idStatusReporte, String status) {
        this.idStatusReporte = idStatusReporte;
        this.status          = status;
    }

    /** Constructor para INSERT */
    public StatusReporte(String status) {
        this.status = status;
    }

    public int    getIdStatusReporte()                         { return idStatusReporte; }
    public void   setIdStatusReporte(int idStatusReporte)      { this.idStatusReporte = idStatusReporte; }

    public String getStatus()                { return status; }
    public void   setStatus(String status)   { this.status = status; }

    @Override
    public String toString() {
        return "StatusReporte{idStatusReporte=" + idStatusReporte + ", status='" + status + "'}";
    }
}