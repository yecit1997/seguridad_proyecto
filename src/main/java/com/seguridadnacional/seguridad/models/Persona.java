package com.seguridadnacional.seguridad.models;


public class Persona {

    private int    idPersona;
    private String nombre;    // NOT NULL, VARCHAR(45)
    private String apellido;  // NOT NULL, VARCHAR(45)
    private String correo;    // NOT NULL, VARCHAR(45)
    private String telefono;  // NOT NULL, VARCHAR(45)

    public Persona() {}

    public Persona(int idPersona, String nombre, String apellido, String correo, String telefono) {
        this.idPersona = idPersona;
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.correo    = correo;
        this.telefono  = telefono;
    }

    /** Constructor */
    public Persona(String nombre, String apellido, String correo, String telefono) {
        this.nombre   = nombre;
        this.apellido = apellido;
        this.correo   = correo;
        this.telefono = telefono;
    }

//    Metodos Setters y Getters
    public int    getIdPersona()                   { return idPersona; }
    public void   setIdPersona(int idPersona)       { this.idPersona = idPersona; }

    public String getNombre()                { return nombre; }
    public void   setNombre(String nombre)   { this.nombre = nombre; }

    public String getApellido()                  { return apellido; }
    public void   setApellido(String apellido)   { this.apellido = apellido; }

    public String getCorreo()                { return correo; }
    public void   setCorreo(String correo)   { this.correo = correo; }

    public String getTelefono()                  { return telefono; }
    public void   setTelefono(String telefono)   { this.telefono = telefono; }

//     Reescribimos el metodo toStrin para mostrar la informacion mucho mas clara
    @Override
    public String toString() {
        return "Persona{idPersona=" + idPersona +
               ", nombre='" + nombre +
               "', apellido='" + apellido +
               "', correo='" + correo + "'}";
    }
}