package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: conductor
 *
 * CREATE TABLE conductor (
 *   id_conductor       INT NOT NULL AUTO_INCREMENT,
 *   licencia           VARCHAR(45) NOT NULL,
 *   persona_id_persona INT NOT NULL,
 *   PRIMARY KEY (id_conductor),
 *   FOREIGN KEY (persona_id_persona) REFERENCES persona(id_persona)
 * );
 */
public class Conductor {

    private int     idConductor;
    private String  licencia;   // NOT NULL, VARCHAR(45)
    private Persona persona;    // FK -> persona.id_persona  NOT NULL

    public Conductor() {}

    public Conductor(int idConductor, String licencia, Persona persona) {
        this.idConductor = idConductor;
        this.licencia    = licencia;
        this.persona     = persona;
    }

    /** Constructor para INSERT */
    public Conductor(String licencia, Persona persona) {
        this.licencia = licencia;
        this.persona  = persona;
    }

    public int    getIdConductor()                     { return idConductor; }
    public void   setIdConductor(int idConductor)       { this.idConductor = idConductor; }

    public String getLicencia()                    { return licencia; }
    public void   setLicencia(String licencia)     { this.licencia = licencia; }

    public Persona getPersona()                  { return persona; }
    public void    setPersona(Persona persona)   { this.persona = persona; }

    /** FK helper */
    public int getPersonaId() { return persona != null ? persona.getIdPersona() : 0; }

    @Override
    public String toString() {
        return "Conductor{idConductor=" + idConductor +
               ", licencia='" + licencia +
               "', personaId=" + getPersonaId() + "}";
    }
}