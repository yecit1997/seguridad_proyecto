package com.seguridadnacional.seguridad.models;

public class Guarda {

    // PK = usuario_rol_id_usuario_rol
    private UsuarioRol usuarioRol;
    private String     areaAsignada;
    private Supervisor supervisor;   // nullable

    public Guarda() {}

    public Guarda(UsuarioRol usuarioRol, String areaAsignada, Supervisor supervisor) {
        this.usuarioRol   = usuarioRol;
        this.areaAsignada = areaAsignada;
        this.supervisor   = supervisor;
    }

    public UsuarioRol getUsuarioRol()                      { return usuarioRol; }
    public void       setUsuarioRol(UsuarioRol usuarioRol) { this.usuarioRol = usuarioRol; }
    public String     getAreaAsignada()                    { return areaAsignada; }
    public void       setAreaAsignada(String areaAsignada) { this.areaAsignada = areaAsignada; }
    public Supervisor getSupervisor()                      { return supervisor; }
    public void       setSupervisor(Supervisor supervisor) { this.supervisor = supervisor; }

    // Helpers para PreparedStatement
    public int getUsuarioRolId()  { return usuarioRol != null ? usuarioRol.getIdUsuarioRol() : 0; }
    public int getSupervisorId()  { return supervisor  != null ? supervisor.getUsuarioRolId() : 0; }

    // Acceso rápido al nombre del guarda
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
        return "Guarda{usuarioRolId=" + getUsuarioRolId() + ", area=" + areaAsignada + "}";
    }
}