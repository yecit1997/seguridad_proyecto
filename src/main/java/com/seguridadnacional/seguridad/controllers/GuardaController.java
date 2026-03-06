package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.GuardaDAO;
import com.seguridadnacional.seguridad.models.Guarda;
import java.sql.SQLException;
import java.util.List;

public class GuardaController {

    private final GuardaDAO dao = new GuardaDAO();

    public Guarda crearGuarda(Guarda guarda) {
        try {
            validarGuarda(guarda);
            boolean ok = dao.insertar(guarda);
            if (!ok) throw new RuntimeException("No se pudo crear el guarda");
            return guarda;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear guarda: " + e.getMessage(), e);
        }
    }

    public Guarda buscarPorId(int id) {
        try {
            Guarda g = dao.buscarPorId(id);
            if (g == null) throw new RuntimeException("Guarda no encontrado con id: " + id);
            return g;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar guarda: " + e.getMessage(), e);
        }
    }

    public List<Guarda> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar guardas: " + e.getMessage(), e);
        }
    }

    public List<Guarda> listarPorSupervisor(int idSupervisor) {
        try {
            return dao.listarPorSupervisor(idSupervisor);
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar guardas por supervisor: " + e.getMessage(), e);
        }
    }

    public Guarda actualizar(Guarda guarda) {
        try {
            validarGuarda(guarda);
            boolean ok = dao.actualizar(guarda);
            if (!ok) throw new RuntimeException("No se pudo actualizar el guarda");
            return guarda;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar guarda: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el guarda con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar guarda: " + e.getMessage(), e);
        }
    }

    private void validarGuarda(Guarda g) {
        if (g.getPersona() == null)
            throw new IllegalArgumentException("La persona del guarda es obligatoria");
        if (g.getAreas() == null)
            throw new IllegalArgumentException("El área asignada es obligatoria");
        if (g.getSupervisor() == null)
            throw new IllegalArgumentException("El supervisor es obligatorio");
    }
}