package com.seguridadnacional.seguridad.models;

public class Usuario {

    private int     idUsuario;
    private String  nombreUsuario;
    private String  contrasena;
    private boolean status;      // TINYINT(1): 1=activo, 0=inactivo
    private Persona persona;

    public Usuario() {}

    public Usuario(String nombreUsuario, String contrasena, boolean status, Persona persona) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena    = contrasena;
        this.status        = status;
        this.persona       = persona;
    }

    public Usuario(int idUsuario, String nombreUsuario, String contrasena, boolean status, Persona persona) {
        this.idUsuario     = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena    = contrasena;
        this.status        = status;
        this.persona       = persona;
    }

    public int     getIdUsuario()                    { return idUsuario; }
    public void    setIdUsuario(int idUsuario)        { this.idUsuario = idUsuario; }
    public String  getNombreUsuario()                { return nombreUsuario; }
    public void    setNombreUsuario(String v)        { this.nombreUsuario = v; }
    public String  getContrasena()                   { return contrasena; }
    public void    setContrasena(String contrasena)  { this.contrasena = contrasena; }
    public boolean isStatus()                        { return status; }
    public void    setStatus(boolean status)         { this.status = status; }
    public Persona getPersona()                      { return persona; }
    public void    setPersona(Persona persona)       { this.persona = persona; }

    // Helper para PreparedStatement
    public int getPersonaId() { return persona != null ? persona.getIdPersona() : 0; }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario + ", usuario=" + nombreUsuario + ", status=" + status + "}";
    }
}