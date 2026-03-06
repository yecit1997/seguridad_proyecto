package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.ReporteDAO;
import com.seguridadnacional.seguridad.models.Reporte;
import java.sql.SQLException;
import java.util.List;

public class ReporteController {

    private final ReporteDAO dao = new ReporteDAO();

    public Reporte crearReporte(Reporte reporte) {
        try {
            validarReporte(reporte);
            boolean ok = dao.insertar(reporte);
            if (!ok) throw new RuntimeException("No se pudo crear el reporte");
            return reporte;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear reporte: " + e.getMessage(), e);
        }
    }

    public Reporte buscarPorId(int id) {
        try {
            Reporte r = dao.buscarPorId(id);
            if (r == null) throw new RuntimeException("Reporte no encontrado con id: " + id);
            return r;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar reporte: " + e.getMessage(), e);
        }
    }

    public List<Reporte> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar reportes: " + e.getMessage(), e);
        }
    }

    public List<Reporte> listarPorUsuario(int idUsuario) {
        try {
            return dao.listarPorUsuario(idUsuario);
        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar por usuario: " + e.getMessage(), e);
        }
    }

    public List<Reporte> listarPorTipo(int idTipo) {
        try {
            return dao.listarPorTipo(idTipo);
        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar por tipo: " + e.getMessage(), e);
        }
    }

    public List<Reporte> listarPorStatus(int idStatus) {
        try {
            return dao.listarPorStatus(idStatus);
        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar por status: " + e.getMessage(), e);
        }
    }

    public Reporte actualizarStatus(int idReporte, int idStatus) {
        try {
            boolean ok = dao.actualizarStatus(idReporte, idStatus);
            if (!ok) throw new RuntimeException("No se pudo actualizar el status del reporte");
            return dao.buscarPorId(idReporte);
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar status: " + e.getMessage(), e);
        }
    }

    public Reporte actualizar(Reporte reporte) {
        try {
            validarReporte(reporte);
            boolean ok = dao.actualizar(reporte);
            if (!ok) throw new RuntimeException("No se pudo actualizar el reporte");
            return reporte;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar reporte: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el reporte con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar reporte: " + e.getMessage(), e);
        }
    }

    private void validarReporte(Reporte r) {
        if (r.getDescripcion() == null || r.getDescripcion().trim().isEmpty())
            throw new IllegalArgumentException("La descripción del reporte es obligatoria");
        if (r.getUsuario() == null)
            throw new IllegalArgumentException("El usuario es obligatorio");
        if (r.getTipoReporte() == null)
            throw new IllegalArgumentException("El tipo de reporte es obligatorio");
        if (r.getStatusReporte() == null)
            throw new IllegalArgumentException("El status del reporte es obligatorio");
    }
}