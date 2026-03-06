package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: reporte
 *
 * CREATE TABLE reporte (
 *   id_reporte                        INT NOT NULL AUTO_INCREMENT,
 *   descripcion                       VARCHAR(255) NOT NULL,
 *   usuario_id_usuario                INT NOT NULL,
 *   tipo_reporte_id_tipo_reporte      INT NOT NULL,
 *   status_reporte_id_status_reporte  INT NOT NULL,
 *   PRIMARY KEY (id_reporte),
 *   FOREIGN KEY (usuario_id_usuario)               REFERENCES usuario(id_usuario),
 *   FOREIGN KEY (tipo_reporte_id_tipo_reporte)     REFERENCES tipo_reporte(id_tipo_reporte),
 *   FOREIGN KEY (status_reporte_id_status_reporte) REFERENCES status_reporte(id_status_reporte)
 * );
 */
public class Reporte {

    private int          idReporte;
    private String       descripcion;    // NOT NULL, VARCHAR(255)
    private Usuario      usuario;        // FK -> usuario.id_usuario             NOT NULL
    private TipoReporte  tipoReporte;   // FK -> tipo_reporte.id_tipo_reporte    NOT NULL
    private StatusReporte statusReporte; // FK -> status_reporte.id_status_reporte NOT NULL

    public Reporte() {}

    public Reporte(int idReporte, String descripcion, Usuario usuario,
                   TipoReporte tipoReporte, StatusReporte statusReporte) {
        this.idReporte     = idReporte;
        this.descripcion   = descripcion;
        this.usuario       = usuario;
        this.tipoReporte   = tipoReporte;
        this.statusReporte = statusReporte;
    }

    /** Constructor para INSERT */
    public Reporte(String descripcion, Usuario usuario,
                   TipoReporte tipoReporte, StatusReporte statusReporte) {
        this.descripcion   = descripcion;
        this.usuario       = usuario;
        this.tipoReporte   = tipoReporte;
        this.statusReporte = statusReporte;
    }

    public int    getIdReporte()                   { return idReporte; }
    public void   setIdReporte(int idReporte)       { this.idReporte = idReporte; }

    public String getDescripcion()                     { return descripcion; }
    public void   setDescripcion(String descripcion)   { this.descripcion = descripcion; }

    public Usuario getUsuario()                  { return usuario; }
    public void    setUsuario(Usuario u)         { this.usuario = u; }

    public TipoReporte getTipoReporte()                    { return tipoReporte; }
    public void        setTipoReporte(TipoReporte t)       { this.tipoReporte = t; }

    public StatusReporte getStatusReporte()                    { return statusReporte; }
    public void          setStatusReporte(StatusReporte s)     { this.statusReporte = s; }

    /** FK helpers */
    public int getUsuarioId()       { return usuario       != null ? usuario.getIdUsuario()               : 0; }
    public int getTipoReporteId()   { return tipoReporte   != null ? tipoReporte.getIdTipoReporte()       : 0; }
    public int getStatusReporteId() { return statusReporte != null ? statusReporte.getIdStatusReporte()   : 0; }

    @Override
    public String toString() {
        return "Reporte{idReporte=" + idReporte +
               ", descripcion='" + descripcion +
               "', usuarioId=" + getUsuarioId() +
               ", tipoReporteId=" + getTipoReporteId() +
               ", statusReporteId=" + getStatusReporteId() + "}";
    }
}