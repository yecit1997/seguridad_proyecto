package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.UsuarioDAO;
import com.seguridadnacional.seguridad.models.Usuario;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario login(String nombreUsuario, String contrasena) {
        try {
            Usuario u = dao.login(nombreUsuario, contrasena);
            if (u == null) throw new RuntimeException("Credenciales incorrectas o usuario inactivo");
            return u;
        } catch (SQLException e) { throw new RuntimeException("Error en login: " + e.getMessage(), e); }
    }
    public Usuario crear(Usuario u) {
        try { int id = dao.insertar(u); u.setIdUsuario(id); return u; }
        catch (SQLException e) { throw new RuntimeException("Error al crear usuario: " + e.getMessage(), e); }
    }
    public Usuario buscarPorId(int id) {
        try { return dao.buscarPorId(id); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public List<Usuario> listarTodos() {
        try { return dao.listarTodos(); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public void actualizar(Usuario u) {
        try { dao.actualizar(u); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public void eliminar(int id) {
        try { dao.eliminar(id); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
}