package com.seguridadnacional.seguridad.models;

import java.time.LocalDate;

public class Supervisor {

    // PK = usuario_rol_id_usuario_rol (FK a usuario_rol)
    private UsuarioRol  usuarioRol;
    private LocalDate   fechaAscenso;

    public Supervisor() {}

    public Supervisor(UsuarioRol usuarioRol, LocalDate fechaAscenso) {
        this.usuarioRol   = usuarioRol;
        this.fechaAscenso = fechaAscenso;
    }

    public UsuarioRol getUsuarioRol()                        { return usuarioRol; }
    public void       setUsuarioRol(UsuarioRol usuarioRol)   { this.usuarioRol = usuarioRol; }
    public LocalDate  getFechaAscenso()                      { return fechaAscenso; }
    public void       setFechaAscenso(LocalDate fechaAscenso){ this.fechaAscenso = fechaAscenso; }

    // Helper: la PK en BD
    public int getUsuarioRolId() { return usuarioRol != null ? usuarioRol.getIdUsuarioRol() : 0; }

    // Acceso rápido al nombre del supervisor
    public String getNombreCompleto() {
        if (usuarioRol != null && usuarioRol.getUsuario() != null
                && usuarioRol.getUsuario().getPersona() != null) {
            Persona p = usuarioRol.getUsuario().getPersona();
            return p.getNombre() + " " + p.getApellido();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Supervisor{usuarioRolId=" + getUsuarioRolId() + ", fechaAscenso=" + fechaAscenso + "}";
    }
}