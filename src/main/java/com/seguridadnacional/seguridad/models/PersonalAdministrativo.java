package com.seguridadnacional.seguridad.models;

public class PersonalAdministrativo {

    // PK = usuario_rol_id_usuario_rol
    private UsuarioRol usuarioRol;
    private String     cargo;

    public PersonalAdministrativo() {}

    public PersonalAdministrativo(UsuarioRol usuarioRol, String cargo) {
        this.usuarioRol = usuarioRol;
        this.cargo      = cargo;
    }

    public UsuarioRol getUsuarioRol()                      { return usuarioRol; }
    public void       setUsuarioRol(UsuarioRol usuarioRol) { this.usuarioRol = usuarioRol; }
    public String     getCargo()                           { return cargo; }
    public void       setCargo(String cargo)               { this.cargo = cargo; }

    public int getUsuarioRolId() { return usuarioRol != null ? usuarioRol.getIdUsuarioRol() : 0; }

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
        return "PersonalAdministrativo{usuarioRolId=" + getUsuarioRolId() + ", cargo=" + cargo + "}";
    }
}