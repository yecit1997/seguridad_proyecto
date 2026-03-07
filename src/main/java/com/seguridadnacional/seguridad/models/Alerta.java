package com.seguridadnacional.seguridad.models;

import java.time.LocalDateTime;

public class Alerta {

    private int           idAlerta;
    private LocalDateTime fechaHora;
    private String        mensaje;
    private boolean       leida;
    private Usuario       usuarioDestinatario;
    private Reporte       reporte;             // nullable

    public Alerta() {}

    public Alerta(LocalDateTime fechaHora, String mensaje, boolean leida,
                  Usuario usuarioDestinatario, Reporte reporte) {
        this.fechaHora           = fechaHora;
        this.mensaje             = mensaje;
        this.leida               = leida;
        this.usuarioDestinatario = usuarioDestinatario;
        this.reporte             = reporte;
    }

    public Alerta(int idAlerta, LocalDateTime fechaHora, String mensaje, boolean leida,
                  Usuario usuarioDestinatario, Reporte reporte) {
        this.idAlerta            = idAlerta;
        this.fechaHora           = fechaHora;
        this.mensaje             = mensaje;
        this.leida               = leida;
        this.usuarioDestinatario = usuarioDestinatario;
        this.reporte             = reporte;
    }

    public int           getIdAlerta()                             { return idAlerta; }
    public void          setIdAlerta(int idAlerta)                 { this.idAlerta = idAlerta; }
    public LocalDateTime getFechaHora()                            { return fechaHora; }
    public void          setFechaHora(LocalDateTime fechaHora)     { this.fechaHora = fechaHora; }
    public String        getMensaje()                              { return mensaje; }
    public void          setMensaje(String mensaje)                { this.mensaje = mensaje; }
    public boolean       isLeida()                                 { return leida; }
    public void          setLeida(boolean leida)                   { this.leida = leida; }
    public Usuario       getUsuarioDestinatario()                  { return usuarioDestinatario; }
    public void          setUsuarioDestinatario(Usuario u)         { this.usuarioDestinatario = u; }
    public Reporte       getReporte()                              { return reporte; }
    public void          setReporte(Reporte reporte)               { this.reporte = reporte; }

    // Helpers
    public int getUsuarioDestinatarioId() { return usuarioDestinatario != null ? usuarioDestinatario.getIdUsuario() : 0; }
    public int getReporteId()             { return reporte             != null ? reporte.getIdReporte()             : 0; }

    @Override
    public String toString() {
        return "Alerta{id=" + idAlerta + ", leida=" + leida + ", mensaje=" + mensaje + "}";
    }
}