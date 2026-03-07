package com.seguridadnacional.seguridad.models;

public class Conductor {

    // PK = id_fk_persona (FK a persona, no autoincremental)
    private Persona persona;
    private String  licencia;

    public Conductor() {}

    public Conductor(Persona persona, String licencia) {
        this.persona  = persona;
        this.licencia = licencia;
    }

    public Persona getPersona()               { return persona; }
    public void    setPersona(Persona persona){ this.persona = persona; }
    public String  getLicencia()              { return licencia; }
    public void    setLicencia(String licencia){ this.licencia = licencia; }

    // Helper: la PK en BD es id_fk_persona
    public int getIdFkPersona() { return persona != null ? persona.getIdPersona() : 0; }

    @Override
    public String toString() {
        return "Conductor{idFkPersona=" + getIdFkPersona() + ", licencia=" + licencia + "}";
    }
}