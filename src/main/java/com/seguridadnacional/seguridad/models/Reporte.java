package com.seguridadnacional.seguridad.models;

import java.time.LocalDateTime;

public class Reporte {

    private int            idReporte;
    private LocalDateTime  fechaCreacion;
    private String         descripcion;
    private Usuario        usuarioGenerador;
    private TipoReporte    tipoReporte;
    private StatusReporte  statusReporte;

    public Reporte() {}

    public Reporte(LocalDateTime fechaCreacion, String descripcion,
                   Usuario usuarioGenerador, TipoReporte tipoReporte, StatusReporte statusReporte) {
        this.fechaCreacion    = fechaCreacion;
        this.descripcion      = descripcion;
        this.usuarioGenerador = usuarioGenerador;
        this.tipoReporte      = tipoReporte;
        this.statusReporte    = statusReporte;
    }

    public Reporte(int idReporte, LocalDateTime fechaCreacion, String descripcion,
                   Usuario usuarioGenerador, TipoReporte tipoReporte, StatusReporte statusReporte) {
        this.idReporte        = idReporte;
        this.fechaCreacion    = fechaCreacion;
        this.descripcion      = descripcion;
        this.usuarioGenerador = usuarioGenerador;
        this.tipoReporte      = tipoReporte;
        this.statusReporte    = statusReporte;
    }

    public int           getIdReporte()                          { return idReporte; }
    public void          setIdReporte(int idReporte)             { this.idReporte = idReporte; }
    public LocalDateTime getFechaCreacion()                      { return fechaCreacion; }
    public void          setFechaCreacion(LocalDateTime v)       { this.fechaCreacion = v; }
    public String        getDescripcion()                        { return descripcion; }
    public void          setDescripcion(String descripcion)      { this.descripcion = descripcion; }
    public Usuario       getUsuarioGenerador()                   { return usuarioGenerador; }
    public void          setUsuarioGenerador(Usuario u)          { this.usuarioGenerador = u; }
    public TipoReporte   getTipoReporte()                        { return tipoReporte; }
    public void          setTipoReporte(TipoReporte tipoReporte) { this.tipoReporte = tipoReporte; }
    public StatusReporte getStatusReporte()                      { return statusReporte; }
    public void          setStatusReporte(StatusReporte v)       { this.statusReporte = v; }

    // Helpers para PreparedStatement
    public int getUsuarioGeneradorId() { return usuarioGenerador != null ? usuarioGenerador.getIdUsuario() : 0; }
    public int getTipoReporteId()      { return tipoReporte      != null ? tipoReporte.getIdTipoReporte()  : 0; }
    public int getStatusReporteId()    { return statusReporte    != null ? statusReporte.getIdStatusReporte() : 0; }

    @Override
    public String toString() {
        return "Reporte{id=" + idReporte + ", fecha=" + fechaCreacion + ", desc=" + descripcion + "}";
    }
}