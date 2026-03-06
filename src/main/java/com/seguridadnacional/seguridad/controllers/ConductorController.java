package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.ConductorDAO;
import com.seguridadnacional.seguridad.models.Conductor;
import java.sql.SQLException;
import java.util.List;

public class ConductorController {

    private final ConductorDAO dao = new ConductorDAO();

    public Conductor crear(Conductor conductor) {
        try {
            validar(conductor);
            boolean ok = dao.insertar(conductor);
            if (!ok) throw new RuntimeException("No se pudo crear el conductor");
            return conductor;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear conductor: " + e.getMessage(), e);
        }
    }

    public Conductor buscarPorId(int id) {
        try {
            Conductor c = dao.buscarPorId(id);
            if (c == null) throw new RuntimeException("Conductor no encontrado con id: " + id);
            return c;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar conductor: " + e.getMessage(), e);
        }
    }

    public List<Conductor> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar conductores: " + e.getMessage(), e);
        }
    }

    public Conductor actualizar(Conductor conductor) {
        try {
            validar(conductor);
            boolean ok = dao.actualizar(conductor);
            if (!ok) throw new RuntimeException("No se pudo actualizar el conductor");
            return conductor;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar conductor: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el conductor con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar conductor: " + e.getMessage(), e);
        }
    }

    private void validar(Conductor c) {
        if (c.getLicencia() == null || c.getLicencia().trim().isEmpty())
            throw new IllegalArgumentException("La licencia del conductor es obligatoria");
        if (c.getPersona() == null)
            throw new IllegalArgumentException("La persona asociada al conductor es obligatoria");
    }
}