package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.GuardaDAO;
import com.seguridadnacional.seguridad.models.Guarda;
import java.sql.SQLException;
import java.util.List;

public class GuardaController {
    private final GuardaDAO dao = new GuardaDAO();

    public void crear(Guarda g) {
        try { dao.insertar(g); }
        catch (SQLException e) { throw new RuntimeException("Error al crear guarda: " + e.getMessage(), e); }
    }
    public Guarda buscarPorId(int id) {
        try { return dao.buscarPorId(id); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public List<Guarda> listarTodos() {
        try { return dao.listarTodos(); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public List<Guarda> listarPorSupervisor(int supervisorId) {
        try { return dao.listarPorSupervisor(supervisorId); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public void actualizar(Guarda g) {
        try { dao.actualizar(g); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
    public void eliminar(int id) {
        try { dao.eliminar(id); }
        catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
}