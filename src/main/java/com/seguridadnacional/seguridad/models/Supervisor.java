package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: supervisor
 *
 * CREATE TABLE supervisor (
 *   id_supervisor     INT NOT NULL AUTO_INCREMENT,
 *   persona_id_persona INT NOT NULL,
 *   PRIMARY KEY (id_supervisor),
 *   FOREIGN KEY (persona_id_persona) REFERENCES persona(id_persona)
 * );
 */
public class Supervisor {

    private int     idSupervisor;
    private Persona persona;       // FK -> persona.id_persona

    public Supervisor() {}

    public Supervisor(int idSupervisor, Persona persona) {
        this.idSupervisor = idSupervisor;
        this.persona      = persona;
    }

    /** Constructor para INSERT */
    public Supervisor(Persona persona) {
        this.persona = persona;
    }

    public int     getIdSupervisor()                       { return idSupervisor; }
    public void    setIdSupervisor(int idSupervisor)        { this.idSupervisor = idSupervisor; }

    public Persona getPersona()                  { return persona; }
    public void    setPersona(Persona persona)   { this.persona = persona; }

    /** FK helper */
    public int getPersonaId() { return persona != null ? persona.getIdPersona() : 0; }

    @Override
    public String toString() {
        return "Supervisor{idSupervisor=" + idSupervisor +
               ", personaId=" + getPersonaId() + "}";
    }
}