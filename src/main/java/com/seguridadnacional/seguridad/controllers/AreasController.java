package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.AreasDAO;
import com.seguridadnacional.seguridad.models.Areas;
import java.sql.SQLException;
import java.util.List;

public class AreasController {

    private final AreasDAO dao = new AreasDAO();

    public Areas crear(Areas area) {
        try {
            validar(area);
            boolean ok = dao.insertar(area);
            if (!ok) throw new RuntimeException("No se pudo crear el área");
            return area;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear área: " + e.getMessage(), e);
        }
    }

    public Areas buscarPorId(int id) {
        try {
            Areas a = dao.buscarPorId(id);
            if (a == null) throw new RuntimeException("Área no encontrada con id: " + id);
            return a;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar área: " + e.getMessage(), e);
        }
    }

    public List<Areas> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar áreas: " + e.getMessage(), e);
        }
    }

    public Areas actualizar(Areas area) {
        try {
            validar(area);
            boolean ok = dao.actualizar(area);
            if (!ok) throw new RuntimeException("No se pudo actualizar el área");
            return area;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar área: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el área con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar área: " + e.getMessage(), e);
        }
    }

    private void validar(Areas a) {
        if (a.getNombreArea() == null || a.getNombreArea().trim().isEmpty())
            throw new IllegalArgumentException("El nombre del área es obligatorio");
    }
}