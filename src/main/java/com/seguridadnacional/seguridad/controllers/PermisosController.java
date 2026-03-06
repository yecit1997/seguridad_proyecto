package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.PermisosDAO;
import com.seguridadnacional.seguridad.models.Permisos;
import java.sql.SQLException;
import java.util.List;

public class PermisosController {

    private final PermisosDAO dao = new PermisosDAO();

    public Permisos crear(Permisos permisos) {
        try {
            validar(permisos);
            boolean ok = dao.insertar(permisos);
            if (!ok) throw new RuntimeException("No se pudo crear el permiso");
            return permisos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear permiso: " + e.getMessage(), e);
        }
    }

    public Permisos buscarPorId(int id) {
        try {
            Permisos p = dao.buscarPorId(id);
            if (p == null) throw new RuntimeException("Permiso no encontrado con id: " + id);
            return p;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar permiso: " + e.getMessage(), e);
        }
    }

    public List<Permisos> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar permisos: " + e.getMessage(), e);
        }
    }

    public Permisos actualizar(Permisos permisos) {
        try {
            validar(permisos);
            boolean ok = dao.actualizar(permisos);
            if (!ok) throw new RuntimeException("No se pudo actualizar el permiso");
            return permisos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar permiso: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el permiso con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar permiso: " + e.getMessage(), e);
        }
    }

    private void validar(Permisos p) {
        if (p.getNombrePermiso() == null || p.getNombrePermiso().trim().isEmpty())
            throw new IllegalArgumentException("El nombre del permiso es obligatorio");
    }
}