package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.PersonalAdministrativoDAO;
import com.seguridadnacional.seguridad.models.PersonalAdministrativo;
import java.sql.SQLException;
import java.util.List;

public class PersonalAdministrativoController {

    private final PersonalAdministrativoDAO dao = new PersonalAdministrativoDAO();

    public PersonalAdministrativo crear(PersonalAdministrativo pa) {
        try {
            validar(pa);
            boolean ok = dao.insertar(pa);
            if (!ok) throw new RuntimeException("No se pudo crear el personal administrativo");
            return pa;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear personal administrativo: " + e.getMessage(), e);
        }
    }

    public PersonalAdministrativo buscarPorId(int id) {
        try {
            PersonalAdministrativo pa = dao.buscarPorId(id);
            if (pa == null) throw new RuntimeException("Personal administrativo no encontrado con id: " + id);
            return pa;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar personal administrativo: " + e.getMessage(), e);
        }
    }

    public List<PersonalAdministrativo> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar personal administrativo: " + e.getMessage(), e);
        }
    }

    public PersonalAdministrativo actualizar(PersonalAdministrativo pa) {
        try {
            validar(pa);
            boolean ok = dao.actualizar(pa);
            if (!ok) throw new RuntimeException("No se pudo actualizar el personal administrativo");
            return pa;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar personal administrativo: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el personal administrativo con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar personal administrativo: " + e.getMessage(), e);
        }
    }

    private void validar(PersonalAdministrativo pa) {
        if (pa.getCargo() == null || pa.getCargo().trim().isEmpty())
            throw new IllegalArgumentException("El cargo es obligatorio");
        if (pa.getPersona() == null)
            throw new IllegalArgumentException("La persona asociada es obligatoria");
    }
}