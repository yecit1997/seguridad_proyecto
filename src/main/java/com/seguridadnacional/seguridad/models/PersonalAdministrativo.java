package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: personal_administrativo
 *
 * CREATE TABLE personal_administrativo (
 *   id_personal_administrativo INT NOT NULL AUTO_INCREMENT,
 *   cargo                      VARCHAR(45) NOT NULL,
 *   persona_id_persona         INT NOT NULL,
 *   PRIMARY KEY (id_personal_administrativo),
 *   FOREIGN KEY (persona_id_persona) REFERENCES persona(id_persona)
 * );
 */
public class PersonalAdministrativo {

    private int     idPersonalAdministrativo;
    private String  cargo;    // NOT NULL, VARCHAR(45)
    private Persona persona;  // FK -> persona.id_persona  NOT NULL

    public PersonalAdministrativo() {}

    public PersonalAdministrativo(int idPersonalAdministrativo, String cargo, Persona persona) {
        this.idPersonalAdministrativo = idPersonalAdministrativo;
        this.cargo   = cargo;
        this.persona = persona;
    }

    /** Constructor para INSERT */
    public PersonalAdministrativo(String cargo, Persona persona) {
        this.cargo   = cargo;
        this.persona = persona;
    }

    public int    getIdPersonalAdministrativo()                                  { return idPersonalAdministrativo; }
    public void   setIdPersonalAdministrativo(int idPersonalAdministrativo)       { this.idPersonalAdministrativo = idPersonalAdministrativo; }

    public String getCargo()                 { return cargo; }
    public void   setCargo(String cargo)     { this.cargo = cargo; }

    public Persona getPersona()                  { return persona; }
    public void    setPersona(Persona persona)   { this.persona = persona; }

    /** FK helper */
    public int getPersonaId() { return persona != null ? persona.getIdPersona() : 0; }

    @Override
    public String toString() {
        return "PersonalAdministrativo{id=" + idPersonalAdministrativo +
               ", cargo='" + cargo +
               "', personaId=" + getPersonaId() + "}";
    }
}