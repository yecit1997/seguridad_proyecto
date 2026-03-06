package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: tipo_reporte
 *
 * CREATE TABLE tipo_reporte (
 *   id_tipo_reporte INT NOT NULL AUTO_INCREMENT,
 *   tipo            VARCHAR(45) NOT NULL,
 *   PRIMARY KEY (id_tipo_reporte)
 * );
 */
public class TipoReporte {

    private int    idTipoReporte;
    private String tipo;   // NOT NULL, VARCHAR(45)

    public TipoReporte() {}

    public TipoReporte(int idTipoReporte, String tipo) {
        this.idTipoReporte = idTipoReporte;
        this.tipo          = tipo;
    }

    /** Constructor para INSERT */
    public TipoReporte(String tipo) {
        this.tipo = tipo;
    }

    public int    getIdTipoReporte()                       { return idTipoReporte; }
    public void   setIdTipoReporte(int idTipoReporte)       { this.idTipoReporte = idTipoReporte; }

    public String getTipo()              { return tipo; }
    public void   setTipo(String tipo)   { this.tipo = tipo; }

    @Override
    public String toString() {
        return "TipoReporte{idTipoReporte=" + idTipoReporte + ", tipo='" + tipo + "'}";
    }
}