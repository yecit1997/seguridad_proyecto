package com.seguridadnacional.seguridad.models;

public class StatusReporte {

    private int    idStatusReporte;
    private String nombre;

    public StatusReporte() {}

    public StatusReporte(String nombre) {
        this.nombre = nombre;
    }

    public StatusReporte(int idStatusReporte, String nombre) {
        this.idStatusReporte = idStatusReporte;
        this.nombre          = nombre;
    }

    public int    getIdStatusReporte()         { return idStatusReporte; }
    public void   setIdStatusReporte(int id)   { this.idStatusReporte = id; }
    public String getNombre()                  { return nombre; }
    public void   setNombre(String nombre)     { this.nombre = nombre; }

    @Override
    public String toString() {
        return "StatusReporte{id=" + idStatusReporte + ", nombre=" + nombre + "}";
    }
}