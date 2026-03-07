package com.seguridadnacional.seguridad.models;

public class Persona {

    private int    idPersona;
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;

    public Persona() {}

    public Persona(String dni, String nombre, String apellido, String correo, String telefono) {
        this.dni      = dni;
        this.nombre   = nombre;
        this.apellido = apellido;
        this.correo   = correo;
        this.telefono = telefono;
    }

    public Persona(int idPersona, String dni, String nombre, String apellido, String correo, String telefono) {
        this.idPersona = idPersona;
        this.dni       = dni;
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.correo    = correo;
        this.telefono  = telefono;
    }

    public int    getIdPersona()               { return idPersona; }
    public void   setIdPersona(int idPersona)  { this.idPersona = idPersona; }
    public String getDni()                     { return dni; }
    public void   setDni(String dni)           { this.dni = dni; }
    public String getNombre()                  { return nombre; }
    public void   setNombre(String nombre)     { this.nombre = nombre; }
    public String getApellido()                { return apellido; }
    public void   setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo()                  { return correo; }
    public void   setCorreo(String correo)     { this.correo = correo; }
    public String getTelefono()                { return telefono; }
    public void   setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Persona{id=" + idPersona + ", dni=" + dni + ", nombre=" + nombre + " " + apellido + "}";
    }
}