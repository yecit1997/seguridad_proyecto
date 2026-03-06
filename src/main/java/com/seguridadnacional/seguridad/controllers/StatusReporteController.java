package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.StatusReporteDAO;
import com.seguridadnacional.seguridad.models.StatusReporte;
import java.sql.SQLException;
import java.util.List;

public class StatusReporteController {

    private final StatusReporteDAO dao = new StatusReporteDAO();

    public StatusReporte crear(StatusReporte status) {
        try {
            validar(status);
            boolean ok = dao.insertar(status);
            if (!ok) throw new RuntimeException("No se pudo crear el status de reporte");
            return status;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear status de reporte: " + e.getMessage(), e);
        }
    }

    public StatusReporte buscarPorId(int id) {
        try {
            StatusReporte s = dao.buscarPorId(id);
            if (s == null) throw new RuntimeException("Status de reporte no encontrado con id: " + id);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar status de reporte: " + e.getMessage(), e);
        }
    }

    public List<StatusReporte> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar status de reporte: " + e.getMessage(), e);
        }
    }

    public StatusReporte actualizar(StatusReporte status) {
        try {
            validar(status);
            boolean ok = dao.actualizar(status);
            if (!ok) throw new RuntimeException("No se pudo actualizar el status de reporte");
            return status;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar status de reporte: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el status de reporte con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar status de reporte: " + e.getMessage(), e);
        }
    }

    private void validar(StatusReporte s) {
        if (s.getStatus() == null || s.getStatus().trim().isEmpty())
            throw new IllegalArgumentException("El status es obligatorio");
    }
}