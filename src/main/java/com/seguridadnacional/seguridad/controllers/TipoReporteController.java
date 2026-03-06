package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.TipoReporteDAO;
import com.seguridadnacional.seguridad.models.TipoReporte;
import java.sql.SQLException;
import java.util.List;

public class TipoReporteController {

    private final TipoReporteDAO dao = new TipoReporteDAO();

    public TipoReporte crear(TipoReporte tipo) {
        try {
            validar(tipo);
            boolean ok = dao.insertar(tipo);
            if (!ok) throw new RuntimeException("No se pudo crear el tipo de reporte");
            return tipo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear tipo de reporte: " + e.getMessage(), e);
        }
    }

    public TipoReporte buscarPorId(int id) {
        try {
            TipoReporte t = dao.buscarPorId(id);
            if (t == null) throw new RuntimeException("Tipo de reporte no encontrado con id: " + id);
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar tipo de reporte: " + e.getMessage(), e);
        }
    }

    public List<TipoReporte> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar tipos de reporte: " + e.getMessage(), e);
        }
    }

    public TipoReporte actualizar(TipoReporte tipo) {
        try {
            validar(tipo);
            boolean ok = dao.actualizar(tipo);
            if (!ok) throw new RuntimeException("No se pudo actualizar el tipo de reporte");
            return tipo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tipo de reporte: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el tipo de reporte con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar tipo de reporte: " + e.getMessage(), e);
        }
    }

    private void validar(TipoReporte t) {
        if (t.getTipo() == null || t.getTipo().trim().isEmpty())
            throw new IllegalArgumentException("El tipo de reporte es obligatorio");
    }
}