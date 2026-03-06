package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: permisos
 *
 * CREATE TABLE permisos (
 *   id_permisos     INT NOT NULL AUTO_INCREMENT,
 *   nombre_permiso  VARCHAR(50),
 *   PRIMARY KEY (id_permisos),
 *   UNIQUE KEY id_permisos_UNIQUE (id_permisos)
 * );
 */
public class Permisos {

    private int    idPermisos;
    private String nombrePermiso;

    public Permisos() {}

    public Permisos(int idPermisos, String nombrePermiso) {
        this.idPermisos    = idPermisos;
        this.nombrePermiso = nombrePermiso;
    }

    /** Constructor para INSERT */
    public Permisos(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public int    getIdPermisos()                      { return idPermisos; }
    public void   setIdPermisos(int idPermisos)        { this.idPermisos = idPermisos; }

    public String getNombrePermiso()                         { return nombrePermiso; }
    public void   setNombrePermiso(String nombrePermiso)     { this.nombrePermiso = nombrePermiso; }

    @Override
    public String toString() {
        return "Permisos{idPermisos=" + idPermisos + ", nombrePermiso='" + nombrePermiso + "'}";
    }
}