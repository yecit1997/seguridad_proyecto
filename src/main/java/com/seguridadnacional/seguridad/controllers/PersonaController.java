package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.PersonaDAO;
import com.seguridadnacional.seguridad.models.Persona;
import java.sql.SQLException;
import java.util.List;

public class PersonaController {
    private final PersonaDAO dao = new PersonaDAO();

    public Persona crear(Persona p) {
        try { int id = dao.insertar(p); p.setIdPersona(id); return p; }
        catch (SQLException e) { throw new RuntimeException("Error al crear persona: " + e.getMessage(), e); }
    }
    public Persona buscarPorId(int id) {
        try { return dao.buscarPorId(id); }
        catch (SQLException e) { throw new RuntimeException("Error al buscar persona: " + e.getMessage(), e); }
    }
    public Persona buscarPorDni(String dni) {
        try { return dao.buscarPorDni(dni); }
        catch (SQLException e) { throw new RuntimeException("Error al buscar por DNI: " + e.getMessage(), e); }
    }
    public List<Persona> listarTodos() {
        try { return dao.listarTodos(); }
        catch (SQLException e) { throw new RuntimeException("Error al listar personas: " + e.getMessage(), e); }
    }
    public void actualizar(Persona p) {
        try { dao.actualizar(p); }
        catch (SQLException e) { throw new RuntimeException("Error al actualizar persona: " + e.getMessage(), e); }
    }
    public void eliminar(int id) {
        try { dao.eliminar(id); }
        catch (SQLException e) { throw new RuntimeException("Error al eliminar persona: " + e.getMessage(), e); }
    }
}