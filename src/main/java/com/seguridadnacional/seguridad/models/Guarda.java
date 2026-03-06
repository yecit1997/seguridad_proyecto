package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: guarda
 *
 * CREATE TABLE guarda (
 *   id_guarda               INT NOT NULL AUTO_INCREMENT,
 *   persona_id_persona      INT NOT NULL,
 *   areas_id_areas          INT NOT NULL,
 *   supervisor_id_supervisor INT NOT NULL,
 *   PRIMARY KEY (id_guarda),
 *   FOREIGN KEY (persona_id_persona)       REFERENCES persona(id_persona),
 *   FOREIGN KEY (areas_id_areas)           REFERENCES areas(id_areas),
 *   FOREIGN KEY (supervisor_id_supervisor) REFERENCES supervisor(id_supervisor)
 * );
 */
public class Guarda {

    private int        idGuarda;
    private Persona    persona;     // FK -> persona.id_persona    NOT NULL
    private Areas      areas;       // FK -> areas.id_areas         NOT NULL
    private Supervisor supervisor;  // FK -> supervisor.id_supervisor NOT NULL

    public Guarda() {}

    public Guarda(int idGuarda, Persona persona, Areas areas, Supervisor supervisor) {
        this.idGuarda   = idGuarda;
        this.persona    = persona;
        this.areas      = areas;
        this.supervisor = supervisor;
    }

    /** Constructor para INSERT */
    public Guarda(Persona persona, Areas areas, Supervisor supervisor) {
        this.persona    = persona;
        this.areas      = areas;
        this.supervisor = supervisor;
    }

    public int        getIdGuarda()                    { return idGuarda; }
    public void       setIdGuarda(int idGuarda)         { this.idGuarda = idGuarda; }

    public Persona    getPersona()                  { return persona; }
    public void       setPersona(Persona persona)   { this.persona = persona; }

    public Areas      getAreas()                { return areas; }
    public void       setAreas(Areas areas)     { this.areas = areas; }

    public Supervisor getSupervisor()                    { return supervisor; }
    public void       setSupervisor(Supervisor s)        { this.supervisor = s; }

    /** FK helpers */
    public int getPersonaId()    { return persona    != null ? persona.getIdPersona()          : 0; }
    public int getAreasId()      { return areas      != null ? areas.getIdAreas()              : 0; }
    public int getSupervisorId() { return supervisor != null ? supervisor.getIdSupervisor()    : 0; }

    @Override
    public String toString() {
        return "Guarda{idGuarda=" + idGuarda +
               ", personaId=" + getPersonaId() +
               ", areasId=" + getAreasId() +
               ", supervisorId=" + getSupervisorId() + "}";
    }
}