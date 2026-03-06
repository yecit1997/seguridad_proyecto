package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.RolUsuarioDAO;
import com.seguridadnacional.seguridad.models.RolUsuario;
import java.sql.SQLException;
import java.util.List;

public class RolUsuarioController {

    private final RolUsuarioDAO dao = new RolUsuarioDAO();

    public RolUsuario crear(RolUsuario rol) {
        try {
            validar(rol);
            boolean ok = dao.insertar(rol);
            if (!ok) throw new RuntimeException("No se pudo crear el rol");
            return rol;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear rol: " + e.getMessage(), e);
        }
    }

    public RolUsuario buscarPorId(int id) {
        try {
            RolUsuario r = dao.buscarPorId(id);
            if (r == null) throw new RuntimeException("Rol no encontrado con id: " + id);
            return r;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar rol: " + e.getMessage(), e);
        }
    }

    public List<RolUsuario> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar roles: " + e.getMessage(), e);
        }
    }

    public RolUsuario actualizar(RolUsuario rol) {
        try {
            validar(rol);
            boolean ok = dao.actualizar(rol);
            if (!ok) throw new RuntimeException("No se pudo actualizar el rol");
            return rol;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar rol: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el rol con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar rol: " + e.getMessage(), e);
        }
    }

    private void validar(RolUsuario r) {
        if (r.getNombreRolUsuario() == null || r.getNombreRolUsuario().trim().isEmpty())
            throw new IllegalArgumentException("El nombre del rol es obligatorio");
        if (r.getPermisos() == null)
            throw new IllegalArgumentException("El permiso asociado al rol es obligatorio");
    }
}