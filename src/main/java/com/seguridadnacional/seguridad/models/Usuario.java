package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: usuario
 *
 * CREATE TABLE usuario (
 *   id_usuario                        INT NOT NULL AUTO_INCREMENT,
 *   nombre_usuario                    VARCHAR(50) NOT NULL,
 *   contrasena                        VARCHAR(20) NOT NULL,
 *   status_usuario_id_status_usuario  INT NOT NULL,
 *   rol_usuario_id_rol_usuario        INT NOT NULL,
 *   PRIMARY KEY (id_usuario),
 *   FOREIGN KEY (status_usuario_id_status_usuario) REFERENCES status_usuario(id_status_usuario),
 *   FOREIGN KEY (rol_usuario_id_rol_usuario)        REFERENCES rol_usuario(id_rol_usuario)
 * );
 */
public class Usuario {

    private int          idUsuario;
    private String       nombreUsuario;   // NOT NULL, VARCHAR(50)
    private String       contrasena;      // NOT NULL, VARCHAR(20)
    private StatusUsuario statusUsuario;  // FK -> status_usuario.id_status_usuario
    private RolUsuario   rolUsuario;      // FK -> rol_usuario.id_rol_usuario

    public Usuario() {}

    public Usuario(int idUsuario, String nombreUsuario, String contrasena,
                   StatusUsuario statusUsuario, RolUsuario rolUsuario) {
        this.idUsuario     = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena    = contrasena;
        this.statusUsuario = statusUsuario;
        this.rolUsuario    = rolUsuario;
    }

    /** Constructor para INSERT */
    public Usuario(String nombreUsuario, String contrasena,
                   StatusUsuario statusUsuario, RolUsuario rolUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena    = contrasena;
        this.statusUsuario = statusUsuario;
        this.rolUsuario    = rolUsuario;
    }

    public int    getIdUsuario()                   { return idUsuario; }
    public void   setIdUsuario(int idUsuario)       { this.idUsuario = idUsuario; }

    public String getNombreUsuario()                         { return nombreUsuario; }
    public void   setNombreUsuario(String nombreUsuario)     { this.nombreUsuario = nombreUsuario; }

    public String getContrasena()                    { return contrasena; }
    public void   setContrasena(String contrasena)   { this.contrasena = contrasena; }

    public StatusUsuario getStatusUsuario()                        { return statusUsuario; }
    public void          setStatusUsuario(StatusUsuario s)         { this.statusUsuario = s; }

    public RolUsuario getRolUsuario()                  { return rolUsuario; }
    public void       setRolUsuario(RolUsuario r)      { this.rolUsuario = r; }

    /** FK helpers para INSERT/UPDATE */
    public int getStatusUsuarioId() { return statusUsuario != null ? statusUsuario.getIdStatusUsuario() : 0; }
    public int getRolUsuarioId()    { return rolUsuario    != null ? rolUsuario.getIdRolUsuario()       : 0; }

    @Override
    public String toString() {
        return "Usuario{idUsuario=" + idUsuario +
               ", nombreUsuario='" + nombreUsuario +
               "', statusId=" + getStatusUsuarioId() +
               ", rolId=" + getRolUsuarioId() + "}";
    }
}