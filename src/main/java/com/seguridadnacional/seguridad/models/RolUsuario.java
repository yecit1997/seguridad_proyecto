package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: rol_usuario
 *
 * CREATE TABLE rol_usuario (
 *   id_rol_usuario        INT NOT NULL AUTO_INCREMENT,
 *   nombre_rol_usuario    VARCHAR(45),
 *   permisos_id_permisos  INT NOT NULL,
 *   PRIMARY KEY (id_rol_usuario),
 *   FOREIGN KEY (permisos_id_permisos) REFERENCES permisos(id_permisos)
 * );
 */
public class RolUsuario {

    private int      idRolUsuario;
    private String   nombreRolUsuario;
    private Permisos permisos;           // FK -> permisos.id_permisos

    public RolUsuario() {}

    public RolUsuario(int idRolUsuario, String nombreRolUsuario, Permisos permisos) {
        this.idRolUsuario      = idRolUsuario;
        this.nombreRolUsuario  = nombreRolUsuario;
        this.permisos          = permisos;
    }

    /** Constructor para INSERT */
    public RolUsuario(String nombreRolUsuario, Permisos permisos) {
        this.nombreRolUsuario = nombreRolUsuario;
        this.permisos         = permisos;
    }

    public int    getIdRolUsuario()                        { return idRolUsuario; }
    public void   setIdRolUsuario(int idRolUsuario)        { this.idRolUsuario = idRolUsuario; }

    public String getNombreRolUsuario()                              { return nombreRolUsuario; }
    public void   setNombreRolUsuario(String nombreRolUsuario)       { this.nombreRolUsuario = nombreRolUsuario; }

    public Permisos getPermisos()                    { return permisos; }
    public void     setPermisos(Permisos permisos)   { this.permisos = permisos; }

    /** Retorna solo la FK para usar en INSERT/UPDATE */
    public int getPermisosId() {
        return permisos != null ? permisos.getIdPermisos() : 0;
    }

    @Override
    public String toString() {
        return "RolUsuario{idRolUsuario=" + idRolUsuario +
               ", nombreRolUsuario='" + nombreRolUsuario +
               "', permisosId=" + getPermisosId() + "}";
    }
}