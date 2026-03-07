package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.UsuarioRolDAO;
import com.seguridadnacional.seguridad.models.UsuarioRol;
import java.sql.SQLException;
import java.util.List;

public class UsuarioRolController {
    private final UsuarioRolDAO dao = new UsuarioRolDAO();

    public UsuarioRol crear(UsuarioRol ur) {
        try { int id = dao.insertar(ur); ur.setIdUsuarioRol(id); return ur; }
        catch (SQLException e) { throw new RuntimeException("Error al crear usuario_rol: " + e.getMessage(), e); }
    }
    public UsuarioRol buscarPorId(int id) {
        try { return dao.buscarPorId(id); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public List<UsuarioRol> listarPorUsuario(int idUsuario) {
        try { return dao.listarPorUsuario(idUsuario); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public List<UsuarioRol> listarTodos() {
        try { return dao.listarTodos(); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public void eliminar(int id) {
        try { dao.eliminar(id); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
}