package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.StatusUsuarioDAO;
import com.seguridadnacional.seguridad.models.StatusUsuario;
import java.sql.SQLException;
import java.util.List;

public class StatusUsuarioController {

    private final StatusUsuarioDAO dao = new StatusUsuarioDAO();

    public StatusUsuario crear(StatusUsuario status) {
        try {
            validar(status);
            boolean ok = dao.insertar(status);
            if (!ok) throw new RuntimeException("No se pudo crear el status de usuario");
            return status;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear status de usuario: " + e.getMessage(), e);
        }
    }

    public StatusUsuario buscarPorId(int id) {
        try {
            StatusUsuario s = dao.buscarPorId(id);
            if (s == null) throw new RuntimeException("Status de usuario no encontrado con id: " + id);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar status de usuario: " + e.getMessage(), e);
        }
    }

    public List<StatusUsuario> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar status de usuario: " + e.getMessage(), e);
        }
    }

    public StatusUsuario actualizar(StatusUsuario status) {
        try {
            validar(status);
            boolean ok = dao.actualizar(status);
            if (!ok) throw new RuntimeException("No se pudo actualizar el status de usuario");
            return status;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar status de usuario: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el status de usuario con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar status de usuario: " + e.getMessage(), e);
        }
    }

    private void validar(StatusUsuario s) {
        if (s.getStatus() == null || s.getStatus().trim().isEmpty())
            throw new IllegalArgumentException("El status es obligatorio");
    }
}