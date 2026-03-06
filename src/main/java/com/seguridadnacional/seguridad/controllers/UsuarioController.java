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
            if (u == null) throw new RuntimeException("Credenciales incorrectas");
            return u;
        } catch (SQLException e) {
            throw new RuntimeException("Error al iniciar sesión: " + e.getMessage(), e);
        }
    }

    public Usuario crearUsuario(Usuario usuario) {
        try {
            validarUsuario(usuario);
            boolean ok = dao.insertar(usuario);
            if (!ok) throw new RuntimeException("No se pudo crear el usuario");
            return usuario;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario: " + e.getMessage(), e);
        }
    }

    public Usuario buscarPorId(int id) {
        try {
            Usuario u = dao.buscarPorId(id);
            if (u == null) throw new RuntimeException("Usuario no encontrado con id: " + id);
            return u;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario: " + e.getMessage(), e);
        }
    }

    public List<Usuario> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
    }

    public Usuario actualizar(Usuario usuario) {
        try {
            validarUsuario(usuario);
            boolean ok = dao.actualizar(usuario);
            if (!ok) throw new RuntimeException("No se pudo actualizar el usuario");
            return usuario;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el usuario con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    private void validarUsuario(Usuario u) {
        if (u.getNombreUsuario() == null || u.getNombreUsuario().trim().isEmpty())
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        if (u.getContrasena() == null || u.getContrasena().trim().isEmpty())
            throw new IllegalArgumentException("La contraseña es obligatoria");
        if (u.getRolUsuario() == null)
            throw new IllegalArgumentException("El rol es obligatorio");
        if (u.getStatusUsuario() == null)
            throw new IllegalArgumentException("El status es obligatorio");
    }
}