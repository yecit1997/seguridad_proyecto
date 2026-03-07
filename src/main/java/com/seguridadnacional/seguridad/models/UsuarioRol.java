package com.seguridadnacional.seguridad.models;

public class UsuarioRol {

    private int     idUsuarioRol;
    private Usuario usuario;
    private Rol     rol;

    public UsuarioRol() {}

    public UsuarioRol(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol     = rol;
    }

    public UsuarioRol(int idUsuarioRol, Usuario usuario, Rol rol) {
        this.idUsuarioRol = idUsuarioRol;
        this.usuario      = usuario;
        this.rol          = rol;
    }

    public int      getIdUsuarioRol()                    { return idUsuarioRol; }
    public void     setIdUsuarioRol(int idUsuarioRol)    { this.idUsuarioRol = idUsuarioRol; }
    public Usuario  getUsuario()                         { return usuario; }
    public void     setUsuario(Usuario usuario)          { this.usuario = usuario; }
    public Rol      getRol()                             { return rol; }
    public void     setRol(Rol rol)                      { this.rol = rol; }

    // Helpers para PreparedStatement
    public int getUsuarioId() { return usuario != null ? usuario.getIdUsuario() : 0; }
    public int getRolId()     { return rol     != null ? rol.getIdRol()         : 0; }

    @Override
    public String toString() {
        return "UsuarioRol{id=" + idUsuarioRol + ", usuario=" + getUsuarioId() + ", rol=" + getRolId() + "}";
    }
}