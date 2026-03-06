package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: areas
 *
 * CREATE TABLE areas (
 *   id_areas    INT NOT NULL AUTO_INCREMENT,
 *   nombre_area VARCHAR(50) NOT NULL,
 *   PRIMARY KEY (id_areas)
 * );
 */
public class Areas {

    private int    idAreas;
    private String nombreArea;  // NOT NULL, VARCHAR(50)

    public Areas() {}

    public Areas(int idAreas, String nombreArea) {
        this.idAreas    = idAreas;
        this.nombreArea = nombreArea;
    }

    /** Constructor para INSERT */
    public Areas(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public int    getIdAreas()                 { return idAreas; }
    public void   setIdAreas(int idAreas)       { this.idAreas = idAreas; }

    public String getNombreArea()                    { return nombreArea; }
    public void   setNombreArea(String nombreArea)   { this.nombreArea = nombreArea; }

    @Override
    public String toString() {
        return "Areas{idAreas=" + idAreas + ", nombreArea='" + nombreArea + "'}";
    }
}