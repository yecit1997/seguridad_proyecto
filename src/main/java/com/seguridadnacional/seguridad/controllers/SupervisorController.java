package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.SupervisorDAO;
import com.seguridadnacional.seguridad.models.Supervisor;
import java.sql.SQLException;
import java.util.List;

public class SupervisorController {

    private final SupervisorDAO dao = new SupervisorDAO();

    public Supervisor crear(Supervisor supervisor) {
        try {
            validar(supervisor);
            boolean ok = dao.insertar(supervisor);
            if (!ok) throw new RuntimeException("No se pudo crear el supervisor");
            return supervisor;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear supervisor: " + e.getMessage(), e);
        }
    }

    public Supervisor buscarPorId(int id) {
        try {
            Supervisor s = dao.buscarPorId(id);
            if (s == null) throw new RuntimeException("Supervisor no encontrado con id: " + id);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar supervisor: " + e.getMessage(), e);
        }
    }

    public List<Supervisor> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar supervisores: " + e.getMessage(), e);
        }
    }

    public Supervisor actualizar(Supervisor supervisor) {
        try {
            validar(supervisor);
            boolean ok = dao.actualizar(supervisor);
            if (!ok) throw new RuntimeException("No se pudo actualizar el supervisor");
            return supervisor;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar supervisor: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el supervisor con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar supervisor: " + e.getMessage(), e);
        }
    }

    private void validar(Supervisor s) {
        if (s.getPersona() == null)
            throw new IllegalArgumentException("La persona asociada al supervisor es obligatoria");
    }
}