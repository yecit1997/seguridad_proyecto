package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.PersonaDAO;
import com.seguridadnacional.seguridad.models.Persona;
import java.sql.SQLException;
import java.util.List;

public class PersonaController {

    private final PersonaDAO dao = new PersonaDAO();

    public Persona crearPersona(Persona persona) {
        try {
            validar(persona);
            boolean ok = dao.insertar(persona);
            if (!ok) throw new RuntimeException("No se pudo crear la persona");
            return persona;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear persona: " + e.getMessage(), e);
        }
    }

    public Persona buscarPorId(int id) {
        try {
            Persona p = dao.buscarPorId(id);
            if (p == null) throw new RuntimeException("Persona no encontrada con id: " + id);
            return p;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar persona: " + e.getMessage(), e);
        }
    }

    public List<Persona> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar personas: " + e.getMessage(), e);
        }
    }

    public Persona actualizar(Persona persona) {
        try {
            validar(persona);
            boolean ok = dao.actualizar(persona);
            if (!ok) throw new RuntimeException("No se pudo actualizar la persona");
            return persona;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar persona: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar la persona con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar persona: " + e.getMessage(), e);
        }
    }

    private void validar(Persona p) {
        if (p.getNombre()   == null || p.getNombre().trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (p.getApellido() == null || p.getApellido().trim().isEmpty())
            throw new IllegalArgumentException("El apellido es obligatorio");
        if (p.getCorreo()   == null || p.getCorreo().trim().isEmpty())
            throw new IllegalArgumentException("El correo es obligatorio");
        if (p.getTelefono() == null || p.getTelefono().trim().isEmpty())
            throw new IllegalArgumentException("El teléfono es obligatorio");
    }
}