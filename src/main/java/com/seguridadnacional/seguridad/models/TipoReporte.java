package com.seguridadnacional.seguridad.models;

public class TipoReporte {

    private int    idTipoReporte;
    private String nombre;

    public TipoReporte() {}

    public TipoReporte(String nombre) {
        this.nombre = nombre;
    }

    public TipoReporte(int idTipoReporte, String nombre) {
        this.idTipoReporte = idTipoReporte;
        this.nombre        = nombre;
    }

    public int    getIdTipoReporte()               { return idTipoReporte; }
    public void   setIdTipoReporte(int id)         { this.idTipoReporte = id; }
    public String getNombre()                      { return nombre; }
    public void   setNombre(String nombre)         { this.nombre = nombre; }

    @Override
    public String toString() {
        return "TipoReporte{id=" + idTipoReporte + ", nombre=" + nombre + "}";
    }
}