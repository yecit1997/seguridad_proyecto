package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.ConductorDAO;
import com.seguridadnacional.seguridad.models.Conductor;
import java.sql.SQLException;
import java.util.List;
public class ConductorController {
    private final ConductorDAO dao = new ConductorDAO();
    public void crear(Conductor c) { try { dao.insertar(c); } catch (SQLException e) { throw new RuntimeException("Error al crear conductor: " + e.getMessage(), e); } }
    public Conductor buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Conductor> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(Conductor c) { try { dao.actualizar(c); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}